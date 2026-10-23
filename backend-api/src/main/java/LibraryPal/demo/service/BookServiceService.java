
package LibraryPal.demo.service;

import LibraryPal.demo.entity.BookService;
import LibraryPal.demo.entity.Customer;
import LibraryPal.demo.repository.BookServiceRepository;
import LibraryPal.demo.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceService {

    private final BookServiceRepository bookServiceRepository;
    private final CustomerRepository customerRepository;

    public BookServiceService(
            BookServiceRepository bookServiceRepository,
            CustomerRepository customerRepository) {
        this.bookServiceRepository = bookServiceRepository;
        this.customerRepository = customerRepository;
    }

    public List<BookService> findAll() {
        return bookServiceRepository.findAll();
    }

    public BookService findById(Long id) {
        return bookServiceRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Book service not found"));
    }

    @Transactional
    public BookService create(Long customerId, String serviceName, String description) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Customer not found"));

        BookService bookService = new BookService();
        bookService.setServiceName(serviceName);
        bookService.setDescription(description);
        bookService.setCustomer(customer);

        return bookServiceRepository.save(bookService);
    }

    @Transactional
    public BookService update(
            Long id, Long customerId, String serviceName, String description) {
        BookService bookService = findById(id);

        if (!bookService.getCustomer().getId().equals(customerId)) {
            throw new IllegalArgumentException(
                    "You can only update your own book services");
        }

        if (serviceName != null && !serviceName.isBlank()) {
            bookService.setServiceName(serviceName);
        }

        if (description != null && !description.isBlank()) {
            bookService.setDescription(description);
        }

        return bookServiceRepository.save(bookService);
    }

    @Transactional
    public void delete(Long id, Long customerId) {
        BookService bookService = findById(id);

        if (!bookService.getCustomer().getId().equals(customerId)) {
            throw new IllegalArgumentException(
                    "You can only delete your own book services");
        }

        bookServiceRepository.delete(bookService);
    }
}
