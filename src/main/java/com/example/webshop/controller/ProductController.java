package com.example.webshop.controller;

import com.example.webshop.model.dto.ProductDto;
import com.example.webshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
    }

    @GetMapping("/page")
    public ResponseEntity<List<ProductDto>> getProductsPage(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsPage(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProduct(id));
    }

    @GetMapping("/find")
    public ResponseEntity<List<ProductDto>> getProductsByName(@RequestParam String name) {

        return ResponseEntity.status(HttpStatus.OK).body(productService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.createProduct(productDto));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId, @RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(productId, productDto));

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
