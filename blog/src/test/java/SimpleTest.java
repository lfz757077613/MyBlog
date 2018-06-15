//import com.google.common.collect.Lists;
//import org.junit.Test;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.List;
//import java.util.ListIterator;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//import java.util.concurrent.ThreadPoolExecutor;
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
//    @Test
//    public void testLFZ() {
//        Face face = new FaceImpl();
//        FaceImpl faceImpl = (FaceImpl) Proxy.newProxyInstance(face.getClass().getClassLoader(),
//                face.getClass().getInterfaces(), new InvocationHandler() {
//                    @Override
//                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                        System.out.println("pre");
//                        Object result = method.invoke(face, args);
//                        System.out.println("suf");
//                        return result;
//                    }
//                });
//    }
//
//    @Test
//    public void testProject() {
//        Double d1 = null;
//        double d2 = d1;
//        System.out.println(d2);
//    }
//}
//
//interface Face {
//    void print(String str);
//}
//
//class FaceImpl implements Face {
//
//    @Override
//    public void print(String str) {
//        System.out.println(str);
//    }
//}
