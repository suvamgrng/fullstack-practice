package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            //  ↑ check name field                                  ↑ if name doesn't match, try next
            "LOWER(p.desc) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            //  ↑ check description field                            ↑ still no match? try next
            "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
        //  ↑ check category field — last chance!
    List<Product> searchProducts(@Param("keyword") String keyword);
}
