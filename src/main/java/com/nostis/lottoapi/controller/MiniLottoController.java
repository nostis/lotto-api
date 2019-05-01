package com.nostis.lottoapi.controller;

import com.nostis.lottoapi.model.MiniLotto;
import com.nostis.lottoapi.service.MiniLottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class MiniLottoController {
    @Autowired
    private MiniLottoService miniLottoService;

    @RequestMapping("/mini")
    @GetMapping({"page", "size"})
    public List<MiniLotto> getPaginatedDraws(@RequestParam(value = "page", defaultValue = "0", required = false) int page, @RequestParam(value = "size", defaultValue = "10", required = false) int size){
        Page<MiniLotto> resultPage = miniLottoService.findPaginatedDraw(page, size);

        if(page > resultPage.getTotalPages()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found");
        }

        return resultPage.getContent();
    }

    @GetMapping("/mini/all")
    public List<MiniLotto> getAllDraws(){
        return miniLottoService.getAllDraws();
    }
}
