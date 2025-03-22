package edu.icet.mos.service;

import edu.icet.mos.dto.Customer;
import edu.icet.mos.dto.Product;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);
    List<Product> getAll();
//    List<Product> getProductByType(String type);
    void updateProduct(Product product);
    void deleteProduct(Long id);
}
