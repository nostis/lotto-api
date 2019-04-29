package com.nostis.lottoapi.repository;

import com.nostis.lottoapi.model.MiniLotto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MiniLottoDTOCrud extends JpaRepository<MiniLotto, Long> {
}
