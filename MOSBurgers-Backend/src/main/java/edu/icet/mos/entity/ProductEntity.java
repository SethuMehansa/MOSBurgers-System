package edu.icet.mos.entity;

import edu.icet.mos.util.ProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private Double discount;


    private String type;
    private Integer quantity;
//    private String imageUrl;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;


    public void updateStock(Integer quantity) {
        if (this.quantity < quantity) {
            throw new RuntimeException("Not enough stock available!");
        }
        this.quantity -= quantity;
    }
}
