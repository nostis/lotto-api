package com.nostis.lottoapi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class MiniLotto {
    @Id
    private Long id;
    @Column
    private Date drawDate;
    @Column
    @ElementCollection
    private List<Byte> numbers;

    public MiniLotto(Long id, Date drawDate, List<Byte> numbers) {
        this.id = id;
        this.drawDate = drawDate;
        this.numbers = numbers;
    }

    public MiniLotto(){}
}