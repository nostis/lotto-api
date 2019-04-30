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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            if(lottoService.getAllDraws().size() == 0){
                //populate all records
                BufferedReader bufReader = new BufferedReader(new StringReader(getPageSource()));

                Pattern datePattern = Pattern.compile("([^ ]++(?= \\d++,\\d++,\\d++,\\d++,\\d++))");
                Pattern drawNumberPattern = Pattern.compile("\\d++(?=\\. \\d++\\.\\d++\\.\\d++ \\d++,\\d++,\\d++,\\d++,\\d++)");
                Pattern drawResultPattern = Pattern.compile("\\d++[,\\d]++");

                String line = "";
                Date date;
                Long drawNumber;
                List<Byte> drawResult;

                while((line = bufReader.readLine()) != null) {
                    Matcher dateMatcher = datePattern.matcher(line);
                    Matcher drawNumberMatcher = drawNumberPattern.matcher(line);
                    Matcher drawResultMatcher = drawResultPattern.matcher(line);

                    if(dateMatcher.find()){
                        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                        date = new Date();

                        try {
                             date = format.parse(dateMatcher.group());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                    else{
                        throw new IOException("Error during extracting date");
                    }


                    if(drawNumberMatcher.find()){
                        drawNumber = Long.parseLong(drawNumberMatcher.group());
                        //System.out.println(drawNumber);
                    }
                    else{
                        System.out.println(line);
                        throw new IOException("Error during extracting draw number");
                    }


                    if(drawResultMatcher.find()){
                        //System.out.println(drawResultMatcher.group());
                        drawResult = Stream.of(drawResultMatcher.group().split(","))
                                .map(String::trim)
                                .map(Byte::parseByte)
                                .collect(Collectors.toList());
                        //System.out.println(drawResult);
                    }
                    else{
                        throw new IOException("Error during extracting draw result");
                    }



                }
            }
            else{
                //only add missing record/s
            }

        }

    }
}
