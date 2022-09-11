package by.singularity.service;

import by.singularity.dto.ProductDto;
import by.singularity.entity.Product;
import by.singularity.entity.ProductStatus;
import by.singularity.entity.QProduct;
import by.singularity.exception.ProductException;
import by.singularity.mapper.ProductMapper;
import by.singularity.repository.ProductRepository;
import by.singularity.repository.queryUtils.QPredicate;
import by.singularity.service.utils.ParseUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public Product createProduct(ProductDto productDto) {
        Product product = productMapper.toModel(productDto);
        productRepository.save(product);
        return product;
    }

    public void updateProduct(ProductDto productDto, Long id) throws ProductException {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            throw new ProductException("product with id"+ id +" not found");
        }
        Product product = productOpt.get();
        Optional.ofNullable(productDto.getAmount()).ifPresent(product::setAmount);
        Optional.ofNullable(productDto.getProductStatus()).ifPresent(product::setProductStatus);
        productRepository.save(product);
        log.info("PRODUCT WITH ID {} UPDATED", id);
    }

    public Page<Product> getAllProducts(Map<String, String> params, Pageable pageable) {
        return productRepository.findAll(getFindingPredicate(params),pageable);
    }

    //todo пофиксить
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
        log.info("PRODUCT WITH ID {} DELETED", id);
    }


    public Product getProduct(Long id) throws ProductException {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            throw new ProductException("product with id " + id + "not found");
        }
        return productOpt.get();
    }

    private Predicate getFindingPredicate(Map<String,String> params) {
        return QPredicate.builder()
                .add(params.get("name"), QProduct.product.name::eq)
                .add(ParseUtils.parseInt(params.get("amount")), QProduct.product.amount::eq)
                .add(ParseUtils.parseEnum(params.get("productStatus"), ProductStatus.class), QProduct.product.productStatus::contains)
                .buildAnd();
    }

}