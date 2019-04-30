package com.nostis.lottoapi.config;

import com.nostis.lottoapi.bean.UrlBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConf {
    @Bean(name = {"lotto"})
    public UrlBean lottoUrl(){
        return new UrlBean("http://www.mbnet.com.pl/dl.txt");
    }

    @Bean(name = {"minilotto"})
    public UrlBean miniLottoUrl(){
        return new UrlBean("http://www.mbnet.com.pl/el.txt");
    }

    @Bean(name = {"multimulti"})
    public UrlBean multiMultioUrl(){
        return new UrlBean("http://www.mbnet.com.pl/ml.txt");
    }

}
