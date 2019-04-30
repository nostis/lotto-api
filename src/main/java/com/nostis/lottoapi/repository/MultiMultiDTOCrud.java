package com.nostis.lottoapi.repository;

import com.nostis.lottoapi.model.MultiMulti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultiMultiDTOCrud extends JpaRepository<MultiMulti, Long>, CustomMultiMulti<MultiMulti, Long> {
}
