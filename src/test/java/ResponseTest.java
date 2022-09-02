import org.example.model.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResponseTest {


    @Test
    void uniqueCharactersCheck(){

        String outputString = "00000011111";
        HashMap<Character, Integer> expected = new HashMap<>();

        Response response = new Response(outputString);

        Map<Character, Integer> map = new HashMap<>();
        map.put('0', 6);
        map.put('1', 5);

        assertEquals(map, response.getUniqueCharacters());
    }


}
