package edu.icet.mos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderedProduct {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Double discount;
    private Double price;
}
