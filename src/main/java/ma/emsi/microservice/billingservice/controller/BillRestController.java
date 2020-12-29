package ma.emsi.microservice.billingservice.controller;

import ma.emsi.microservice.billingservice.data.Bill;
import ma.emsi.microservice.billingservice.repository.BillRepository;
import ma.emsi.microservice.billingservice.repository.ProductItemRepository;
import ma.emsi.microservice.billingservice.service.CustomerServiceClient;
import ma.emsi.microservice.billingservice.service.InventoryServiceClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillRestController{

    private final BillRepository billRepository;
    private final ProductItemRepository productItemRepository;
    private final CustomerServiceClient customerServiceClient;
    private final InventoryServiceClient inventoryServiceClient;

    public BillRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerServiceClient customerServiceClient, InventoryServiceClient inventoryServiceClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerServiceClient = customerServiceClient;
        this.inventoryServiceClient = inventoryServiceClient;
    }

    @GetMapping("/bills/full/{id}")
    Bill getBill(@PathVariable(name="id") Long id){
        Bill bill=billRepository.findById(id).get();
        bill.setCustomer(customerServiceClient.findCustomerById(bill.getCustomerID()));
        bill.setProductItems(productItemRepository.findByBillId(id));
        bill.getProductItems().forEach(pi->{
            pi.setProduct(inventoryServiceClient.findProductById(pi.getProductID()));
        });
        return bill; }

}