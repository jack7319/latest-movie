package com.bizideal.mn.crawler;

import com.aliyuncs.exceptions.ClientException;
import com.bizideal.mn.utils.SmsUtils;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: liulq
 * @Date: 2018/3/28 18:03
 * @Description:
 * @version: 1.0
 */
public class MovieCrawler {

    private static String username = "17621216043";
    private static String password = "921228";

    private String cookie;
    private Map<String, String> cookieMap = new HashMap<>();

    private static Logger logger = LoggerFactory.getLogger(MovieCrawler.class);

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void getMovies() throws IOException {
        JsonObject param = new JsonObject();
        String movies = "";
        String comingMovies = "";
        String url = "http://maoyan.com/cinema/17146";
        Document html = Jsoup.connect(url).method(Connection.Method.GET).get();
        Elements divs = html.select("div.show-list");
        for (Element div : divs) {
            String movieName = div.select("h3.movie-name").get(0).text();
            Elements spans = div.select("div.movie-desc").select("span.value");
            // 电影时长
            String time = spans.get(0).text();
            // 电影类型
            String type = spans.get(1).text();
            // 主演
            String actor = spans.size() > 2 ? spans.get(2).text() : null;
            // 观影时间
            String watchTime = div.select("span.date-item.active").get(0).text();
            logger.info("电影：{}，类型：{}，主演：{}，时长：{}，观影时间：{}", movieName, type, actor, time, watchTime);
            if (watchTime.contains("今天")) {
                movies += movieName + "（" + actor + "）" + "、";
            } else {
                comingMovies += movieName + "（" + actor + "）" + "、";
            }
        }
        if (movies.endsWith("、")) {
            movies = movies.substring(0, movies.length() - 1);
        }
        if (comingMovies.endsWith("、")) {
            comingMovies = comingMovies.substring(0, comingMovies.length() - 1);
        }
        param.addProperty("cinema", "明星时代影城");
        param.addProperty("movies", movies);
        param.addProperty("comingMovies", comingMovies);
        try {
            logger.debug(movies);
            logger.debug(comingMovies);
            logger.debug(param.toString().length() + "");
            SmsUtils.sendMsg("17621216043", "电影资讯", "SMS_129745677", param.toString());
            logger.info("推送完成，时间：{}", sdf.format(new Date()));
        } catch (ClientException e) {
            logger.error("短信发送失败，", e);
        }
    }

    public static void main(String[] args) throws Exception {
        getMovies();
    }
}
