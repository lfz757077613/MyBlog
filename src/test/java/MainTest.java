import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.junit.Test;

/**
 * Created by fuzhi.lai on 2017/11/17.
 */
public class MainTest {
    @Test
    public void delay() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        System.out.println(mapper.writeValueAsString(new son()));
    }

    class dad {
        public int getGetSSS(int a) {
            return 22;
        }
    }
    @Getter
    class son extends dad{
        int aa=1;
    }
}