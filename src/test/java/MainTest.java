import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by fuzhi.lai on 2017/11/17.
 */
public class MainTest {
    @Test
    public void delay() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(new son()));
    }

    class dad {
        public int getGetSSS(int a) {
            return 22;
        }
    }

    class son extends dad{
        int aa;
    }
}