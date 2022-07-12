package com.cafe.facade.impl.product;

import com.cafe.dto.request.ProductInOrderRegistrationRequestDto;
import com.cafe.dto.request.ProductInOrderUpdateRequestDto;
import com.cafe.dto.request.ProductRegistrationRequestDto;
import com.cafe.dto.request.ProductUpdateRequestDto;
import com.cafe.dto.response.ProductInOrderRegistrationResponseDto;
import com.cafe.dto.response.ProductInOrderUpdateResponseDto;
import com.cafe.dto.response.ProductRegistrationResponseDto;
import com.cafe.dto.response.ProductUpdateResponseDto;
import com.cafe.dto.response.error.ErrorProductInOrderRegistrationResponseDto;
import com.cafe.dto.response.error.ErrorProductInOrderUpdateResponseDto;
import com.cafe.dto.response.error.ErrorProductRegistrationResponseDto;
import com.cafe.dto.response.error.ErrorProductUpdateResponseDto;
import com.cafe.entity.order.Order;
import com.cafe.entity.order.OrderStatusType;
import com.cafe.entity.product.Product;
import com.cafe.entity.product.ProductInOrder;
import com.cafe.facade.core.product.ProductFacade;
import com.cafe.mapper.product.*;
import com.cafe.service.core.order.OrderService;
import com.cafe.service.core.product.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ProductFacadeImpl implements ProductFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductFacadeImpl.class);
    private final ProductService productService;
    private final ProductInOrderService productInOrderService;
    private final OrderService orderService;
    private final ProductCreationParamsMapper productCreationParamsMapper;
    private final ProductRegistrationResponseDtoMapper productRegistrationResponseDtoMapper;
    private final ProductUpdateParamsMapper productUpdateParamsMapper;
    private final ProductUpdateResponseDtoMapper productUpdateResponseDtoMapper;
    private final ProductInOrderCreationParamsMapper productInOrderCreationParamsMapper;
    private final ProductInOrderRegistrationResponseDtoMapper productInOrderRegistrationResponseDtoMapper;
    private final ProductInOrderUpdateParamsMapper productInOrderUpdateParamsMapper;
    private final ProductInOrderUpdateResponseDtoMapper productInOrderUpdateResponseDtoMapper;

    public ProductFacadeImpl(ProductService productService,
                             ProductInOrderService productInOrderService,
                             OrderService orderService,
                             ProductCreationParamsMapper productCreationParamsMapper,
                             ProductRegistrationResponseDtoMapper productRegistrationResponseDtoMapper,
                             ProductUpdateParamsMapper productUpdateParamsMapper,
                             ProductUpdateResponseDtoMapper productUpdateResponseDtoMapper,
                             ProductInOrderCreationParamsMapper productInOrderCreationParamsMapper,
                             ProductInOrderRegistrationResponseDtoMapper productInOrderRegistrationResponseDtoMapper,
                             ProductInOrderUpdateParamsMapper productInOrderUpdateParamsMapper,
                             ProductInOrderUpdateResponseDtoMapper productInOrderUpdateResponseDtoMapper) {
        Assert.notNull(productService, "Product service should not be null");
        Assert.notNull(productInOrderService, "Product in order service should not be null");
        Assert.notNull(orderService, "Order service should not be null");
        Assert.notNull(productCreationParamsMapper, "Product registration request dto mapper should not be null");
        Assert.notNull(productRegistrationResponseDtoMapper, "Product registration response dto mapper should not be null");
        Assert.notNull(productUpdateParamsMapper, "Product update params mapper should not be null");
        Assert.notNull(productUpdateResponseDtoMapper, "Product update response dto mapper should not be null");
        Assert.notNull(productInOrderCreationParamsMapper, "Product in order creation params mapper should not be null");
        Assert.notNull(productInOrderRegistrationResponseDtoMapper, "Product in order registration response dto mapper should not be null");
        Assert.notNull(productInOrderUpdateParamsMapper, "Product in order update params mapper should not be null");
        Assert.notNull(productInOrderUpdateResponseDtoMapper, "Product in order update response dto mapper should not be null");
        this.productService = productService;
        this.productInOrderService = productInOrderService;
        this.orderService = orderService;
        this.productCreationParamsMapper = productCreationParamsMapper;
        this.productRegistrationResponseDtoMapper = productRegistrationResponseDtoMapper;
        this.productUpdateParamsMapper = productUpdateParamsMapper;
        this.productUpdateResponseDtoMapper = productUpdateResponseDtoMapper;
        this.productInOrderCreationParamsMapper = productInOrderCreationParamsMapper;
        this.productInOrderRegistrationResponseDtoMapper = productInOrderRegistrationResponseDtoMapper;
        this.productInOrderUpdateParamsMapper = productInOrderUpdateParamsMapper;
        this.productInOrderUpdateResponseDtoMapper = productInOrderUpdateResponseDtoMapper;
    }

    @Override
    public ProductRegistrationResponseDto registerProduct(ProductRegistrationRequestDto dto) {
        Assert.notNull(dto, "Product registration request dto should not be null");
        LOGGER.info("Registering a new product according to the product registration request dto - {}", dto);
        if(productService.findByName(dto.getName()).isPresent()) {
            return new ErrorProductRegistrationResponseDto(
                    List.of(String.format("Product with the name %s is already registered", dto.getName())),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        Product product = productService.create(productCreationParamsMapper.apply(dto));
        ProductRegistrationResponseDto responseDto = productRegistrationResponseDtoMapper.apply(product);
        LOGGER.info("Successfully registered a new product according to the product registration request dto - {}, response - {}", dto, responseDto);
        return responseDto;
    }

    @Override
    public ProductUpdateResponseDto updateProduct(ProductUpdateRequestDto dto) {
        Assert.notNull(dto, "Product update request dto should not be null");
        LOGGER.info("Updating a product according to the product update request dto - {}", dto);
        if(productService.findByName(dto.getOriginalName()).isEmpty()) {
            return new ErrorProductUpdateResponseDto(
                    List.of(String.format("Cannot update product named as %s because it does not exist.", dto.getOriginalName())),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        Product product = productService.updateProduct(productUpdateParamsMapper.apply(dto));
        ProductUpdateResponseDto responseDto = productUpdateResponseDtoMapper.apply(product);
        LOGGER.info("Successfully updated a product according to the product update request dto - {}, response - {}", dto, responseDto);
        return responseDto;
    }

    @Override
    public ProductInOrderRegistrationResponseDto registerProductInOrder(ProductInOrderRegistrationRequestDto dto) {
        Assert.notNull(dto, "Product in order registration request dto should not be null");
        LOGGER.info("Registering a new product according to the product in order registration request dto - {}", dto);
        Optional<Product> productOptional = productService.findByName(dto.getProductName());
        if(productOptional.isEmpty()) {
            return new ErrorProductInOrderRegistrationResponseDto(
                    List.of(String.format("No product named as: %s", dto.getProductName())),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        Product product = productOptional.get();
        Integer productAmount = product.getAmount();
        Integer productInOrderAmount = dto.getAmount();
        if(productAmount < productInOrderAmount) {
            return new ErrorProductInOrderRegistrationResponseDto(
                    List.of(String.format("Could not register a product in order because its amount(%d) exceeds the amount of the product(%d)", productInOrderAmount, productAmount)),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        Optional<Order> orderOptional = orderService.findById(dto.getOrderId());
        if(orderOptional.isEmpty()) {
            return new ErrorProductInOrderRegistrationResponseDto(
                    List.of(String.format("Could not register a product in order for an order with an id of %d because the order does not exist.", dto.getOrderId())),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        Order order = orderOptional.get();
        if(!order.getWaiter().getUsername().equals(dto.getWaiterUsername())) {
            return new ErrorProductInOrderRegistrationResponseDto(
                    List.of("Could not register a product in order since the order belongs to another user."),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        ProductInOrder productInOrder = productInOrderService.create(productInOrderCreationParamsMapper.apply(dto));
        productService.updateProduct(new ProductUpdateParams(
                product.getName(),
                product.getName(),
                product.getAmount() - dto.getAmount(),
                product.getPrice()
        ));
        ProductInOrderRegistrationResponseDto responseDto = productInOrderRegistrationResponseDtoMapper.apply(productInOrder);
        LOGGER.info("Successfully registered a new product in order according to the product in order registration dto - {}, resopnse - {}",
                dto,
                responseDto
        );
        return responseDto;
    }

    @Override
    public ProductInOrderUpdateResponseDto updateProductInOrder(ProductInOrderUpdateRequestDto dto) {
        Assert.notNull(dto, "Product in order update request dto should not be null");
        LOGGER.info("Updating a product in order according to the product in order update request dto - {}", dto);

        if(dto.getAmount() < 0) {
            return new ErrorProductInOrderUpdateResponseDto(
                    List.of(String.format("The amount must be a positive number, actual amount - %d", dto.getAmount())),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        if(productService.findByName(dto.getProductName()).isEmpty()) {
            return new ErrorProductInOrderUpdateResponseDto(
                    List.of(String.format("No product found named as %s", dto.getProductName())),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        Optional<Order> orderOptional = orderService.findById(dto.getOrderId());
        if(orderOptional.isEmpty()) {
            return new ErrorProductInOrderUpdateResponseDto(
                    List.of(String.format("No order found having an id of %d", dto.getOrderId())),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        Order order = orderOptional.get();
        if(order.getOrderStatusType() != OrderStatusType.OPEN) {
            return new ErrorProductInOrderUpdateResponseDto(
                    List.of(String.format("The order having an id of %d is not open, therefore cannot have a product in order attached to it", dto.getOrderId())),
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
        ProductInOrder productInOrder = productInOrderService.update(productInOrderUpdateParamsMapper.apply(dto));
        ProductInOrderUpdateResponseDto responseDto = productInOrderUpdateResponseDtoMapper.apply(productInOrder);
        LOGGER.info("Successfully updated product in order according to the request dto - {}, response - {}", dto, responseDto);
        return responseDto;
    }
}