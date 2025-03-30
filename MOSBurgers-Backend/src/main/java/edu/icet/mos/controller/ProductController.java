package edu.icet.mos.controller;

import edu.icet.mos.dto.Product;
import edu.icet.mos.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController{

    final ProductService productService;

    @PostMapping("/add-product")
    public void addProduct(@RequestPart Product product, @RequestPart MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            product.setImage(Base64.getEncoder().encodeToString(image.getBytes())); // Convert to Base64 string
        }

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
