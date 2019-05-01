package com.nostis.lottoapi.repository;

import com.nostis.lottoapi.model.MiniLotto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomMiniLotto<T, S> {
    Optional<MiniLotto> findByDrawNumber(Long drawNumber);
}
