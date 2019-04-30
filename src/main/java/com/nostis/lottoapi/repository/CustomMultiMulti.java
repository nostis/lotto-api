package com.nostis.lottoapi.repository;

import com.nostis.lottoapi.model.Lotto;
import com.nostis.lottoapi.model.MultiMulti;

import java.util.Optional;

public interface CustomMultiMulti<T, S> {
    Optional<MultiMulti> findByDrawNumber(Long drawNumber);
}
