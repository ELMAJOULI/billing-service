package ma.emsi.microservice.billingservice;

import ma.emsi.microservice.billingservice.data.Bill;
import ma.emsi.microservice.billingservice.data.Customer;
import ma.emsi.microservice.billingservice.data.ProductItem;
import ma.emsi.microservice.billingservice.repository.BillRepository;
import ma.emsi.microservice.billingservice.repository.ProductItemRepository;
import ma.emsi.microservice.billingservice.service.CustomerServiceClient;
import ma.emsi.microservice.billingservice.service.InventoryServiceClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {
    public static void main(String[] args) {SpringApplication.run(BillingServiceApplication.class, args); }
    @Bean
    CommandLineRunner start(BillRepository billRepository, ProductItemRepository productItemRepository,
                            InventoryServiceClient inventoryServiceClient, CustomerServiceClient customerServiceClient){

        return args -> {

            Bill bill=new Bill();
            bill.setBillingDate(new Date());
            Customer customer=customerServiceClient.findCustomerById(1L);
            bill.setCustomerID(customer.getId());
            billRepository.save(bill);
            inventoryServiceClient.findAll().getContent().forEach(p->{

                productItemRepository.save(new ProductItem(null,null,p.getId(),p.getPrice(),(int)(1+Math.random()*1000),bill));

            });

        };

    }
}