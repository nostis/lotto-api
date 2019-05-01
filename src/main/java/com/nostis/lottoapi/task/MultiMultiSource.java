package com.nostis.lottoapi.task;

import com.nostis.lottoapi.bean.UrlBean;
import com.nostis.lottoapi.model.MultiMulti;
import com.nostis.lottoapi.service.MultiMultiService;
import com.nostis.lottoapi.util.ProcessSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MultiMultiSource extends MBNet {
    @Autowired
    private MultiMultiService multiMultiService;
    private URL pageUrl;
    private ProcessSource processSource;

    public MultiMultiSource(@Qualifier("multimulti") UrlBean urlBean) throws IOException {
        pageUrl = urlBean.getUrl();
        updatePageSource(pageUrl);
        processSource = new ProcessSource();
    }

    @Transactional
    @Scheduled(fixedDelay = 5000, initialDelay = 7000)
    public void updateDrawsInDatabase() throws IOException {
        updatePageSource(pageUrl);

        Optional<MultiMulti> lastDraw = multiMultiService.findDrawByDrawNumber(getLastDrawNumber());

        if(!lastDraw.isPresent()){
            if(multiMultiService.getAllDraws().size() == 0){
                //populate all records
                BufferedReader bufReader = new BufferedReader(new StringReader(getPageSource()));
                String line = "";

                List<MultiMulti> draws = new ArrayList<>();

                while((line = bufReader.readLine()) != null) {
                    draws.add(new MultiMulti(processSource.getDrawNumberFromLine(line), processSource.getDrawDateFromLine(line), processSource.getDrawResultFromLine(line)));
                }

                multiMultiService.saveAllDraws(draws);
            }
            else{
                /*Long lastExisting;
                List<MultiMulti> existingDraws = multiMultiService.getAllDraws();
                List<MultiMulti> drawsToAdd = new ArrayList<>();
                for(int i = existingDraws.size() - 1; i > 0; i--){

                }
                //only add missing record/s
                */

            }

        }

    }
}
