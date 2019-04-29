package com.nostis.lottoapi.service;

import com.nostis.lottoapi.repository.MiniLottoDTOCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiniLottoService {
    @Autowired
    private MiniLottoDTOCrud miniLottoDTOCrud;
}
