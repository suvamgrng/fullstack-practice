package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    final private ProductRepo repo;

    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public void addProducts(List<Product> product) {
         repo.saveAll(product);
    }

    public Product getProduct(int id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND
                        ));
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return repo.save(product);
    }

    public Product updateProduct(int productId, Product product, MultipartFile imageFile) throws IOException {
        if (!repo.existsById(productId)) {
            return null;
        }
        product.setId(productId);
        product.setImageDate(imageFile.getBytes());
        return repo.save(product);
    }
}
