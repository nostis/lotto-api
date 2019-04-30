package com.nostis.lottoapi.task;

import com.nostis.lottoapi.bean.UrlBean;
import com.nostis.lottoapi.model.Lotto;
import com.nostis.lottoapi.service.LottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        if(!lastDraw.isPresent()){
            //System.out.println("Present");
            if(lottoService.getAllDraws().size() == 0){
                //populate all records
                BufferedReader bufReader = new BufferedReader(new StringReader(getPageSource()));
                Pattern pattern = Pattern.compile("([^ ]++(?= \\d++,\\d++,\\d++,\\d++,\\d++))");

                String line = "";

                while((line = bufReader.readLine()) != null) {
                    Matcher matcher = pattern.matcher(line);

                    if(matcher.find()){
                        //System.out.println(matcher.group());
                        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                        Date date = new Date();

                        try {
                             date = format.parse(matcher.group());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        //System.out.println(date);
                    }
                    else{
                        throw new IOException("Error during extracting date");
                    }
                }
            }
            else{
                //only add missing record/s
            }

        }

    }
}
