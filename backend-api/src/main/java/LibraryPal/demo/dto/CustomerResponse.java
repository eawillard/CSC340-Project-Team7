
package LibraryPal.demo.dto;

import LibraryPal.demo.entity.Customer;

public class CustomerResponse {

    private Long id;
    private String name;
    private String username;

    public CustomerResponse() {
    }

    public CustomerResponse(Long id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getUsername()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }
}
