import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * Created by fuzhi.lai on 2017/11/17.
 */
public class MainTest {
    public static void main(String[] args) {
        System.out.println(new Md5Hash("hehe", "hehe", 5));
    }

}