package com.nostis.lottoapi.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProcessSource {
    //all procesing things from *Source classes
    private final String PTRN_DRAW_DATE = "([^ ]++(?= \\d++,\\d++,\\d++,\\d++,\\d++))";
    private final String PTRN_DRAW_NUMBER = "\\d++(?=\\. \\d++\\.\\d++\\.\\d++ \\d++,\\d++,\\d++,\\d++,\\d++)";
    private final String PTRN_DRAW_RESULT = "\\d++[,\\d]++";
    private Pattern datePattern;
    private Pattern drawNumberPattern;
    private Pattern drawResultPattern;

    public ProcessSource(){
        datePattern = Pattern.compile(PTRN_DRAW_DATE);
        drawNumberPattern = Pattern.compile(PTRN_DRAW_NUMBER);
        drawResultPattern = Pattern.compile(PTRN_DRAW_RESULT);
    }

    public Date getDrawDateFromLine(String line) throws IOException {
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

    public Long getDrawNumberFromLine(String line) throws IOException {
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

    public List<Byte> getDrawResultFromLine(String line) throws IOException {
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
