package com.nostis.lottoapi.repository;

import com.nostis.lottoapi.model.MiniLotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "mini", path = "mini")
public interface MiniLottoDTOCrud extends JpaRepository<MiniLotto, Long>, CustomMiniLotto<MiniLotto, Long> {
}
