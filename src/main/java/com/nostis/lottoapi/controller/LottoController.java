package com.nostis.lottoapi.controller;

import com.nostis.lottoapi.model.Draw;
import com.nostis.lottoapi.service.DrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LottoController {
    @Autowired
    private DrawService drawService;

    @GetMapping("/lotto")
    public List<Draw> getAll(@RequestParam(value = "page", defaultValue = "0") int page) {
        Pageable pagination = PageRequest.of(page, 50);

        return this.drawService.findAllByDrawType("lotto", pagination);
    }
}
