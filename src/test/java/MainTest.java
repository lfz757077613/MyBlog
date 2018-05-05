import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.qunar.lfz.model.MyResponse;
import com.qunar.lfz.model.ResponseEnum;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

/**
 * Created by fuzhi.lai on 2017/11/17.
 */
public class MainTest {
    @Test
    public void delay() throws JsonProcessingException, InterruptedException {
        Md5Hash md5Hash = new Md5Hash("hehe", "hehe", 5);

        String s = JSON.toJSONString(MyResponse.createResponse(ResponseEnum.SUCC));
        System.out.println(s);
    }

}