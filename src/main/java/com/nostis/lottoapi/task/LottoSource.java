package com.nostis.lottoapi.task;

import com.nostis.lottoapi.bean.UrlBean;
import com.nostis.lottoapi.model.Lotto;
import com.nostis.lottoapi.service.LottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Component
public class LottoSource extends MBNet{
    private URL pageUrl;
    @Autowired
    private LottoService lottoService;

    public LottoSource(@Qualifier("lotto") UrlBean urlBean) throws IOException {
        pageUrl = urlBean.getUrl();
        updatePageSource(pageUrl);
    }


    @Scheduled(fixedRate = 5000)
    public void updateDrawsInDatabase() throws IOException {
        updatePageSource(pageUrl);

        Optional<Lotto> lastDraw = lottoService.findDrawByDrawNumber(getLastDrawNumber());

        if(lastDraw.isPresent()){
            System.out.println("Present");
        }
        else{
            System.out.println("Not present");
        }
    }
}
