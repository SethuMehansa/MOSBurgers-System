package edu.icet.mos.entity;

import edu.icet.mos.dto.Order;
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
@Table(name = "ordered_products")
public class OrderedProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private ProductEntity product;

    private Integer quantity;
    private Double discount;
    private Double price;

    @Override
    public String toString() {
        return "OrderedProductEntity{" +
                "id=" + id +
                ", product=" + (product != null ? product.getId() : "null") + // Print only product ID
                ", quantity=" + quantity +
                ", discount=" + discount +
                ", price=" + price +
                '}';
    }
}
