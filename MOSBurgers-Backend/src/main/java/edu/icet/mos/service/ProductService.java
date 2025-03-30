package edu.icet.mos.service;

import edu.icet.mos.dto.Product;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);
    List<Product> getAll();
    void updateProduct(Product product);
    void deleteProduct(Long id);
}
