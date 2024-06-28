package com.debuggeandoideas.gadgetplus.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reject_products")
@IdClass(RejectProductId.class)
public class RejectProductEntity {

    @Id
    private String productName;
    @Id
    private String brandName;
    private  Integer quantity;
}
