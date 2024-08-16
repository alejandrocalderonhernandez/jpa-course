package com.debuggeandoideas.gadgetplus.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsDTO {

    private BigInteger quantity;
    private String name;
}
