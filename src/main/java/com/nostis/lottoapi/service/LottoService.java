package com.nostis.lottoapi.service;

import com.nostis.lottoapi.model.Lotto;
import com.nostis.lottoapi.repository.LottoDTOCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LottoService {
    @Autowired
    private LottoDTOCrud lottoDTOCrud;

    public void saveDraw(Lotto lotto){
        lottoDTOCrud.save(lotto);
    }

    public List<Lotto> getAllDraws(){
        return lottoDTOCrud.findAll();
    }

    public Optional<Lotto> findDrawByDrawNumber(Long number){
        return lottoDTOCrud.findByDrawNumber(number);
    }

    public void saveAllDraws(Iterable<Lotto> draws){
        lottoDTOCrud.saveAll(draws);
    }

    public Page<Lotto> findPaginatedDraw(int page, int size){
        return lottoDTOCrud.findAll(PageRequest.of(page, size));
    }

}
