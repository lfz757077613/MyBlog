//import com.alibaba.fastjson.JSON;
//import com.google.common.base.Splitter;
//import com.google.common.base.Stopwatch;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.google.common.io.Files;
//import com.qunar.lfz.assist.DateTimeUtil;
//import lombok.Data;
//import org.I0Itec.zkclient.ZkClient;
//import org.apache.commons.io.FileUtils;
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.ZooKeeper;
//import org.joda.time.DateTime;
//import org.joda.time.DateTimeUtils;
//import org.joda.time.format.DateTimeFormat;
//import org.joda.time.format.DateTimeFormatter;
//import org.junit.Test;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//import java.nio.ByteBuffer;
//import java.nio.MappedByteBuffer;
//import java.nio.channels.FileChannel;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//import java.util.ListIterator;
//import java.util.Map;
//import java.util.StringTokenizer;
//import java.util.TimeZone;
//import java.util.concurrent.Callable;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//import java.util.concurrent.Semaphore;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.AbstractQueuedSynchronizer;
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
