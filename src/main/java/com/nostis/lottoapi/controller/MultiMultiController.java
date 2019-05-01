package com.nostis.lottoapi.controller;

import com.nostis.lottoapi.model.MultiMulti;
import com.nostis.lottoapi.service.MultiMultiService;
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
public class MultiMultiController {
    @Autowired
    private MultiMultiService multiMultiService;

    @RequestMapping("/multi")
    @GetMapping({"page", "size"})
    public List<MultiMulti> getPaginatedDraws(@RequestParam(value = "page", defaultValue = "0", required = false) int page, @RequestParam(value = "size", defaultValue = "10", required = false) int size){
        Page<MultiMulti> resultPage = multiMultiService.findPaginatedDraw(page, size);

        if(page > resultPage.getTotalPages()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page not found");
        }

        return resultPage.getContent();
    }

    @GetMapping("/multi/all")
    public List<MultiMulti> getAllDraws(){
        return multiMultiService.getAllDraws();
    }
}
