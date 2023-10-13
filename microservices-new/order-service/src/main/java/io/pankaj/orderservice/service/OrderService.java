package io.pankaj.orderservice.service;

import io.pankaj.orderservice.dto.InventoryResponse;
import io.pankaj.orderservice.dto.OrderLineItemsDto;
import io.pankaj.orderservice.dto.OrderRequest;
import io.pankaj.orderservice.model.Order;
import io.pankaj.orderservice.model.OrderLineItems;
import io.pankaj.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems=  orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto).toList();

        order.setOrderLinItems(orderLineItems);

        List<String> skuCodes =order.getOrderLinItems().stream()
                .map(OrderLineItems::getSkuCode).toList();

        //call inventory service and place order if order is in stock
        InventoryResponse[] inventoryResponseArray = webClient.get()
                .uri("http://localhost:9003/api/inventory",uriBuilder -> uriBuilder.queryParam("sku-code",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

        if(allProductsInStock){
            orderRepository.save(order);
        }else{
            throw new IllegalArgumentException("Product is not in Stock please try later");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
