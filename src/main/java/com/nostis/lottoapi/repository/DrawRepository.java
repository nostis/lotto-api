package com.nostis.lottoapi.repository;

import com.nostis.lottoapi.model.Draw;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrawRepository extends CrudRepository<Draw, Long> {
    Optional<Draw> findByDrawNumber(Long drawNumber);
    List<Draw> findAllByType(String type);
}