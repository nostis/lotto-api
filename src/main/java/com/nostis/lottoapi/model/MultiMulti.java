package com.nostis.lottoapi.model;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("multi_multi")
public class MultiMulti extends Draw {
    @Override
    public boolean equals(Object other) {
        if(other instanceof Draw) {
            return this.getDrawNumber().equals(((Draw) other).getDrawNumber());
        }

        return true;
    }
}
