package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
         return  ResponseEntity.ok(service.getAllProducts());
    }

    @PostMapping("/products")
    public List<Product>  addProducts(@RequestBody List<Product> product) {
        service.addProducts(product);
        return product;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Product product = service.getProduct(id);

        if (product != null) {
            return ResponseEntity.ok(service.getProduct(id));
        }
        return ResponseEntity.notFound().build();
    }
}
