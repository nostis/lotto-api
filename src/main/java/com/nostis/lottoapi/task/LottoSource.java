package com.nostis.lottoapi.task;

import com.nostis.lottoapi.bean.UrlBean;
import com.nostis.lottoapi.model.Lotto;
import com.nostis.lottoapi.service.LottoService;
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
public class LottoSource extends MBNet{
    private URL pageUrl;
    @Autowired
    private LottoService lottoService;
    private ProcessSource processSource;

    public LottoSource(@Qualifier("lotto") UrlBean urlBean) throws IOException {
        pageUrl = urlBean.getUrl();
        updatePageSource(pageUrl);
        processSource = new ProcessSource();
    }

    @Transactional
    @Scheduled(fixedDelay = 60000, initialDelay = 5000)
    public void updateDrawsInDatabase() throws IOException {
        updatePageSource(pageUrl);

        Optional<Lotto> lastDraw = lottoService.findDrawByDrawNumber(getLastDrawNumber());

        if(!lastDraw.isPresent()){
            if(lottoService.getAllDraws().size() == 0){
                getLogger().info("Table Lotto is empty; populating Lotto with all records");

                BufferedReader bufReader = new BufferedReader(new StringReader(getPageSource()));
                String line;

                List<Lotto> draws = new ArrayList<>();

                while((line = bufReader.readLine()) != null) {
                    draws.add(new Lotto(processSource.getDrawNumberFromLine(line), processSource.getDrawDateFromLine(line), processSource.getDrawResultFromLine(line)));
                }

                getLogger().info("Saving all draws gathered from page source (Lotto)");
                lottoService.saveAllDraws(draws);
                getLogger().info("Draws added (Lotto)");
            }
            else{
                getLogger().info("Records are not up to date (Lotto)");

                List<Lotto> existingDraws = lottoService.getAllDraws();
                Long lastExistingInDb = existingDraws.get(existingDraws.size() - 1).getDrawNumber();

                List<Lotto> drawsToAdd = new ArrayList<>();

                BufferedReader bufReader = new BufferedReader(new StringReader(getPageSource()));
                String line;

                boolean canAdd = false;

                while((line = bufReader.readLine()) != null) {

                    if(processSource.getDrawNumberFromLine(line).equals(lastExistingInDb + 1)){
                        canAdd = true;
                    }

                    if(canAdd){
                        getLogger().info("Updating: " + processSource.getDrawNumberFromLine(line) + " " + processSource.getDrawDateFromLine(line) + " " + processSource.getDrawResultFromLine(line));

                        drawsToAdd.add(new Lotto(processSource.getDrawNumberFromLine(line), processSource.getDrawDateFromLine(line), processSource.getDrawResultFromLine(line)));
                    }
                }

                lottoService.saveAllDraws(drawsToAdd);
                getLogger().info("Draws updated (Lotto)");
            }
        }
    }
}
