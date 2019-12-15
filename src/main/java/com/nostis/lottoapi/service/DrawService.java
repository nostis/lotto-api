package com.nostis.lottoapi.service;

import com.nostis.lottoapi.model.Draw;
import com.nostis.lottoapi.repository.DrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DrawService {
    @Autowired
    private DrawRepository drawRepository;

    public Optional<Draw> findDrawByDrawNumber(Long number){
        return drawRepository.findByDrawNumber(number);
    }

    public List<Draw> findAllByDrawType(String drawType, Pageable pageable) {
        return drawRepository.findAllByType(drawType, pageable);
    }

    public void saveAllDraws(Iterable<Draw> draws) {
        drawRepository.saveAll(draws);
    }

    public List<Draw> findAllByDrawType(String drawType) {
        return this.drawRepository.findAllByType(drawType);
    }
}
