package com.nostis.lottoapi.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MBNet {
    private URL pageUrl;
    private String pageSource;
    private Long lines;

    public MBNet(URL pageUrl){
        this.pageUrl = pageUrl;

        pageSource = "";
        lines = 0L;

        try {
            updatePageSource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@Scheduled
    public void updatePageSource() throws IOException {
        lines = 0L;
        pageSource = "";

        URLConnection connection = pageUrl.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuilder stringBuilder = new StringBuilder();

        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            stringBuilder.append(inputLine);
            lines++;
            stringBuilder.append("\n");



        }

        pageSource = stringBuilder.toString();

        reader.close();
    }

    public String getPageSource(){
        return pageSource;
    }
    public Long getAmountOfLines(){
        return lines;
    }
}