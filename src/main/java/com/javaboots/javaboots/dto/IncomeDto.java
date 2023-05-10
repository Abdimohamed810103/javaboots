package com.javaboots.javaboots.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigInteger;

@Document
@Data
@Builder
public class IncomeDto {
    @NotNull(message = "You need to enter a valid amount")
    private BigInteger amount;
    @NotNull(message = "Date is required")
    private String date;
    @NotBlank
    private String category;
    @NotBlank
    private String description;

}
