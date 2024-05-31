package com.debuggeandoideas.gadgetplus.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "bill")
@Data
public class BillEntity {

    @Id
    @Column(length = 64)
    private String id;
    @Column
    private BigDecimal totalAmount;
    @Column(name = "client_rfc", length = 14, nullable = false)
    private String rfc;
    @ToString.Exclude
    @OneToOne(mappedBy = "bill", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private OrderEntity order;

}
