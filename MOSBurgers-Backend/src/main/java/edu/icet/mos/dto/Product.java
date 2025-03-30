package edu.icet.mos.dto;

import edu.icet.mos.util.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private Long id;
    private String name;
    private Double price;
    private Double discount;
    private String type;
    private Integer quantity;
   private String image;
//    private byte[] image;

}
