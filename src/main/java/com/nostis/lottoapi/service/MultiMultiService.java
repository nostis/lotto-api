package com.nostis.lottoapi.service;

import com.nostis.lottoapi.repository.MultiMultiDTOCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MultiMultiService {
    @Autowired
    MultiMultiDTOCrud multiMultiDTOCrud;
}
