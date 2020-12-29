package ma.emsi.microservice.billingservice.data;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private double price;
}
