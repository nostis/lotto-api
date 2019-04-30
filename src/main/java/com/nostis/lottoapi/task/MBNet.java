package com.nostis.lottoapi.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MBNet {
    private URL pageUrl;
    private String pageSource;
    private Long lastDrawNumber;

    public MBNet(URL pageUrl){
        this.pageUrl = pageUrl;

        pageSource = "";
        lastDrawNumber = 0L;

        try {
            updatePageSource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@Scheduled
    public void updatePageSource() throws IOException {
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


        Pattern pattern = Pattern.compile("\\w++(?=\\. [^ ]++ \\d++,\\w++,\\d\\d,\\d++,\\d\\d)");
        Matcher matcher = pattern.matcher(previousLine);

        if(matcher.find()){
            lastDrawNumber = Long.parseLong(matcher.group());
        }
        else{
            throw new IOException("Can't find draw number");
        }

        pageSource = stringBuilder.toString();

        reader.close();
    }

    public String getPageSource(){
        return pageSource;
    }
    public Long getLastDrawNumber(){
        return lastDrawNumber;
    }
}