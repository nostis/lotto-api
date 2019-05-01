package com.nostis.lottoapi.repository;

import com.nostis.lottoapi.model.MultiMulti;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomMultiMulti<T, S> {
    Optional<MultiMulti> findByDrawNumber(Long drawNumber);
}
