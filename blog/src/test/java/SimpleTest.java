import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.qunar.lfz.assist.DateTimeUtil;
import com.qunar.lfz.crawler.HttpLoggingInterceptor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.Test;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Proxy;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Author: fuzhi.lai
 * Date: 2018/6/8 上午11:00
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
public class SimpleTest {
    static int num = 0;
    static volatile boolean flag = false;

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            for (; 100000> num; ) {
                if (!flag && (num++ % 2 == 0)) {
                    System.out.println(num);
                    flag = true;
                }
            }
        });

        Thread t2 = new Thread(() -> {
            for (; 100000 > num; ) {
                if (flag && (num++ % 2 != 0)) {
                    System.out.println(num);
                    flag = false;
                }
            }
        });
        t1.start();
        t2.start();
    }

    @Test
    public void testLFZ() {
        Face face = new FaceImpl();
        Face faceImpl = (Face) Proxy.newProxyInstance(face.getClass().getClassLoader(),
                face.getClass().getInterfaces(), (proxy, method, args) -> {
                    System.out.println("pre");
                    Object result = method.invoke(face, args);
                    System.out.println("suf");
                    return result;
                });

        System.out.println(DateTimeUtil.formatDate(new Date()));
    }

    @Test
    public void testProject() throws ParseException, IOException {
        Function<Integer, String> function = String::valueOf;
        System.out.println(function.apply(1));
        log.info("asdfasdfasdfasdfsd");
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(200, 30, TimeUnit.SECONDS))
                .followRedirects(false)
                .followSslRedirects(false)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        client.newCall(new Request.Builder().url("http://www.baidu.com").build()).execute().body().string();



    }


    @Test
    public void test() throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(getClass().getResource(File.separator).getPath() + "批量导入模版v1.xlsx", "rw");
             FileChannel channel = file.getChannel();) {
            MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size());
            Future<Object> future = Executors.newFixedThreadPool(10).submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    return null;
                }
            });

//            System.out.println(mappedByteBuffer == mappedByteBuffer.get(new byte[channel.c]));
//            System.out.println(mappedByteBuffer.get(new byte[10]));
        }
    }

    @Test
    public void testFuture() throws InterruptedException, IOException {
        ListenableFutureTask<String> task1 = ListenableFutureTask.create(new Callable<String>() {
            @Override
            public String call() throws Exception {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("task1 over" + new Date());
                return "";
            }
        });

        ListenableFuture<String> transform = Futures.transform(task1, new Function<String, String>() {
            @Override
            public String apply(String input) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("trans over" + new Date());
                //显示的是执行task1的线程
                System.out.println("trans over" + Thread.currentThread());
                return "";
            }
        });
        new Thread(task1).start();
        System.out.println("main  " + Thread.currentThread());
        System.in.read();
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}

interface Face {
    void print(String str);
}

@Data
class FaceImpl implements Face {
    private String str = "123";
    private int i;

    @Override
    public void print(String str) {
        System.out.println(str);
    }
}

class Son extends FaceImpl {

}