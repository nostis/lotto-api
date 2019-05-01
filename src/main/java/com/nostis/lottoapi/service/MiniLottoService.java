package com.nostis.lottoapi.service;

import com.nostis.lottoapi.model.MiniLotto;
import com.nostis.lottoapi.repository.MiniLottoDTOCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MiniLottoService {
    @Autowired
    private MiniLottoDTOCrud miniLottoDTOCrud;

    public void saveDraw(MiniLotto miniLotto){
        miniLottoDTOCrud.save(miniLotto);
    }

    public List<MiniLotto> getAllDraws(){
        return miniLottoDTOCrud.findAll();
    }

    public Optional<MiniLotto> findDrawByDrawNumber(Long number){
        return miniLottoDTOCrud.findByDrawNumber(number);
    }

    public void saveAllDraws(Iterable<MiniLotto> draws){
        miniLottoDTOCrud.saveAll(draws);
    }

    public Page<MiniLotto> findPaginatedDraw(int page, int size){
        return miniLottoDTOCrud.findAll(PageRequest.of(page, size));
    }
}
