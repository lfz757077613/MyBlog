//import lombok.extern.slf4j.Slf4j;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.ConnectionPool;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//import org.apache.commons.lang3.ArrayUtils;
//import org.apache.shiro.crypto.hash.Md5Hash;
//import org.joda.time.DateTime;
//import org.joda.time.Seconds;
//import org.joda.time.format.DateTimeFormat;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.security.KeyStoreException;
//import java.security.cert.CertificateException;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * Created by fuzhi.lai on 2017/11/17.
// */
//@Slf4j
//public class MainTest {
//
//    @Test
//    public void testHttpClient() throws InterruptedException, CertificateException, KeyStoreException {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .connectTimeout(1, TimeUnit.SECONDS)
//                .connectionPool(new ConnectionPool(50, 5, TimeUnit.SECONDS))
//                .readTimeout(1, TimeUnit.SECONDS)
//                .writeTimeout(1, TimeUnit.SECONDS)
//                .followRedirects(false)
//                .followSslRedirects(false)
//                .build();
//
//        Request request = new Request.Builder()
//                .url("https://www.laifuzhi.cn")
//                .build();
//        AtomicInteger count = new AtomicInteger();
//        while (true) {
////            Thread.sleep(5);
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
////                    log.error("error", e);
//                    System.out.println("失败count  " + count.incrementAndGet());
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) {
//                    try (ResponseBody body = response.body()) {
//                        System.out.println(response.code());//string()会调用关闭
//                        System.out.println("成功count  " + count.incrementAndGet());
//                        if (body != null) {
//                            body.string();
//                        }
//                    } catch (Exception e) {
////                        log.error("close", e);
//                    }
//                }
//            });
//        }
//    }
//
//    public static int binarysearch(int[] arr, int target) {
//        if (ArrayUtils.isEmpty(arr)) {
//            return -1;
//        }
//        int start = 0, end = arr.length - 1;
//        while (start + 1 < end) {
//            int mid = start + (end - start) / 2;
//            if (arr[mid] > target)
//                end = mid;
//            else if (arr[mid] == target)
//                return mid;//找任意位置，start=mid找最后一个，end=mid找第一个；
//            else
//                start = mid;
//        }
//        if (arr[start] == target)
//            return start;
//        if (arr[end] == target)
//            return end;
//        return -1;
//    }
//
//
//    public static void main(String[] args) {
//        System.out.println(new Md5Hash("hehe", "hehe", 5));
//        Seconds seconds = Seconds.secondsBetween(DateTime.now(),
//                DateTime.parse("201804180900", DateTimeFormat.forPattern("yyyyMMddHHmm")));
//        System.out.println(seconds.getSeconds());
//        int[] arr = {0, 2, 3, 4};
//        System.out.println(binarysearch(arr, 2));
//    }
//
//    public int binarySearch(int[] arr, int target) {
//        if (ArrayUtils.isEmpty(arr)) {
//            return -1;
//        }
//        int start = 0, end = arr.length - 1;
//        while (start + 1 < end) {
//            int mid = start + (end - start) / 2;
//            if (arr[mid] > target) {
//                end = mid;
//            } else if (arr[mid] < target) {
//                start = mid;
//            } else {
//                return mid;
//            }
//        }
//        if (arr[start] == target) {
//            return start;
//        } else if (arr[end] == target) {
//            return end;
//        }
//        return -1;
//    }
//
//
//    public static void quickSort(int[] arr, int left, int right) {
//        if (left >= right)
//            return;
//        int i = left, j = right - 1;
//        int tmp = arr[right];
//        while (true) {
//            while (arr[i] <= tmp && i < right) i++;
//            while (arr[j] >= tmp && j > left) j--;
//            if (i < j) {
//                int t = arr[j];
//                arr[j] = arr[i];
//                arr[i] = t;
//            } else
//                break;
//        }
//        arr[right] = arr[i];
//        arr[i] = tmp;
//        quickSort(arr, left, i - 1);
//        quickSort(arr, i + 1, right);
//    }
//
//
//    public void heapSort(int[] arr) {
//        if (ArrayUtils.isEmpty(arr)) {
//            return;
//        }
//        buildHeap(arr);
//        int end = arr.length - 1;
//        for (int i = end; i > 0; i--) {
//            swap(arr, 0, i);
//            adjustHeap(arr, 0, i - 1);
//        }
//    }
//
//    public void swap(int[] arr, int a, int b) {
//        arr[a] = arr[a] ^ arr[b];
//        arr[b] = arr[a] ^ arr[b];
//        arr[a] = arr[a] ^ arr[b];
//    }
//
//    public void adjustHeap(int[] arr, int init, int end) {
//        int tmp = arr[init];
//        for (int i = init * 2 + 1; i <= end; i = 2 * i + 1) {
//            if (i != end && arr[i] < arr[i + 1])
//                i++;
//            if (tmp > arr[i])
//                break;
//            else {
//                arr[init] = arr[i];
//                init = i;
//            }
//        }
//        arr[init] = tmp;
//    }
//
//    public void buildHeap(int[] arr) {
//        int end = arr.length - 1;
//        for (int i = (end - 1) / 2; i >= 0; i--) {
//            adjustHeap(arr, i, end);
//        }
//    }
//
//}