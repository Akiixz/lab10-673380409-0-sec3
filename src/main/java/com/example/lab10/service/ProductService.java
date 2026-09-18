package com.example.lab10.service;

import com.example.lab10.model.Product;
import com.example.lab10.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * ProductService — Business Logic Layer
 *
 * รับ request จาก Controller
 * → ประมวลผล Business Logic
 * → เรียก Repository
 * → คืนผลแบบ Mono / Flux
 */
@Service
public class ProductService {

    // Constructor Injection
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    /**
     * ดึง Product ตาม id
     * ถ้าไม่พบให้เกิด RuntimeException
     */
    public Mono<Product> getById(String id) {
        return repository.findById(id)
                .switchIfEmpty(
                        Mono.error(
                                new RuntimeException("Product not found: " + id)
                        )
                );
    }

    /**
     * ดึง Product ทั้งหมด
     */
    public Flux<Product> getAll() {
        return repository.findAll();
    }

    /**
     * บันทึก Product
     * ถ้าไม่มี id จะสร้าง id ด้วย UUID
     */
    public Mono<Product> save(Product product) {

        if (product.getId() == null) {
            product.setId(UUID.randomUUID().toString());
        }

        return repository.save(product);
    }

    /**
     * ลบ Product ตาม id
     */
    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }

    /**
     * ค้นหา Product ตาม category
     */
    public Flux<Product> getByCategory(String category) {
        return repository.findByCategory(category);
    }

    /**
     * คำนวณราคาหลังส่วนลด
     */
    public Mono<Double> getDiscountedPrice(String id) {
        return getById(id)
                .map(product -> product.getDiscountedPrice());
    }
}