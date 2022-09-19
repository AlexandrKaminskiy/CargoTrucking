package by.singularity.service;

import by.singularity.dto.InvoiceDto;
import by.singularity.entity.*;
import by.singularity.exception.*;
import by.singularity.mapper.impl.InvoiceMapper;
import by.singularity.repository.InvoiceRepository;
import by.singularity.repository.queryUtils.QPredicate;
import by.singularity.service.utils.ParseUtils;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceService {
    private final InvoiceMapper invoiceMapper;
    private final InvoiceRepository invoiceRepository;
    private final ProductOwnerService productOwnerService;
    private final StorageService storageService;
    private final UserService userService;
    private final ProductService productService;

    @Transactional
    public Invoice createInvoice(InvoiceDto invoiceDto, HttpServletRequest request) throws ProductOwnerException, UserException, StorageException, ProductException, ClientException, InvoiceException {
        if (invoiceRepository.existsById(invoiceDto.getNumber())) {
            throw new InvoiceException("invoice with id " + invoiceDto.getNumber() + " exists");
        }
        Storage storage = storageService.getStorage(invoiceDto.getStorageId());
        if (!storage.getClient().getIsActive()) {
            throw new ClientException("client with id " + storage.getClient().getId() + " is not active");
        }
        User driver = userService.getById(invoiceDto.getDriverId());
        if (!driver.getRoles().contains(Role.DRIVER)) {
            throw new UserException("user with id " + driver.getId() + " is not driver");
        }
        User creator = userService.getUserByAuthorization(request);
        Set<Product> products = getProducts(invoiceDto);
        Invoice invoice = invoiceMapper.toModel(invoiceDto);
        invoice.setCreator(creator);
        invoice.setProducts(products);
        invoice.setCreationDate(new Date());
        invoice.setStatus(Collections.singleton(InvoiceStatus.MADE_OUT));
        invoiceRepository.save(invoice);
        log.info("INVOICE WITH NUMBER {} CREATED", invoice.getNumber());
        return invoice;
    }

    @Transactional
    public void createInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Transactional
    public void updateInvoice(InvoiceDto invoiceDto, String number) throws InvoiceException, ProductOwnerException, UserException, StorageException, ProductException {
        Invoice invoice = invoiceRepository.findById(number)
                .orElseThrow(()->new InvoiceException("invoice with number " + number + "not found"));
        invoice.getProducts().forEach(product -> productService.deleteProduct(product.getId()));
        Invoice updatedInvoice = invoiceMapper.toModel(invoiceDto);
        invoice.setDriver(updatedInvoice.getDriver());
        invoice.setStorage(updatedInvoice.getStorage());
        invoice.setProductOwner(updatedInvoice.getProductOwner());
        invoice.setProducts(getProducts(invoiceDto));
        invoiceRepository.save(invoice);
        log.info("INVOICE WITH NUMBER {} UPDATED", number);
    }

    public Page<Invoice> getAllInvoices(Map<String,String> params, Pageable pageable) {
        return invoiceRepository.findAll(getFindingPredicate(params), pageable);
    }

    @Transactional
    public void deleteInvoice(String number) {
        invoiceRepository.deleteById(number);
        log.info("INVOICE WITH NUMBER {} DELETED", number);
    }

    public Invoice getInvoice(String number) throws InvoiceException {
        return invoiceRepository.findById(number)
                .orElseThrow(()->new InvoiceException("invoice with number " + number + "not found"));

    }

    private Predicate getFindingPredicate(Map<String, String> params) {
        return QPredicate.builder()
                .add(params.get("number"), QInvoice.invoice.number::eq)
                .add(ParseUtils.parseDate(params.get("beforeCreationDate")), QInvoice.invoice.creationDate::goe)
                .add(ParseUtils.parseDate(params.get("afterCreationDate")), QInvoice.invoice.creationDate::loe)
                .add(ParseUtils.parseDate(params.get("beforeVerifiedDate")), QInvoice.invoice.verifiedDate::goe)
                .add(ParseUtils.parseDate(params.get("afterVerifiedDate")), QInvoice.invoice.verifiedDate::loe)
                .add(ParseUtils.parseEnum(params.get("status"),InvoiceStatus.class),QInvoice.invoice.status::contains)
                .buildAnd();
    }

    private Set<Product> getProducts(InvoiceDto invoiceDto) throws ProductOwnerException, ProductException {
        ProductOwner productOwner = productOwnerService.getProductOwner(invoiceDto.getProductOwnerId());
        Set<Product> productsFromInvoice = invoiceDto.getProducts().stream()
                .map(productDto -> {
                    Product product = new Product();
                    product.setName(productDto.getName());
                    product.setAmount(productDto.getAmount());
                    return product;
                }).collect(Collectors.toSet());
        LinkedHashSet<Product> products = new LinkedHashSet<>(productsFromInvoice);
        Set<Product> nessProducts = productOwner.getProducts().stream()
                .filter(product -> products.stream()
                        .anyMatch(p -> {
                            boolean isMatch = p.getName().equals(product.getName())
                                    && product.getAmount() >= p.getAmount()
                                    && product.getProductStatus().size() == 0;
                            if (isMatch) {
                                p.setProductStatus(Collections.singleton(ProductStatus.RESERVED));
                                p.setProductOwner(product.getProductOwner());
                                p.setCreator(product.getCreator());
                            }
                            return isMatch;
                        }))
                .collect(Collectors.toSet());
        if (nessProducts.size() != productsFromInvoice.size()) {
            throw new ProductException("product owner with id " + productOwner.getId() + " doesn't have this products");
        }
        return products;
    }

    @Transactional
    public void validateInvoice(String number) throws InvoiceException {
        Invoice invoice = invoiceRepository.findById(number)
                .orElseThrow(()->new InvoiceException("invoice with number " + number + "not found"));
        invoice.setStatus(new HashSet<>(Collections.singleton(InvoiceStatus.VERIFICATION_COMPLETE)));
        invoice.setVerifiedDate(new Date());
        invoiceRepository.save(invoice);
        log.info("INVOICE {} IS ACTIVE", invoice.getNumber());
    }
}