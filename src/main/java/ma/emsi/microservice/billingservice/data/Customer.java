package ma.emsi.microservice.billingservice.data;

import lombok.Data;

@Data
public class Customer {
    private Long id;
    private String name;
    private String email;
}
