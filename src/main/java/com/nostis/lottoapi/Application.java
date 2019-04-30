package com.nostis.lottoapi;

import com.nostis.lottoapi.model.Lotto;
import com.nostis.lottoapi.service.LottoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
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

            System.out.println(lotto1);

            lottoService.saveDraw(lotto1);

            Lotto lotto2 = new Lotto( 2L, new GregorianCalendar(2019, Calendar.APRIL, 1).getTime(), list);

            lottoService.saveDraw(lotto2);

        };
    }

}
