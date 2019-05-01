package com.nostis.lottoapi.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class MiniLotto {
    @Id
    @GeneratedValue(generator = "gen")
    @GenericGenerator(name = "gen", strategy = "increment")
    private Long id;
    private Long drawNumber;
    @Column
    private Date drawDate;
    @Column
    @ElementCollection
    private List<Byte> numbers;

    public MiniLotto(Long drawNumber, Date drawDate, List<Byte> numbers) {
        this.drawNumber = drawNumber;
        this.drawDate = drawDate;
        this.numbers = numbers;
    }

    public MiniLotto(){}
}
