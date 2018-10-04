package com.qunar.lfz.crawler;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.http.HttpHeaders;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Author: fuzhi.lai
 * Date: 2018/8/13 下午5:46
 * Create by Intellij idea
 */
/*
                       _oo0oo_
                      o8888888o
                      88" . "88
                      (| -_- |)
                      0\  =  /0
                    ___/`---'\___
                  .' \\|     |// '.
                 / \\|||  :  |||// \
                / _||||| -:- |||||- \
               |   | \\\  -  /// |   |
               | \_|  ''\---/''  |_/ |
               \  .-\__  '-'  ___/-. /
             ___'. .'  /--.--\  `. .'___
          ."" '<  `.___\_<|>_/___.' >' "".
         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
         \  \ `_.   \_ __\ /__ _/   .-` /  /
     =====`-.____`.___ \_____/___.-`___.-'=====
                       `=---='
     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

               佛祖保佑         永无BUG
*/
@Slf4j
@Component
public class PcGetQueueCountCrawler extends AbstractCrawler {

    private static final String URL = "https://kyfw.12306.cn/otn/confirmPassenger/getQueueCount";

    public String crawler(String username, String token, String trainDate, String trainNo,
                          String stationTrainCode, String seatType, String fromStationTelecode,
                          String toStationTelecode, String leftTicket, String train_location) {
        FormBody formBody = new FormBody.Builder(Util.UTF_8)
                .add("train_date", trainDate)
                .add("train_no", trainNo)
                .add("stationTrainCode", stationTrainCode)
                .add("seatType", seatType)
                .add("fromStationTelecode", fromStationTelecode)
                .add("toStationTelecode", toStationTelecode)
                .addEncoded("leftTicket", leftTicket)
                .add("purpose_codes", "00")
                .add("train_location", train_location)
                .add("json_att", StringUtils.EMPTY)
                .add("REPEAT_SUBMIT_TOKEN", token)
                .build();

        Request request = new Request.Builder()
                .url(URL)
                .header("Origin", "https://kyfw.12306.cn")
                .header("Accept", "application/json, text/javascript, */*; q=0.01")
                .header("X-Requested-With", "XMLHttpRequest")
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
                .header("Accept-Language", "zh-CN,zh;q=0.9")
                .header("Referer", "https://kyfw.12306.cn/otn/confirmPassenger/initDc")
                // 加入Accept-Encoding，okhttp不会自动解gzip。不加的话请求头有Accept-Encoding: gzip
                // .header("Accept-Encoding", "gzip, deflate, br")
                // FormBody会设置请求头Content-Type: application/x-www-form-urlencoded
                // .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .post(formBody)
                .build();

        try (Response resp = getClient(username).newCall(request).execute()) {
            if (!resp.isSuccessful() || !HttpHeaders.hasBody(resp)) {
                return StringUtils.EMPTY;
            }
            return resp.body().string();
        } catch (IOException e) {
            log.error("error", e);
        }
        return StringUtils.EMPTY;
    }
}
