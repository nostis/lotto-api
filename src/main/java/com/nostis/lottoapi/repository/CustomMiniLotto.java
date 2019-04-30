package com.nostis.lottoapi.repository;

import com.nostis.lottoapi.model.Lotto;
import com.nostis.lottoapi.model.MiniLotto;

import java.util.Optional;

public interface CustomMiniLotto<T, S> {
    Optional<MiniLotto> findByDrawNumber(Long drawNumber);
}
