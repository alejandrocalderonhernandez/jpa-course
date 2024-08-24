package com.debuggeandoideas.gadgetplus.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private String clientName;
    private BillDTO bill;
    private List<ProductsDTO> products;
}
