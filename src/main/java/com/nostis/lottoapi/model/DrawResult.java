package com.nostis.lottoapi.model;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class DrawResult {
    private Long id;
    private Date drawDate;
    private Set<Byte> numbers;
}
