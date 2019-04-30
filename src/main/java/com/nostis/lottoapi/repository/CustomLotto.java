package com.nostis.lottoapi.repository;

import com.nostis.lottoapi.model.Lotto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomLotto<T, S> {
    Optional<Lotto> findByDrawNumber(Long drawNumber);
}
