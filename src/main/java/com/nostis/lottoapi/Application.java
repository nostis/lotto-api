package com.nostis.lottoapi;

import com.nostis.lottoapi.bean.UrlBean;
import com.nostis.lottoapi.config.BeanConf;
import com.nostis.lottoapi.model.Lotto;
import com.nostis.lottoapi.service.LottoService;
import com.nostis.lottoapi.task.LottoSource;;
import com.nostis.lottoapi.task.MiniLottoSource;
import com.nostis.lottoapi.task.MultiMultiSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@EnableScheduling
@SpringBootApplication
public class Application {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);

        AnnotationConfigApplicationContext annotationContext = new AnnotationConfigApplicationContext(BeanConf.class);

        UrlBean lottoUrl = (UrlBean) annotationContext.getBean("lotto");
        UrlBean miniLottoUrl = (UrlBean) annotationContext.getBean("minilotto");
        UrlBean multiMultiUrl = (UrlBean) annotationContext.getBean("multimulti");

        LottoSource lottoSource = new LottoSource(lottoUrl);
        MiniLottoSource miniLottoSource = new MiniLottoSource(miniLottoUrl);
        MultiMultiSource multiMultiSource = new MultiMultiSource(multiMultiUrl);

        System.out.println("Lotto last draw number: " + lottoSource.getLastDrawNumber());
        System.out.println("Mini Lotto last draw number: " + miniLottoSource.getLastDrawNumber());
        System.out.println("Multi Multi last draw number: " + multiMultiSource.getLastDrawNumber());

        //lottoSource.updateDrawsInDatabase();
    }

    @Bean
    public CommandLineRunner demoData(LottoService lottoService){
        return args -> {
            List<Byte> list = new ArrayList<>();

            list.add((byte) 1);
            list.add((byte) 2);
            list.add((byte) 1);
            list.add((byte) 1);

            Lotto lotto1 = new Lotto( 1L, new GregorianCalendar(2019, Calendar.MAY, 1, 0,0).getTime(), list);
            //lottoService.saveDraw(lotto1);

            Lotto lotto2 = new Lotto( 6249L, new GregorianCalendar(2019, Calendar.APRIL, 1, 0, 0).getTime(), list);
            //lottoService.saveDraw(lotto2);
        };
    }
}