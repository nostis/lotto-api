package com.nostis.lottoapi.service;

import com.nostis.lottoapi.repository.LottoDTOCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LottoService {
    @Autowired
    LottoDTOCrud lottoDTOCrud;
}
