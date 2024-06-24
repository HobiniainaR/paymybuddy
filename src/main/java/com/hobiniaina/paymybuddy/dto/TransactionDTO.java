package com.hobiniaina.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Integer id;
    private Integer receiver_id;
    private String description;
    private Double amount;

}
