package edu.icet.mos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private Long id;
    private Date date;
    private Long customerId;
    private Double totalPrice;
    private List<OrderedProduct> products;
}
