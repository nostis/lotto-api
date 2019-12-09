package com.nostis.lottoapi.model;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("mini_lotto")
public class MiniLotto extends Draw {
    @Override
    public boolean equals(Object other) {
        if(other instanceof Draw) {
            return this.getDrawNumber().equals(((Draw) other).getDrawNumber());
        }

        return true;
    }
}
