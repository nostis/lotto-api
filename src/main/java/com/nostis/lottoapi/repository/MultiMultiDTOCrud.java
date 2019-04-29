package com.nostis.lottoapi.repository;

import com.nostis.lottoapi.model.MultiMulti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultiMultiDTOCrud extends JpaRepository<MultiMulti, Long> {
}
