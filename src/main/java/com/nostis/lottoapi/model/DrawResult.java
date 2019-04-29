package com.nostis.lottoapi.model;

import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class DrawResult {
    private Long id;
    private Date drawDate;
    private Set<Byte> numbers;
}
