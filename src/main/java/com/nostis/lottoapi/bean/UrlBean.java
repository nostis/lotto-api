package com.nostis.lottoapi.bean;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlBean {
    private URL url;

    public UrlBean(String url){
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public URL getUrl(){
        return url;
    }
}
