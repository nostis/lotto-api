package com.nostis.lottoapi.service;

import com.nostis.lottoapi.model.DrawResult;
import com.nostis.lottoapi.repository.DrawResultDTOCrud;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class DrawResultService {
    @Autowired
    private DrawResultDTOCrud drawResultDTOCrud;

    public void saveDraw(DrawResult drawResult){
        drawResultDTOCrud.save(drawResult);
    }

    public Optional<DrawResult> getDrawById(Long id){
        return drawResultDTOCrud.findById(id);
    }
}
