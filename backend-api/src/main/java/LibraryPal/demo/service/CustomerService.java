
package LibraryPal.demo.service;

import LibraryPal.demo.entity.Customer;
import LibraryPal.demo.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Customer register(Customer customer) {
        if (customerRepository.existsByUsername(customer.getUsername())) {
            throw new IllegalArgumentException("Username is already taken");
        }

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateProfile(Long id, String name, String username) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        if (username != null && !username.equals(customer.getUsername())
                && customerRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username is already taken");
        }

        if (name != null && !name.isBlank()) {
            customer.setName(name);
        }

        if (username != null && !username.isBlank()) {
            customer.setUsername(username);
        }

        return customerRepository.save(customer);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }
}
