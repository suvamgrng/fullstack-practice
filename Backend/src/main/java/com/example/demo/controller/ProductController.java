package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @PostMapping("/products")
    public List<Product> addProducts(@RequestBody List<Product> product) {
        service.addProducts(product);
        return product;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        return ResponseEntity.ok(service.getProduct(id));
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile) throws IOException {
        Product product1 = service.addProduct(product, imageFile);

        if (product1 != null) {
            return ResponseEntity.status(201).body(product1);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Not added");
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getProductImageById(@PathVariable int productId) {
        Product product = service.getProduct(productId);
        byte[] imageFile = product.getImageDate();
        return ResponseEntity.ok(imageFile);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable int productId,
                                                 @RequestPart("product") Product product,
                                                 @RequestPart("imageFile") MultipartFile imageFile) throws IOException {
        Product updated = service.updateProduct(productId, product, imageFile);

        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int productId) {
        boolean deleted = service.deleteProduct(productId);

        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("products/search")
    public ResponseEntity<List<Product>> searchProductById(@RequestParam String keyword) {
        System.out.println("Searching with " + keyword);
        List<Product> products = service.searchProducts(keyword);
        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(products);
    }
}
