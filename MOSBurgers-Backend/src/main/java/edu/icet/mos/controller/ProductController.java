package edu.icet.mos.controller;

import edu.icet.mos.dto.Product;
import edu.icet.mos.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {

    final ProductService productService;

    @PostMapping("/add-product")
    public ResponseEntity<Void> addProduct(@RequestPart Product product, @RequestPart MultipartFile image) throws IOException {
        try {
            if (image != null && !image.isEmpty()) {
                product.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }
            productService.addProduct(product);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/get-all")
    public ResponseEntity<List<Product>> getAll() {
        try {
            List<Product> products = productService.getAll();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update-product/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") Product updatedProduct,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        try {
            // Get the existing product from the database
            Product existingProduct = productService.getProductById(id);

            // Update fields (excluding image if not provided)
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            // ... update other fields as needed

            // Only update image if a new one is provided
            if (image != null && !image.isEmpty()) {
                existingProduct.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            }
            // Otherwise, the existing image remains unchanged

            productService.updateProduct(existingProduct);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
