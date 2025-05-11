package ro.interview.store_management_tool.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ro.interview.store_management_tool.dto.ProductType;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 10)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private BigDecimal price;

    @Column
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Column(unique = true, nullable = false)
    private String sku;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Stock stock;
}
