package com.nostis.lottoapi.service;

import com.nostis.lottoapi.model.Lotto;
import com.nostis.lottoapi.repository.LottoDTOCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
