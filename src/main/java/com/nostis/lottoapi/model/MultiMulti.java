package com.nostis.lottoapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class MultiMulti {
    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "increment")
    private Long id;
    private Long drawNumber;
    @Column
    @JsonFormat(timezone = "Europe/Warsaw", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date drawDate;
    @Column
    @ElementCollection
    private List<Byte> numbers;

    public MultiMulti(Long drawNumber, Date drawDate, List<Byte> numbers) {
        this.drawNumber = drawNumber;
        this.drawDate = drawDate;
        this.numbers = numbers;
    }

    public MultiMulti(){}
}
