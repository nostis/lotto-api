package com.nostis.lottoapi.repository;

import com.nostis.lottoapi.model.MultiMulti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "multi", path = "multi")
public interface MultiMultiDTOCrud extends JpaRepository<MultiMulti, Long>, CustomMultiMulti<MultiMulti, Long> {
}
