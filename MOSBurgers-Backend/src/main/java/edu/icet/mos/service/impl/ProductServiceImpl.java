package edu.icet.mos.service.impl;

import edu.icet.mos.dto.Product;
import edu.icet.mos.entity.ProductEntity;
import edu.icet.mos.repository.ProductRepository;
import edu.icet.mos.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public void addProduct(Product product) {
        ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
        if (product.getImage() != null && !product.getImage().isEmpty()) {
            productEntity.setImage(Base64.getDecoder().decode(product.getImage()));
        }
        productRepository.save(productEntity);
    }

    @Override
    public List<Product> getAll() {
        List<ProductEntity> productEntities = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        productEntities.forEach(productEntity -> {
            Product product = modelMapper.map(productEntity, Product.class);
            if (productEntity.getImage() != null) {
                product.setImage(Base64.getEncoder().encodeToString(productEntity.getImage()));
            }
            products.add(product);
        });
        return products;
    }

    @Override
    public void updateProduct(Product updatedProduct) {
        ProductEntity existingProduct = productRepository.findById(updatedProduct.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Update fields only if they are provided in updatedProduct
        if (updatedProduct.getName() != null) {
            existingProduct.setName(updatedProduct.getName());
        }
        if (updatedProduct.getPrice() != null) {
            existingProduct.setPrice(updatedProduct.getPrice());
        }
        // Only update image if a new one is provided
        if (updatedProduct.getImage() != null && !updatedProduct.getImage().isEmpty()) {
            existingProduct.setImage(Base64.getDecoder().decode(updatedProduct.getImage()));
        }
        productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public Product getProductById(Long id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        Product product = modelMapper.map(productEntity, Product.class);
        if (productEntity.getImage() != null) {
            product.setImage(Base64.getEncoder().encodeToString(productEntity.getImage()));
        }
        return product;
    }
}