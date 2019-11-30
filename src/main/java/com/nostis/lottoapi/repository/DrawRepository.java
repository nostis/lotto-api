package com.nostis.lottoapi.repository;

import com.nostis.lottoapi.model.Draw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrawRepository extends JpaRepository<Draw, Long> {
    List<Draw> findByDrawType(String drawType);
    Draw findByDrawNumberAndDrawType(Long drawNumber, String drawType);
}