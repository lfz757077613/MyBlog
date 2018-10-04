package jmh;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.openjdk.jmh.runner.options.WarmupMode;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: fuzhi.lai
 * Date: 2018/8/31 下午2:31
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
@State(Scope.Benchmark)
@Warmup(iterations = 1, time = 1, batchSize = 1)
@Measurement(iterations = 0, time = 1, batchSize = 1)
public class HttpClientJMH {


    private OkHttpClient okHttpClient;
    double x;

    @Setup
    public void init() {
        okHttpClient = new OkHttpClient.Builder().build();
    }

    @TearDown
    public void down() {
        assert x > Math.PI : "Nothing changed?";
    }

    @Benchmark
//    @BenchmarkMode({Mode.SingleShotTime}) // 测试方法平均执行时间
//    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public int testOKHttp() throws IOException {
        Request request = new Request.Builder().url("http://www.baidu.com").build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            return response.code();
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(HttpClientJMH.class.getSimpleName())
                .forks(1)
//                .warmupTime(TimeValue.seconds(1))
//                .warmupIterations(1) //预热次数
//                .measurementTime(TimeValue.seconds(1))
//                .measurementIterations(3) //真正执行次数
                .jvmArgs("-ea") //打开断言
                .shouldFailOnError(false)
                .build();

        new Runner(opt).run();
    }
}
