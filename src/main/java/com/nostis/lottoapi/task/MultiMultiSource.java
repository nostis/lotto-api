package com.nostis.lottoapi.task;

import com.nostis.lottoapi.bean.UrlBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class MultiMultiSource extends MBNet {
    private URL pageUrl;

    public MultiMultiSource(@Qualifier("multimulti") UrlBean urlBean) throws IOException {
        pageUrl = urlBean.getUrl();
        updatePageSource(pageUrl);
    }
}
