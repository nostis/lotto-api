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

                String line = "";

                List<MiniLotto> draws = new ArrayList<>();

                Long drawNumber;
                Date drawDate;
                List<Byte> drawResult;

                while((line = bufReader.readLine()) != null) {
                    drawNumber = getDrawNumberFromLine(line);
                    drawDate = getDrawDateFromLine(line);
                    drawResult = getDrawResultFromLine(line);

                    //draws.add(new MiniLotto(getDrawNumberFromLine(line), getDrawDateFromLine(line), getDrawResultFromLine(line)));
                    draws.add(new MiniLotto(drawNumber, drawDate, drawResult));
                }

                miniLottoService.saveAllDraws(draws);
            }
            else{
                Long lastExisting;
                List<MiniLotto> existingDraws = miniLottoService.getAllDraws();
                List<MiniLotto> drawsToAdd = new ArrayList<>();
                for(int i = existingDraws.size() - 1; i > 0; i--){

                }
                //only add missing record/s
            }

        }

    }

    private Date getDrawDateFromLine(String line) throws IOException {
        Pattern datePattern = Pattern.compile("([^ ]++(?= \\d++,\\d++,\\d++,\\d++,\\d++))");
        Matcher dateMatcher = datePattern.matcher(line);

        Date drawDate;

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

        return drawDate;
    }

    private Long getDrawNumberFromLine(String line) throws IOException {
        Pattern drawNumberPattern = Pattern.compile("\\d++(?=\\. \\d++\\.\\d++\\.\\d++ \\d++,\\d++,\\d++,\\d++,\\d++)");
        Matcher drawNumberMatcher = drawNumberPattern.matcher(line);

        Long drawNumber;

        if(drawNumberMatcher.find()){
            drawNumber = Long.parseLong(drawNumberMatcher.group());
        }
        else{
            throw new IOException("Error during extracting draw number");
        }

        return drawNumber;
    }

    private List<Byte> getDrawResultFromLine(String line) throws IOException {
        Pattern drawResultPattern = Pattern.compile("\\d++[,\\d]++");
        Matcher drawResultMatcher = drawResultPattern.matcher(line);

        List<Byte> drawResult;

        if(drawResultMatcher.find()){
            drawResult = Stream.of(drawResultMatcher.group().split(","))
                    .map(String::trim)
                    .map(Byte::parseByte)
                    .collect(Collectors.toList());
        }
        else{
            throw new IOException("Error during extracting draw result");
        }

        return drawResult;
    }
}
