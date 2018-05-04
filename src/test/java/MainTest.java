import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Sets;
import com.qunar.lfz.model.MyResponse;
import com.qunar.lfz.model.ResponseEnum;
import com.qunar.lfz.model.userInfo.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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