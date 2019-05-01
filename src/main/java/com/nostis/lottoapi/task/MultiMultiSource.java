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
    @Scheduled(fixedDelay = 60000, initialDelay = 7000)
    public void updateDrawsInDatabase() throws IOException {
        updatePageSource(pageUrl);

        Optional<MultiMulti> lastDraw = multiMultiService.findDrawByDrawNumber(getLastDrawNumber());

        if(!lastDraw.isPresent()){
            if(multiMultiService.getAllDraws().size() == 0){
                getLogger().info("Table MultiMulti is empty; populating MultiMulti with all records");

                BufferedReader bufReader = new BufferedReader(new StringReader(getPageSource()));
                String line;

                List<MultiMulti> draws = new ArrayList<>();

                while((line = bufReader.readLine()) != null) {
                    draws.add(new MultiMulti(processSource.getDrawNumberFromLine(line), processSource.getDrawDateFromLine(line), processSource.getDrawResultFromLine(line)));
                }

                getLogger().info("Saving all draws gathered from page source (MultiMulti)");
                multiMultiService.saveAllDraws(draws);
            }
            else{
                getLogger().info("Records are not up to date (MultiMulti)");

                List<MultiMulti> existingDraws = multiMultiService.getAllDraws();
                Long lastExistingInDb = existingDraws.get(existingDraws.size() - 1).getDrawNumber();

                List<MultiMulti> drawsToAdd = new ArrayList<>();

                BufferedReader bufReader = new BufferedReader(new StringReader(getPageSource()));
                String line;

                boolean canAdd = false;

                while((line = bufReader.readLine()) != null) {

                    if(processSource.getDrawNumberFromLine(line).equals(lastExistingInDb + 1)){
                        canAdd = true;
                    }

                    if(canAdd){
                        getLogger().info("Updating: " + processSource.getDrawNumberFromLine(line) + " " + processSource.getDrawDateFromLine(line) + " " + processSource.getDrawResultFromLine(line));

                        drawsToAdd.add(new MultiMulti(processSource.getDrawNumberFromLine(line), processSource.getDrawDateFromLine(line), processSource.getDrawResultFromLine(line)));
                    }
                }

                multiMultiService.saveAllDraws(drawsToAdd);
            }
        }
    }
}

