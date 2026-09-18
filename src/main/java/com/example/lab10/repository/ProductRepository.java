package com.example.lab10.repository;

import com.example.lab10.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ProductRepository — In-memory Reactive Repository
 *
 * ใช้ ConcurrentHashMap สำหรับเก็บข้อมูลในหน่วยความจำ
 * และใช้ Mono / Flux สำหรับ Reactive Programming
 */
public class ProductRepository {

    // In-memory storage
    private final Map<String, Product> store = new ConcurrentHashMap<>();

    // ข้อมูลตัวอย่าง
    public ProductRepository() {

        store.put("1", new Product(
                "1",
                "iPhone 15 Pro (673380409-0 SEC 3)",
                "Electronics",
                "Apple",
                50,
                39900.0,
                "MEMBER"
        ));

        store.put("2", new Product(
                "2",
                "MacBook Air M3",
                "Electronics",
                "Apple",
                20,
                49900.0,
                "NONE"
        ));

        store.put("3", new Product(
                "3",
                "Samsung Galaxy S24",
                "Electronics",
                "Samsung",
                30,
                29900.0,
                "SEASONAL"
        ));
    }

    /**
     * หา Product ตาม id
     * ถ้าไม่พบจะคืน Mono.empty()
     */
    public Mono<Product> findById(String id) {

        Product product = store.get(id);

        if (product == null) {
            return Mono.empty();
        }

        return Mono.just(product);
    }

    /**
     * ดึง Product ทั้งหมด
     */
    public Flux<Product> findAll() {
        return Flux.fromIterable(store.values());
    }

    /**
     * บันทึก Product
     */
    public Mono<Product> save(Product product) {

        store.put(product.getId(), product);

        return Mono.just(product);
    }

    /**
     * ลบ Product ตาม id
     */
    public Mono<Void> deleteById(String id) {

        store.remove(id);

        return Mono.empty();
    }

    /**
     * ค้นหา Product ตาม category
     */
    public Flux<Product> findByCategory(String category) {

        return findAll()
                .filter(product ->
                        product.getCategory().equalsIgnoreCase(category)
                );
    }
}