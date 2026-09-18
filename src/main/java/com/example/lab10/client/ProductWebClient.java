package com.example.lab10.client;

import com.example.lab10.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ProductWebClient — Reactive HTTP Client
 *
 * ใช้ WebClient สำหรับส่ง HTTP Request ไปยัง Product API
 */
@Component
public class ProductWebClient {

    // WebClient ชี้ไปยัง Server ของเรา
    private final WebClient client =
            WebClient.create("http://localhost:8080");

    /**
     * GET /products/{id}
     * ดึง Product ตาม id
     */
    public Mono<Product> getProductById(String id) {
        return client.get()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(Product.class);
    }

    /**
     * GET /products
     * ดึง Product ทั้งหมด
     */
    public Flux<Product> getAllProducts() {
        return client.get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(Product.class);
    }

    /**
     * POST /products
     * เพิ่ม Product ใหม่
     */
    public Mono<Product> createProduct(Product product) {
        return client.post()
                .uri("/products")
                .bodyValue(product)
                .retrieve()
                .bodyToMono(Product.class);
    }

    /**
     * DELETE /products/{id}
     * ลบ Product ตาม id
     */
    public Mono<Void> deleteProduct(String id) {
        return client.delete()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    /**
     * GET /products/category/{category}
     * ดึง Product ตาม category
     */
    public Flux<Product> getByCategory(String category) {
        return client.get()
                .uri("/products/category/{category}", category)
                .retrieve()
                .bodyToFlux(Product.class);
    }

    /**
     * GET /products/{id}/price
     * ดึงราคาหลังส่วนลด
     */
    public Mono<Double> getDiscountedPrice(String id) {
        return client.get()
                .uri("/products/{id}/price", id)
                .retrieve()
                .bodyToMono(Double.class)
                .doOnNext(price ->
                        System.out.println("Price: " + price));
    }
}