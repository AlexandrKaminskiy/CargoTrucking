package by.singularity.service;

import by.singularity.dto.ProductOwnerDto;
import by.singularity.entity.Product;
import by.singularity.entity.ProductOwner;
import by.singularity.exception.ProductOwnerException;
import by.singularity.mapper.ProductOwnerMapper;
import by.singularity.repository.ProductOwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        //todo
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
        Optional<ProductOwner> productOwnerOpt = productOwnerRepository.findById(id);
        if (productOwnerOpt.isEmpty()) {
            throw new ProductOwnerException("product owner with id" + id + "not found");
        }
        ProductOwner productOwner = productOwnerOpt.get();
        Optional.ofNullable(productOwnerDto.getName()).ifPresent(productOwner::setName);
        Optional.ofNullable(productOwnerDto.getProducts())
                .ifPresent((productDtos)-> {
                    productService.getAllProducts()
                            .forEach((product)->
                                    productService.deleteProduct(product.getId()));
                    //todo delete by product owner id
                    Set<Product> products = productDtos
                            .stream()
                            .map(productService::createProduct)
                            .collect(Collectors.toSet());
                    productOwner.setProducts(products);
                });
        productOwnerRepository.save(productOwner);
        log.info("PRODUCT OWNER WITH ID {} UPDATED", productOwner.getId());
    }

    public List<ProductOwner> getAllProductOwners() {
        //todo
        return productOwnerRepository.findAll();
    }

    public void deleteProductOwner(Long id) {
        productOwnerRepository.deleteById(id);
        log.info("PRODUCT OWNER WITH ID {} DELETED", id);
    }

    public ProductOwner getProductOwner(Long id) throws ProductOwnerException {
        Optional<ProductOwner> productOwnerOpt = productOwnerRepository.findById(id);
        if (productOwnerOpt.isEmpty()) {
            throw new ProductOwnerException("product owner with id " + id + "not found");
        }
        return productOwnerOpt.get();
    }

}