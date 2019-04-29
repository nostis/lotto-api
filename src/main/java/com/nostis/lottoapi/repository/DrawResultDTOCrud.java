package com.nostis.lottoapi.repository;

import com.nostis.lottoapi.model.DrawResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrawResultDTOCrud extends JpaRepository<DrawResult, Long> {
}
