package com.nostis.lottoapi.task;

import com.nostis.lottoapi.bean.UrlBean;
import com.nostis.lottoapi.model.MiniLotto;
import com.nostis.lottoapi.service.MiniLottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
public class MiniLottoSource extends MBNet {
    @Autowired
    private MiniLottoService miniLottoService;
    private URL pageUrl;

    public MiniLottoSource(@Qualifier("minilotto") UrlBean urlBean) throws IOException {
        pageUrl = urlBean.getUrl();
        updatePageSource(pageUrl);
    }

    @Transactional
    @Scheduled(fixedDelay = 5000, initialDelay = 6000)
    public void updateDrawsInDatabase() throws IOException {
        updatePageSource(pageUrl);

        Optional<MiniLotto> lastDraw = miniLottoService.findDrawByDrawNumber(getLastDrawNumber());

        if(!lastDraw.isPresent()){
            if(miniLottoService.getAllDraws().size() == 0){
                //populate all records
                BufferedReader bufReader = new BufferedReader(new StringReader(getPageSource()));

                Pattern datePattern = Pattern.compile("([^ ]++(?= \\d++,\\d++,\\d++,\\d++,\\d++))");
                Pattern drawNumberPattern = Pattern.compile("\\d++(?=\\. \\d++\\.\\d++\\.\\d++ \\d++,\\d++,\\d++,\\d++,\\d++)");
                Pattern drawResultPattern = Pattern.compile("\\d++[,\\d]++");

                String line = "";
                Date drawDate;
                Long drawNumber;
                List<Byte> drawResult;
                List<MiniLotto> draws = new ArrayList<>();

                while((line = bufReader.readLine()) != null) {
                    Matcher dateMatcher = datePattern.matcher(line);
                    Matcher drawNumberMatcher = drawNumberPattern.matcher(line);
                    Matcher drawResultMatcher = drawResultPattern.matcher(line);

                    if(dateMatcher.find()){
                        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                        drawDate = new Date();

                        try {
                            drawDate = format.parse(dateMatcher.group());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                    else{
                        throw new IOException("Error during extracting date");
                    }


                    if(drawNumberMatcher.find()){
                        drawNumber = Long.parseLong(drawNumberMatcher.group());
                    }
                    else{
                        throw new IOException("Error during extracting draw number");
                    }


                    if(drawResultMatcher.find()){
                        drawResult = Stream.of(drawResultMatcher.group().split(","))
                                .map(String::trim)
                                .map(Byte::parseByte)
                                .collect(Collectors.toList());
                    }
                    else{
                        throw new IOException("Error during extracting draw result");
                    }

                    draws.add(new MiniLotto(drawNumber, drawDate, drawResult));
                }

                miniLottoService.saveAllDraws(draws);
            }
            else{
                //only add missing record/s
            }

        }

    }
}
