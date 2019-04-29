package com.nostis.lottoapi.repository;

import com.nostis.lottoapi.model.Lotto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LottoDTOCrud extends JpaRepository<Lotto, Long> {
}
