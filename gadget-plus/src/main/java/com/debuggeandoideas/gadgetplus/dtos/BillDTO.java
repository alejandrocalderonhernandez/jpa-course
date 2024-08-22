package com.debuggeandoideas.gadgetplus.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {

    private String idBill;
    private String clientRfc;
    private BigDecimal totalAmount;
}
