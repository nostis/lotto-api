package com.nostis.lottoapi.service;

import com.nostis.lottoapi.model.Draw;
import com.nostis.lottoapi.repository.DrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrawService {
    @Autowired
    private DrawRepository drawRepository;

    public void saveDraw(Draw draw){
        drawRepository.save(draw);
    }

    public List<Draw> getAllDraws(String drawType){
        return drawRepository.findByDrawType(drawType);
    }

    public Draw findDrawByDrawNumber(Long number, String drawType){
        return drawRepository.findByDrawNumberAndDrawType(number, drawType);
    }

    public void saveAllDraws(Iterable<Draw> draws) {
        drawRepository.saveAll(draws);
    }
}
