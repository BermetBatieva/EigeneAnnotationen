import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestConverter {


    @Test
    public void test(){
        User user = new User("bermet","batieva",21);
        ObjectToJson objectToJson = new ObjectToJson();
        System.out.println(objectToJson.convertToJson(user));
        assertEquals(
                "{\"userName\":\"Bermet\",\"lastname\":\"Batieva\"}",
                objectToJson.convertToJson(user));
    }
}
