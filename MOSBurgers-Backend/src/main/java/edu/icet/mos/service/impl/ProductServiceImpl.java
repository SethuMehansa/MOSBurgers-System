package edu.icet.mos.service.impl;

import edu.icet.mos.dto.Product;
import edu.icet.mos.entity.ProductEntity;
import edu.icet.mos.repository.ProductRepository;
import edu.icet.mos.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public void addProduct(Product product) {
        ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
        System.out.println("Mapped ProductEntity: " + productEntity);  // Debug log
        productRepository.save(productEntity);
    }

    @Override
    public List<Product> getAll() {
        List<ProductEntity> productEntities = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        productEntities.forEach(productEntity -> products.add(modelMapper.map(productEntity, Product.class)));
        return products;
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.save(modelMapper.map(product, ProductEntity.class));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
