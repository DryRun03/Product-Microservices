package io.pankaj.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor @AllArgsConstructor
public class OrderLineItemsDto {
    private Long id;
    private String skuCode;
    private BigInteger price;
    private Integer quantity;
}
