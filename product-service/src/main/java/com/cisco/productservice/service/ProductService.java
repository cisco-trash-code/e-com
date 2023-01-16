package com.cisco.productservice.service;

import com.cisco.productservice.dto.ProductRequest;
import com.cisco.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    void createProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();
}
