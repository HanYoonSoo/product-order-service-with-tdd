package com.example.productorderservice.product;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ProductService {
    private ProductPort productPort;

    ProductService(ProductPort productPort) {
        this.productPort = productPort;
    }

    @PostMapping("/products")
    @Transactional
    public ResponseEntity<Void> addProduct(@RequestBody AddProductRequest request) {
        final Product product = new Product(request.name(), request.price(), request.discountPolicy());

        productPort.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public GetProductResponse getProduct(long productId) {
        final Product product = productPort.getProduct(productId);

        return new GetProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDiscountPolicy()
        );
    }
}
