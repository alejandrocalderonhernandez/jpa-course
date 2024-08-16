package com.debuggeandoideas.gadgetplus.dtos;

import com.debuggeandoideas.gadgetplus.entities.CodeCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCatalogDTO implements Serializable {

    private UUID id;
    private String mane;
    private String description;
    private BigDecimal price;
    private List<CategoryDTO> categories;



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CategoryDTO {
        private CodeCategoryEnum code;
        private String description;
    }
}
