package com.nostis.lottoapi.repository;

import com.nostis.lottoapi.model.Lotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "lotto", path = "lotto")
public interface LottoDTOCrud extends JpaRepository<Lotto, Long>, CustomLotto<Lotto, Long> {
}
