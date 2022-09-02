import org.example.RequestSender;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

import java.net.URL;

public class RequestSenderTest {

    @Test
    void sendRequest(){

        String result = null;
        try{
            result = RequestSender.sendGET(new URL("http://numbersapi.com/10/trivia"));
        }catch (Exception ignored){

        }
        assertNotEquals(null, result);

    }

}
