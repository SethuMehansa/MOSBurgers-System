package edu.icet.mos.controller;

import edu.icet.mos.dto.Product;
import edu.icet.mos.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController{

    final ProductService productService;

    @PostMapping("/add-product")
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    @GetMapping("/get-all")
    public List<Product> getAll() {
        return productService.getAll();
    }

    @PutMapping("/update-product/{id}")
    public void updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
    }

    @DeleteMapping("/delete-product/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
