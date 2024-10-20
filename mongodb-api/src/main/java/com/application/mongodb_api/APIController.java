package com.application.mongodb_api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.application.mongodb_api.aggregation.ClientsByOrders;
import com.application.mongodb_api.aggregation.OrderCount;
import com.application.mongodb_api.aggregation.OrderResponse;
import com.application.mongodb_api.aggregation.TopProducts;
import com.application.mongodb_api.aggregation.TotalValue;
import com.application.mongodb_api.dto.*;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;





@RestController
public class APIController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    private final MongoTemplate mongoTemplate;

    public APIController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostMapping("/clients")
    public ResponseEntity<String> addClient(@RequestBody Client client) {
        
        if(client.getEmail() == null || client.getEmail().isBlank()){
            return new ResponseEntity<>("Invalid input, email is missing or blank", HttpStatus.BAD_REQUEST);
        }
        if(client.getName() == null || client.getName().isBlank()){
            return new ResponseEntity<>("Invalid input, Name is missing or blank", HttpStatus.BAD_REQUEST);
        }

        clientRepository.save(client);
        List<Client> latestClient = clientRepository.findTopByOrderByIdDesc();
        /*
        int clientID = latestClient.getFirst().getCounterPartOfHexID();

        System.out.println(latestClient);

         */
        return new ResponseEntity<>("Client registered with the ID: " + latestClient.getFirst().getId(), HttpStatus.OK); //returns 200 -> OK


    }

    @GetMapping("/clients/{clientID}")
    public ResponseEntity<?> getClientByID(@PathVariable String clientID){
        Optional<Client> client = clientRepository.findById(clientID);
         
        if(client.isPresent()){
            return new ResponseEntity<>(clientRepository.findById(clientID), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Client with the ID " + clientID + " was not found.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/clients/{clientID}")
    public ResponseEntity<?> deleteClientByID(@PathVariable String clientID){
        Optional<Client> client = clientRepository.findById(clientID);
        if(client.isPresent()){
            clientRepository.deleteById(clientID);
            return new ResponseEntity<>("Client with the ID" + clientID + "was deleted successfully", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Client with the ID " + clientID + " was not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/products")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {

        if(product.getName() == null || product.getName().isBlank()){
            return new ResponseEntity<>("Invalid input, email is missing or blank", HttpStatus.BAD_REQUEST);
        }
        if(product.getPrice() == 0){
            return new ResponseEntity<>("Invalid input, price is missing", HttpStatus.BAD_REQUEST);
        }

        productRepository.save(product);

        return new ResponseEntity<>("Product registered(or appended) with the ID: " + product.getId(), HttpStatus.OK); //returns 200 -> OK

    }
    // Optionally implement category's
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return  productRepository.findAll();
    }

    @GetMapping("/products/{productID}")
    public ResponseEntity<?> getProductByID(@PathVariable String productID){
        Optional<Product> product = productRepository.findById(productID);
        if(product.isPresent()){
            return new ResponseEntity<>(productRepository.findById(productID), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Product with the ID " + productID + " was not found.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/products/{productID}")
    public ResponseEntity<?> deleteProductByID(@PathVariable String productID){
        Optional<Product> product = productRepository.findById(productID);
        if(product.isPresent()){
            productRepository.deleteById(productID);
            return new ResponseEntity<>("Product with the ID" + productID + " was deleted successfully", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Product with the ID " + productID + " was not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/orders")
    public ResponseEntity<String> addOrder(@RequestBody Order order){

        
        if(order.getClientID() == null || order.getClientID().isBlank()){
            return new ResponseEntity<>("Invalid input, client ID is missing", HttpStatus.BAD_REQUEST);
        }
        if(order.getItems().isEmpty()){
            return new ResponseEntity<>("Invalid input, order items are missing", HttpStatus.BAD_REQUEST);
        }
        Optional<Client> client = clientRepository.findById(order.getClientID());

        if(client.isEmpty()){
            return new ResponseEntity<>("Client with the ID " + order.getClientID() + " was not found.", HttpStatus.NOT_FOUND);
        }
        orderRepository.save(order);

        for(OrderItem item : order.getItems()){
            Optional<Product> product = productRepository.findById(item.getProductId());
            if (product.isEmpty()){
                return new ResponseEntity<>("Product with ID " + item.getProductId() + " was not found.", HttpStatus.NOT_FOUND);
            }
}
        return new ResponseEntity<>("Order created with the ID: " + order.getID(), HttpStatus.OK); //returns 200 -> OK

    }

    @GetMapping("/clients/{clientID}/orders")
    public ResponseEntity<?> getClientOrders(@PathVariable String clientID) {

        Optional<Client> client = clientRepository.findById(clientID);
        if(client.isEmpty()) {
            return new ResponseEntity<>("Client with ID " + clientID + " was not found.", HttpStatus.NOT_FOUND);
        }

        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("clientID").is(clientID)),
            Aggregation.unwind("items"),
            Aggregation.group("clientID")
                .first("clientID").as("clientID")
                .push("items").as("items"),
            Aggregation.project()
                .and("clientID").as("clientID")
                .and("items").as("items")
        );

        AggregationResults<OrderResponse> result = mongoTemplate.aggregate(aggregation, "order", OrderResponse.class);
        List<OrderResponse> aggregatedOrders = result.getMappedResults(); 

        if (aggregatedOrders.isEmpty()) {
            return new ResponseEntity<>("No orders found for client with ID " + clientID, HttpStatus.NOT_FOUND);
        }
            
        return new ResponseEntity<>(aggregatedOrders, HttpStatus.OK);
    }

    @GetMapping("/statistics/top/clients")
    public ResponseEntity<?> getTopClientsByOrders() {      
        if (clientRepository.count() == 0) {
            return new ResponseEntity<>("No clients found in the database.", HttpStatus.NOT_FOUND);
        }
        if (orderRepository.count() == 0) {
            return new ResponseEntity<>("No orders found in the database.", HttpStatus.NOT_FOUND);
        }

        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.group("clientID").count().as("totalOrders"), // group by clientID and count orders
            Aggregation.sort(Sort.Direction.DESC, "totalOrders"),
            Aggregation.limit(10),
            Aggregation.project().and("_id").as("clientID").andInclude("totalOrders")        
        );  

        AggregationResults<ClientsByOrders> results = mongoTemplate.aggregate(aggregation, "order", ClientsByOrders.class);

        return new ResponseEntity<>(results.getMappedResults(), HttpStatus.OK);
    }
    
    @GetMapping("/statistics/top/products")
    public ResponseEntity<?> getTopProductsByQuanity() {

        if (clientRepository.count() == 0) {
            return new ResponseEntity<>("No clients found in the database.", HttpStatus.NOT_FOUND);
        }
        if (orderRepository.count() == 0) {
            return new ResponseEntity<>("No orders found in the database.", HttpStatus.NOT_FOUND);
        }
    
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.unwind("items"),
            Aggregation.group("items.productId") // group by productId
                .sum("items.quantity") // sum the quantities of each product
                .as("quantity"), 
            Aggregation.sort(Sort.Direction.DESC, "quantity"),
            Aggregation.limit(10),
            Aggregation.project().and("_id").as("productId").andInclude("quantity")
        );

        AggregationResults<TopProducts> results = mongoTemplate.aggregate(aggregation, "order", TopProducts.class);

        return new ResponseEntity<>(results.getMappedResults(), HttpStatus.OK);
    }

    @GetMapping("/statistics/orders/total")
    public ResponseEntity<?> getNumberOfOrdersPlaced() {
        if (orderRepository.count() == 0) {
            return new ResponseEntity<>("No orders found in the database.", HttpStatus.NOT_FOUND);
        }

        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.group().count().as("totalOrders")
        );

        AggregationResults<OrderCount> results = mongoTemplate.aggregate(aggregation, "order", OrderCount.class);
        
        return new ResponseEntity<>(results.getMappedResults(), HttpStatus.OK);
    }

    @GetMapping("/statistics/orders/totalValue")
    public ResponseEntity<?> getTotalValueOfOrders() {
        if (orderRepository.count() == 0) {
            return new ResponseEntity<>("No orders found in the database.", HttpStatus.NOT_FOUND);
        }
        if (productRepository.count() == 0) {
            return new ResponseEntity<>("No products found in the database.", HttpStatus.NOT_FOUND);
        }
            
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.unwind("items"),
            Aggregation.lookup("product", "items.productId", "_id", "productDetails"),  // join order collection with product collection
            Aggregation.unwind("productDetails"),
            Aggregation.project().and("items.quantity").multiply("productDetails.price").as("totalValue"),
            Aggregation.group().sum("totalValue").as("totalValue")  
        );

        AggregationResults<TotalValue> results = mongoTemplate.aggregate(aggregation, "order", TotalValue.class);
        
        return new ResponseEntity<>(results.getMappedResults(), HttpStatus.OK);
    }

}