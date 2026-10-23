
package LibraryPal.demo.controller;

import LibraryPal.demo.dto.CustomerResponse;
import LibraryPal.demo.entity.Customer;
import LibraryPal.demo.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse register(@Valid @RequestBody RegisterRequest request) {
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setUsername(request.getUsername());
        customer.setPassword(request.getPassword());

        return CustomerResponse.from(customerService.register(customer));
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomer(@PathVariable Long id) {
        return CustomerResponse.from(customerService.findById(id));
    }

    @PutMapping("/{id}")
    public CustomerResponse updateProfile(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProfileRequest request) {

        Customer customer = customerService.updateProfile(
                id,
                request.getName(),
                request.getUsername()
        );

        return CustomerResponse.from(customer);
    }

    public static class RegisterRequest {
        @NotBlank
        private String name;

        @NotBlank
        private String username;

        @NotBlank
        private String password;

        public String getName() { return name; }
        public String getUsername() { return username; }
        public String getPassword() { return password; }

        public void setName(String name) { this.name = name; }
        public void setUsername(String username) { this.username = username; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class UpdateProfileRequest {
        private String name;
        private String username;

        public String getName() { return name; }
        public String getUsername() { return username; }

        public void setName(String name) { this.name = name; }
        public void setUsername(String username) { this.username = username; }
    }
}
