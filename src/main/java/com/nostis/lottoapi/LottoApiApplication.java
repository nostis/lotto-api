package com.nostis.lottoapi;

import com.nostis.lottoapi.model.Lotto;
import com.nostis.lottoapi.service.LottoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class LottoApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(LottoApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(LottoService lottoService){
		return args -> {
			List<Byte> list = new ArrayList<>();

			list.add((byte) 1);
			list.add((byte) 2);
			list.add((byte) 1);
			list.add((byte) 1);

			Lotto lotto1 = new Lotto( 1L, new GregorianCalendar(2019, Calendar.MAY, 1).getTime(), list);
		};
	}
}
