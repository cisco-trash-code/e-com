package com.cisco.inventoryservice.service;

import com.cisco.inventoryservice.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> isInStock(List<String> skuCodeList);
}
