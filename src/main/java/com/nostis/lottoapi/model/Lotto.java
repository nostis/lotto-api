package com.nostis.lottoapi.model;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("lotto")
public class Lotto extends Draw {
}
