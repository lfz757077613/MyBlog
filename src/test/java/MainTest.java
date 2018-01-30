import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

/**
 * Created by fuzhi.lai on 2017/11/17.
 */
public class MainTest {
    @Test
    public void delay() throws JsonProcessingException, InterruptedException {
        Thread.sleep(5000);
        System.out.println("sssssssssss");
    }

}