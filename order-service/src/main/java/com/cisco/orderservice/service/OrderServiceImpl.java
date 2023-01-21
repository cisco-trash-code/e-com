package com.cisco.orderservice.service;

import com.cisco.orderservice.dto.InventoryResponse;
import com.cisco.orderservice.dto.OrderLineItemsDTO;
import com.cisco.orderservice.dto.OrderRequest;
import com.cisco.orderservice.model.Order;
import com.cisco.orderservice.model.OrderLineItems;
import com.cisco.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;
    @Override
    public String placeOrder(OrderRequest orderRequest) {
//        Order order = new Order();
//        order.setOrderNumber(UUID.randomUUID().toString());
//
//        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDTOList().stream()
//                .map(this::mapToDto).toList();
//
//        order.setOrderLineItemsList(orderLineItems);
//
//        List<String> skuCodes = order.getOrderLineItemsList().stream()
//                .map(OrderLineItems::getSkuCode)
//                .toList();
//
//        // call inventory service and place order if product is in stock
//        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
//                .uri("http://localhost:8082/api/inventory/",
//                        uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
//                .retrieve()
//                .bodyToMono(InventoryResponse[].class)
//                .block();
//
//        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);
//
//        if(Boolean.TRUE.equals(allProductsInStock)) {
//            orderRepository.save(order);
//        } else {
//            throw new IllegalArgumentException("Product is not in stock");
//        }

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDTOList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

            InventoryResponse[] inventoryResponsArray = webClientBuilder.build().get()
                    .uri("http://localhost:8082/api/inventory/",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            boolean allProductsInStock = Arrays.stream(inventoryResponsArray)
                    .allMatch(InventoryResponse::isInStock);

            if (allProductsInStock) {
                orderRepository.save(order);
                return "Order Placed Successfully";
            } else {
                throw new IllegalArgumentException("Product is not in stock, please try again later");
            }

    }

    private OrderLineItems mapToDto(OrderLineItemsDTO orderLineItemsDTO) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDTO.getPrice());
        orderLineItems.setQuantity(orderLineItemsDTO.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDTO.getSkuCode());
        return orderLineItems;
    }
}
