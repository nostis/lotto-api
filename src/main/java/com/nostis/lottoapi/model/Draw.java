package com.nostis.lottoapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="draw_type",
        discriminatorType = DiscriminatorType.STRING)
public class Draw {
    @Id
    private Long id;
    @Column(name = "draw_number")
    private Long drawNumber;
    @Column(name = "draw_date")
    @JsonFormat(timezone = "Europe/Warsaw", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date drawDate;
    @ElementCollection
    private List<Byte> result;

    public Draw(Long drawNumber, Date drawDate, List<Byte> result) {
        this.drawNumber = drawNumber;;
        this.drawDate = drawDate;
        this.result = result;
    }

    public Draw(){}
}
