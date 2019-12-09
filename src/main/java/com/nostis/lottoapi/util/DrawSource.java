package com.nostis.lottoapi.util;

import com.nostis.lottoapi.model.Draw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class DrawSource extends MBNet {
    private final ProcessSource processSource;

    public DrawSource(ProcessSource processSource) {
        this.processSource = processSource;
    }

    public List<Draw> getDrawsToSave(List<? extends Draw> allDraws, String url, Class<? extends Draw> drawType) throws IOException, CloneNotSupportedException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        this.updatePageSource(new URL(url));

        if(!this.areDrawsUpToDate(allDraws, this.getLastDrawNumber())) {
            if(allDraws.isEmpty()) {
                return this.getDrawsFromSource(drawType);
            }
            else {

            }
        }

        return Collections.emptyList();
    }

    private boolean areDrawsUpToDate(List<? extends Draw> draws, Long lastDrawNumber) {
        return draws.stream().anyMatch(draw -> draw.getDrawNumber().equals(lastDrawNumber));
    }

    private Draw getEntityWithLastDrawNumber(List<Draw> draws) {
        Map<Draw, Long> drawLongMap = draws.stream().collect(Collectors.toMap(draw -> draw, Draw::getDrawNumber));

        Long highestNumber = 0L;
        Draw drawWithHighestNumber = new Draw();

        for (Map.Entry<Draw, Long> entry : drawLongMap.entrySet()) {
            if (entry.getValue() > highestNumber) {
                drawWithHighestNumber = entry.getKey();
            }
        }

        return drawWithHighestNumber;
    }

    private Draw getDrawFromLine(String line, Class<? extends Draw> drawType) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Draw draw = drawType.getDeclaredConstructor().newInstance();

        draw.setDrawNumber(this.processSource.getDrawNumberFromLine(line));
        draw.setDrawDate(this.processSource.getDrawDateFromLine(line));
        draw.setResult(this.processSource.getDrawResultFromLine(line));

        return draw;
    }

    private List<Draw> getDrawsFromSource(Class<? extends Draw> drawType) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BufferedReader bufReader = new BufferedReader(new StringReader(getPageSource()));
        String line = "";

        List<Draw> drawsToSave = new ArrayList<>();

        while((line = bufReader.readLine()) != null) {
            Draw draw = this.getDrawFromLine(line, drawType);

            drawsToSave.add(draw);
        }

        return drawsToSave;
    }
}
