package com.example.webshop.controller;

import com.example.webshop.model.dto.ProductDto;
import com.example.webshop.model.dto.UserDto;
import com.example.webshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id){
        return  ResponseEntity.status(HttpStatus.OK).body(productService.getProduct(id));
    }

    @GetMapping("/find")
    public ResponseEntity<List<ProductDto>> getProductsByName(@RequestParam ("name") String name){

        return ResponseEntity.status(HttpStatus.OK).body(productService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        return ResponseEntity.status(HttpStatus.OK).body(productService.createProduct(productDto));
    }

}
