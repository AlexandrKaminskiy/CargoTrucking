package by.singularity.service;

import by.singularity.dto.ProductDto;
import by.singularity.dto.ProductOwnerDto;
import by.singularity.entity.Product;
import by.singularity.entity.ProductOwner;
import by.singularity.entity.QProductOwner;
import by.singularity.exception.ProductOwnerException;
import by.singularity.mapper.impl.ProductOwnerMapper;
import by.singularity.repository.ProductOwnerRepository;
import by.singularity.repository.queryUtils.QPredicate;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductOwnerService {
    private final ProductOwnerRepository productOwnerRepository;
    private final ProductOwnerMapper productOwnerMapper;
    private final ProductService productService;

    @Transactional
    public Long createProductOwner(ProductOwnerDto productOwnerDto) {
        ProductOwner productOwner = productOwnerMapper.toModel(productOwnerDto);
        Set<Product> products = productOwnerDto.getProducts()
                .stream()
                .map(productService::createProduct)
                .collect(Collectors.toSet());
        productOwner.setProducts(products);
        productOwnerRepository.save(productOwner);
        log.info("PRODUCT OWNER WITH ID {} CREATED", productOwner.getId());
        return productOwner.getId();
    }

    public void updateProductOwner(ProductOwnerDto productOwnerDto, Long id) throws ProductOwnerException {
        ProductOwner productOwner = productOwnerRepository.findById(id)
                .orElseThrow(()->new ProductOwnerException("product owner with id" + id + "not found"));
        Optional.ofNullable(productOwnerDto.getName()).ifPresent(productOwner::setName);
        Optional.ofNullable(productOwnerDto.getProducts())
                .ifPresent((productDtos)-> {
                    productOwner.getProducts().stream()
                            .map(Product::getId)
                            .forEach(productService::deleteProduct);
                    Set<Product> products = productDtos
                            .stream()
                            .map(productService::createProduct)
                            .collect(Collectors.toSet());
                    productOwner.setProducts(products);
                });
        productOwnerRepository.save(productOwner);
        log.info("PRODUCT OWNER WITH ID {} UPDATED", productOwner.getId());
    }

    public Page<ProductOwner> getAllProductOwners(Pageable pageable, Map<String,String> params) {
        return productOwnerRepository.findAll(getFindingPredicate(params),pageable);
    }

    public void deleteProductOwner(Long id) {
        productOwnerRepository.deleteById(id);
        log.info("PRODUCT OWNER WITH ID {} DELETED", id);
    }

    public ProductOwner getProductOwner(Long id) throws ProductOwnerException {
        return productOwnerRepository.findById(id)
                .orElseThrow(()->new ProductOwnerException("product owner with id " + id + "not found"));
    }

    private Predicate getFindingPredicate(Map<String,String> params) {
        return QPredicate.builder()
                .add(params.get("name"), QProductOwner.productOwner.name::eq)
                .buildAnd();
    }


    public Optional<ProductOwner> getByProductsContaining(Set<ProductDto> productDtos) {
        List<ProductOwner> productOwners = productOwnerRepository.findAll();
//        return productOwners.stream()
//                .filter(productOwner ->
//                    productOwner.getProducts().stream()
//                            .anyMatch(product -> productDtos.stream()
//                                        .anyMatch((productDto -> productDto.getName().equals(product.getName()) &&
//                                                productDto.getAmount() <= product.getAmount()))))
//                .findFirst();
        return null;
    }
}