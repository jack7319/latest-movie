package com.bizideal.mn.schedule;

import com.bizideal.mn.crawler.MovieCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author: liulq
 * @Date: 2018/3/28 22:57
 * @Description:
 * @version: 1.0
 */
@Component
public class MovieSchedule {

    private static Logger logger = LoggerFactory.getLogger(MovieSchedule.class);

    @Scheduled(cron = "0 30 8 * * ?")
    public void movie() {
        try {
            MovieCrawler.getMovies();
        } catch (IOException e) {
            logger.error("出现异常", e);
        }
    }
}
