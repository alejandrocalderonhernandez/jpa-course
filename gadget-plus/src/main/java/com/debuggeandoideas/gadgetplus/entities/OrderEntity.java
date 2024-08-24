package com.debuggeandoideas.gadgetplus.entities;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = true)
    private LocalDateTime lastUpdated;

    @Column(length = 32, nullable = false)
    private String clientName;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_bill", nullable = false, unique = true)
    private BillEntity bill;


    @OneToMany(mappedBy = "order",
                             fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
                             orphanRemoval = true)
    private List<ProductEntity> products = new ArrayList<>();

    @Transient
    private Boolean isSaved = false;

    public void addProduct(ProductEntity product) {
        this.products.add(product);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @PrePersist
    private void prePersist() {
        this.setCreatedAt(LocalDateTime.now());
        log.info("Pre persist {}", this.getCreatedAt().toString());
    }

    @PostPersist
    private void postPersist() {
        log.info("Post persist {}", this.getIsSaved());
        this.setIsSaved(true);
        log.info("Post persist {}", this.getIsSaved());
    }


    @PreUpdate
    private void preUpdate() {
        this.setLastUpdated(LocalDateTime.now());
        log.info("Pre update {}", this.getLastUpdated().toString());
    }

    @PostUpdate
    private void postUpdate() {
        log.info("Post update {}", this.getLastUpdated().toString());
    }

    @PreRemove
    private void preRemove() {
        log.warn("Entity will be removed");
        this.products = new ArrayList<>();
    }

    @PostRemove
    private void postRemove() {
        log.warn("Entity was removed");
    }

}
