package com.nostis.lottoapi.task;

import com.nostis.lottoapi.model.Draw;
import com.nostis.lottoapi.model.Lotto;
import com.nostis.lottoapi.model.MiniLotto;
import com.nostis.lottoapi.model.MultiMulti;
import com.nostis.lottoapi.service.DrawService;
import com.nostis.lottoapi.util.DrawSource;
import com.nostis.lottoapi.util.ProcessSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import java.util.List;

@Component
public class DrawUpdater {
    @Autowired
    private DrawService drawService;
    @Autowired
    private ProcessSource processSource;
    @Autowired
    private DrawSource drawSource;

    @Transactional
    @Scheduled(fixedDelay = 10000, initialDelay = 5000)
    public void updateDraws() throws IOException, CloneNotSupportedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.updateAndSave("http://www.mbnet.com.pl/dl.txt", "lotto", Lotto.class);
        this.updateAndSave("http://www.mbnet.com.pl/el.txt", "mini_lotto", MiniLotto.class);
        this.updateAndSave("http://www.mbnet.com.pl/ml.txt", "multi_multi", MultiMulti.class);
    }

    private void updateAndSave(String url, String type, Class<? extends Draw> classType) throws CloneNotSupportedException, InvocationTargetException, IOException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        List<Draw> allDraws = this.drawService.findAllByDrawType(type);
        List<Draw> toSave;
        toSave = this.drawSource.getDrawsToSave(allDraws, url, classType);
        this.drawService.saveAllDraws(toSave);
    }
}
