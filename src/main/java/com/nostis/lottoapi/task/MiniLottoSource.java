package com.nostis.lottoapi.task;

import com.nostis.lottoapi.bean.UrlBean;
import com.nostis.lottoapi.model.MiniLotto;
import com.nostis.lottoapi.service.MiniLottoService;
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
public class MiniLottoSource extends MBNet {
    @Autowired
    private MiniLottoService miniLottoService;
    private URL pageUrl;
    private ProcessSource processSource;

    public MiniLottoSource(@Qualifier("minilotto") UrlBean urlBean) throws IOException {
        pageUrl = urlBean.getUrl();
        processSource = new ProcessSource();
        updatePageSource(pageUrl);
    }

    @Transactional
    @Scheduled(fixedDelay = 60000, initialDelay = 6000)
    public void updateDrawsInDatabase() throws IOException {
        updatePageSource(pageUrl);

        Optional<MiniLotto> lastDraw = miniLottoService.findDrawByDrawNumber(getLastDrawNumber());

        if(!lastDraw.isPresent()){
            if(miniLottoService.getAllDraws().size() == 0){
                getLogger().info("Table MiniLotto is empty; populating MiniLotto with all records");

                BufferedReader bufReader = new BufferedReader(new StringReader(getPageSource()));
                String line = "";

                List<MiniLotto> draws = new ArrayList<>();

                while((line = bufReader.readLine()) != null) {
                    draws.add(new MiniLotto(processSource.getDrawNumberFromLine(line), processSource.getDrawDateFromLine(line), processSource.getDrawResultFromLine(line)));
                }

                getLogger().info("Saving all draws gathered from page source (MiniLotto)");
                miniLottoService.saveAllDraws(draws);
            }
            else{
                getLogger().info("Records are not up to date (MiniLotto)");

                List<MiniLotto> existingDraws = miniLottoService.getAllDraws();
                Long lastExistingInDb = existingDraws.get(existingDraws.size() - 1).getDrawNumber();

                List<MiniLotto> drawsToAdd = new ArrayList<>();

                BufferedReader bufReader = new BufferedReader(new StringReader(getPageSource()));
                String line;

                boolean canAdd = false;

                while((line = bufReader.readLine()) != null) {

                    if(processSource.getDrawNumberFromLine(line).equals(lastExistingInDb + 1)){
                        canAdd = true;
                    }

                    if(canAdd){
                        getLogger().info("Updating: " + processSource.getDrawNumberFromLine(line) + " " + processSource.getDrawDateFromLine(line) + " " + processSource.getDrawResultFromLine(line));

                        drawsToAdd.add(new MiniLotto(processSource.getDrawNumberFromLine(line), processSource.getDrawDateFromLine(line), processSource.getDrawResultFromLine(line)));
                    }
                }

                miniLottoService.saveAllDraws(drawsToAdd);
                getLogger().info("Draws added (MiniLotto)");
            }
        }
    }
}
