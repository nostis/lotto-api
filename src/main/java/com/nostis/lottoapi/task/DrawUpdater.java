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

    @Transactional
    @Scheduled(fixedDelay = 10000, initialDelay = 5000)
    public void updateDraws() throws IOException, CloneNotSupportedException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ProcessSource processSource = new ProcessSource();
        DrawSource drawSource = new DrawSource(processSource);

        List<Draw> allDrawsLotto = this.drawService.findAllByDrawType("lotto");
        List<Draw> toSave;
        toSave = drawSource.getDrawsToSave(allDrawsLotto, "http://www.mbnet.com.pl/dl.txt", Lotto.class);
        this.drawService.saveAllDraws(toSave);

        List<Draw> allDrawsMini = this.drawService.findAllByDrawType("mini_lotto");
        List<Draw> toSaveMini;
        toSaveMini = drawSource.getDrawsToSave(allDrawsMini, "http://www.mbnet.com.pl/el.txt", MiniLotto.class);
        this.drawService.saveAllDraws(toSaveMini);

        List<Draw> allDrawsMulti = this.drawService.findAllByDrawType("multi_multi");
        List<Draw> toSaveMulti;
        toSaveMulti = drawSource.getDrawsToSave(allDrawsMulti, "http://www.mbnet.com.pl/ml.txt", MultiMulti.class);
        this.drawService.saveAllDraws(toSaveMulti);
    }
}
