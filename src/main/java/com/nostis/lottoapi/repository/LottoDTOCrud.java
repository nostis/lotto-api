package com.nostis.lottoapi.repository;

import com.nostis.lottoapi.model.Lotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LottoDTOCrud extends JpaRepository<Lotto, Long> {
}
