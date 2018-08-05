//import com.alibaba.fastjson.JSON;
//import com.google.common.base.Function;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Sets;
//import com.google.common.util.concurrent.Futures;
//import com.google.common.util.concurrent.ListenableFuture;
//import com.google.common.util.concurrent.ListenableFutureTask;
//import com.qunar.lfz.assist.DateTimeUtil;
//import lombok.Data;
//import org.junit.Test;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.lang.reflect.Proxy;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.nio.MappedByteBuffer;
//import java.nio.channels.FileChannel;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Collections;
//import java.util.Date;
//import java.util.Set;
//import java.util.TimeZone;
//import java.util.concurrent.Callable;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//import java.util.concurrent.TimeUnit;
//import java.util.function.Consumer;
//import java.util.function.Predicate;
//
///**
// * Author: fuzhi.lai
// * Date: 2018/6/8 上午11:00
// * Create by Intellij idea
// */
///*
//                       _oo0oo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                      0\  =  /0
//                    ___/`---'\___
//                  .' \\|     |// '.
//                 / \\|||  :  |||// \
//                / _||||| -:- |||||- \
//               |   | \\\  -  /// |   |
//               | \_|  ''\---/''  |_/ |
//               \  .-\__  '-'  ___/-. /
//             ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
//     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//
//               佛祖保佑         永无BUG
//*/
//public class SimpleTest {
//    static int num = 0;
//    static volatile boolean flag = false;
//
//    public static void main(String[] args) throws InterruptedException {
//
//        Thread t1 = new Thread(() -> {
//            for (; 100000> num; ) {
//                if (!flag && (num++ % 2 == 0)) {
//                    System.out.println(num);
//                    flag = true;
//                }
//            }
//        });
//
//        Thread t2 = new Thread(() -> {
//            for (; 100000 > num; ) {
//                if (flag && (num++ % 2 != 0)) {
//                    System.out.println(num);
//                    flag = false;
//                }
//            }
//        });
//        t1.start();
//        t2.start();
//    }
//
//    @Test
//    public void testLFZ() {
//        Face face = new FaceImpl();
//        Face faceImpl = (Face) Proxy.newProxyInstance(face.getClass().getClassLoader(),
//                face.getClass().getInterfaces(), (proxy, method, args) -> {
//                    System.out.println("pre");
//                    Object result = method.invoke(face, args);
//                    System.out.println("suf");
//                    return result;
//                });
//        System.out.println(DateTimeUtil.formatDate(new Date()));
//    }
//
//    @Test
//    public void testProject() throws ParseException {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date date = format.parse("2018-06-27 11:25");
//        System.out.println(date.getTime());
//        //1529391600000
//        System.out.println(JSON.toJSONString(new Son()));
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
//        dateFormat.setLenient(false);
//        System.out.println(dateFormat.format(new Date()));
//        Set<String> strings = Collections.unmodifiableSet(Sets.newHashSet(Lists.newArrayList()));
//        System.out.println(strings.contains("23"));
//        System.out.println(new BigDecimal("0.356"));
//
//        BigDecimal succRate = new BigDecimal(5 + 3)
//                .divide(new BigDecimal(18), 2, RoundingMode.HALF_UP);
//        System.out.println(succRate.toString());
//
//
//        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
//        Date parse = dateFormater.parse("2018-08-03");
//        System.out.println(parse.getTime());
//
//        LocalDate dateTime = LocalDate.parse("2018-08-01", DateTimeFormatter.ISO_LOCAL_DATE);
//        DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//    }
//
//    @Test
//    public void test() throws IOException {
//        try (RandomAccessFile file = new RandomAccessFile(getClass().getResource(File.separator).getPath() + "批量导入模版v1.xlsx", "rw");
//             FileChannel channel = file.getChannel();) {
//            MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, channel.size());
//            Future<Object> future = Executors.newFixedThreadPool(10).submit(new Callable<Object>() {
//                @Override
//                public Object call() throws Exception {
//                    return null;
//                }
//            });
//
////            System.out.println(mappedByteBuffer == mappedByteBuffer.get(new byte[channel.c]));
////            System.out.println(mappedByteBuffer.get(new byte[10]));
//        }
//    }
//
//    @Test
//    public void testFuture() throws InterruptedException, IOException {
//        ListenableFutureTask<String> task1 = ListenableFutureTask.create(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                TimeUnit.SECONDS.sleep(5);
//                System.out.println("task1 over" + new Date());
//                return "";
//            }
//        });
//
//        ListenableFuture<String> transform = Futures.transform(task1, new Function<String, String>() {
//            @Override
//            public String apply(String input) {
//                try {
//                    TimeUnit.SECONDS.sleep(5);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("trans over" + new Date());
//                //显示的是执行task1的线程
//                System.out.println("trans over" + Thread.currentThread());
//                return "";
//            }
//        });
//        new Thread(task1).start();
//        System.out.println("main  " + Thread.currentThread());
//        System.in.read();
//    }
//
//}
//
//interface Face {
//    void print(String str);
//}
//
//@Data
//class FaceImpl implements Face {
//    private String str = "123";
//    private int i;
//
//    @Override
//    public void print(String str) {
//        System.out.println(str);
//    }
//}
//
//class Son extends FaceImpl {
//
//}
