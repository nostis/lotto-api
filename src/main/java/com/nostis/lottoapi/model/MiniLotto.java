package com.nostis.lottoapi.model;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("mini_lotto")
public class MiniLotto extends Draw {
}
