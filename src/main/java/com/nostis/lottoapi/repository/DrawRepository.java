package com.nostis.lottoapi.repository;

import com.nostis.lottoapi.model.Draw;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrawRepository extends JpaRepository<Draw, Long> {
    Optional<Draw> findByDrawNumber(Long drawNumber);
    List<Draw> findAllByType(String type);
    List<Draw> findAllByType(String type, Pageable pageable);
}