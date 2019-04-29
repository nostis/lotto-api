package com.nostis.lottoapi.repository;

import com.nostis.lottoapi.model.MiniLotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiniLottoDTOCrud extends JpaRepository<MiniLotto, Long> {
}
