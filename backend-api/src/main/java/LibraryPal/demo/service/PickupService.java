package LibraryPal.demo.service;

import LibraryPal.demo.entity.Book;
import LibraryPal.demo.entity.Customer;
import LibraryPal.demo.entity.ServiceRequest;
import LibraryPal.demo.repository.BookRepository;
import LibraryPal.demo.repository.CustomerRepository;
import LibraryPal.demo.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;

// Customer pickup / drop-off requests for books (user story US-1).
@Service
public class PickupService {

    public static final String PICKUP = "PICKUP";
    public static final String DROP_OFF = "DROP_OFF";

    private static final Set<String> ACTIVE_STATUSES =
            Set.of("PENDING", "APPROVED");

    private final ServiceRequestRepository requestRepository;
    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;

    public PickupService(
            ServiceRequestRepository requestRepository,
            CustomerRepository customerRepository,
            BookRepository bookRepository) {
        this.requestRepository = requestRepository;
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
    }

    // Schedule a pickup or drop-off. A pickup reserves one copy.
    @Transactional
    public ServiceRequest schedule(
            Long customerId, Long bookId,
            String requestType, String scheduleDateText) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Customer not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "That book could not be found"));

        String type = requestType == null
                ? "" : requestType.trim().toUpperCase();
        if (!type.equals(PICKUP) && !type.equals(DROP_OFF)) {
            throw new IllegalArgumentException(
                    "Please choose pickup or drop-off");
        }

        LocalDate date = parseDate(scheduleDateText);
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "The date cannot be in the past");
        }

        boolean alreadyRequested = requestRepository
                .findByCustomerIdOrderByScheduleDateAsc(customerId)
                .stream()
                .anyMatch(r -> r.getBook() != null
                        && r.getBook().getId().equals(bookId)
                        && r.getRequestType().equals(type)
                        && ACTIVE_STATUSES.contains(r.getStatus()));
        if (alreadyRequested) {
            throw new IllegalArgumentException(
                    "You already have an active request of this type "
                            + "for this book");
        }

        if (type.equals(PICKUP)) {
            if (book.getAvailableCopies() < 1) {
                throw new IllegalArgumentException(
                        "No copies of this book are available right now");
            }
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);
        }

        ServiceRequest request = new ServiceRequest();
        request.setCustomer(customer);
        request.setBook(book);
        request.setRequestType(type);
        request.setScheduleDate(date);
        request.setLocation(book.getLocalLibrary().getName());
        request.setStatus("PENDING");
        request.setDateAdded(LocalDate.now());

        return requestRepository.save(request);
    }

    @Transactional(readOnly = true)
    public List<ServiceRequest> getForCustomer(Long customerId) {
        return requestRepository
                .findByCustomerIdOrderByScheduleDateAsc(customerId);
    }

    // Cancel one of the customer's own requests; a pickup frees its copy.
    @Transactional
    public void cancel(Long customerId, Long requestId) {
        ServiceRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "That request could not be found"));

        if (request.getCustomer() == null
                || !request.getCustomer().getId().equals(customerId)) {
            throw new IllegalArgumentException(
                    "You can only cancel your own requests");
        }

        if (!ACTIVE_STATUSES.contains(request.getStatus())) {
            throw new IllegalArgumentException(
                    "This request can no longer be cancelled");
        }

        request.setStatus("CANCELLED");
        requestRepository.save(request);

        Book book = request.getBook();
        if (book != null && PICKUP.equals(request.getRequestType())
                && book.getAvailableCopies() < book.getTotalCopies()) {
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookRepository.save(book);
        }
    }

    private LocalDate parseDate(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Please choose a date");
        }

        try {
            return LocalDate.parse(text.trim());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("That date is not valid");
        }
    }
}
