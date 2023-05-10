package com.javaboots.javaboots.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document
@Data
@Builder
@AllArgsConstructor(staticName = "builder")
@NoArgsConstructor
public class Income {
    //title amount type amount date cateogry decription timestamp
    @Id
    private String id;
    @Field
    private String type = "income";
    private BigInteger amount;

    private String date;
    private String category;
    private String description;

}
