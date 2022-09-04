package by.singularity.service;

import by.singularity.dto.ProductDto;
import by.singularity.entity.Product;
import by.singularity.mapper.ProductMapper;
import by.singularity.repository.jparepo.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductJpaRepository productJpaRepository;
    public Product createProduct(ProductDto productDto) {
        Product product = productMapper.toModel(productDto);
        productJpaRepository.save(product);
        return product;
    }
}
