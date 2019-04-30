package com.nostis.lottoapi;

import com.nostis.lottoapi.model.Lotto;
import com.nostis.lottoapi.service.LottoService;
import com.nostis.lottoapi.task.LottoSource;
import com.nostis.lottoapi.task.MiniLottoSource;
import com.nostis.lottoapi.task.MultiMultiSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.URL;
import java.util.*;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demoData(LottoService lottoService){
        return args -> {
            /*List<Byte> list = new ArrayList<>();

            list.add((byte) 1);
            list.add((byte) 2);
            list.add((byte) 1);
            list.add((byte) 1);

            Lotto lotto1 = new Lotto( 1L, new GregorianCalendar(2019, Calendar.MAY, 1, 0,0).getTime(), list);

            System.out.println(lotto1);

            lottoService.saveDraw(lotto1);

            Lotto lotto2 = new Lotto( 2L, new GregorianCalendar(2019, Calendar.APRIL, 1).getTime(), list);

            lottoService.saveDraw(lotto2);*/

            LottoSource lottoSource = new LottoSource(new URL("http://www.mbnet.com.pl/dl.txt"));
            MiniLottoSource miniLottoSource = new MiniLottoSource(new URL("http://www.mbnet.com.pl/el.txt"));
            MultiMultiSource multiMultiSource = new MultiMultiSource(new URL("http://www.mbnet.com.pl/ml.txt"));

            System.out.println("Lotto last draw number: " + lottoSource.getLastDrawNumber());
            System.out.println("Mini Lotto last draw number: " + miniLottoSource.getLastDrawNumber());
            System.out.println("Multi Multi last draw number: " + multiMultiSource.getLastDrawNumber());

        };
    }

}
