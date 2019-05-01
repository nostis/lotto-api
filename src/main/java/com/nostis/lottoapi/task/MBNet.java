package com.nostis.lottoapi.task;


import com.nostis.lottoapi.util.ProcessSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

public class MBNet {
    private String pageSource;
    private Long lastDrawNumber;
    private ProcessSource processSource;
    private static Logger LOGGER = Logger.getLogger("InfoLogging");

    public MBNet() {
        pageSource = "";
        lastDrawNumber = 0L;
        processSource = new ProcessSource();
    }

    public void updatePageSource(URL pageUrl) throws IOException {
        lastDrawNumber = 0L;
        pageSource = "";

        URLConnection connection = pageUrl.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder stringBuilder = new StringBuilder();

        String inputLine;
        String previousLine = "";

        while ((inputLine = reader.readLine()) != null) {
            previousLine = inputLine;

            stringBuilder.append(inputLine);
            stringBuilder.append("\n");
        }

        lastDrawNumber = processSource.getDrawNumberFromLine(previousLine);

        pageSource = stringBuilder.toString();

        reader.close();
    }

    public String getPageSource(){
        return pageSource;
    }
    public Long getLastDrawNumber(){
        return lastDrawNumber;
    }
    public Logger getLogger(){
        return LOGGER;
    }
}