package com.debuggeandoideas.gadgetplus.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CodeCategoryEnum code;

    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    @ToString.Exclude
    private List<ProductCatalogEntity> productsCatalog;

}
