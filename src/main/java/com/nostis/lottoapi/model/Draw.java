package com.nostis.lottoapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type")
public class Draw implements Cloneable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "draw_number")
    protected Long drawNumber;
    @Column(name = "draw_date")
    @JsonFormat(timezone = "Europe/Warsaw", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    protected Date drawDate;
    @ElementCollection
    protected List<Byte> result;
    @Column(updatable = false, insertable = false)
    private String type;

    public Draw(Long drawNumber, Date drawDate, List<Byte> result) {
        this.drawNumber = drawNumber;;
        this.drawDate = drawDate;
        this.result = result;
    }

    public Draw(){}

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
