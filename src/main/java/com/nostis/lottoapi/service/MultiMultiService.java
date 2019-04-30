package com.nostis.lottoapi.service;

import com.nostis.lottoapi.model.MultiMulti;
import com.nostis.lottoapi.repository.MultiMultiDTOCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MultiMultiService {
    @Autowired
    private MultiMultiDTOCrud multiMultiDTOCrud;

    public void saveDraw(MultiMulti multiMulti){
        multiMultiDTOCrud.save(multiMulti);
    }

    public List<MultiMulti> getAllDraws(){
        return multiMultiDTOCrud.findAll();
    }

    public Optional<MultiMulti> findDrawByDrawNumber(Long number){
        return multiMultiDTOCrud.findByDrawNumber(number);
    }

    public void saveAllDraws(Iterable<MultiMulti> draws){
        multiMultiDTOCrud.saveAll(draws);
    }
}
