import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class ObjectToJson {
    public String convertToJson(Object o){

        try {
            checkIfSerializable(o);
            initObject(o);
            return getJsonToString(o);
        } catch (Exception e){
            throw new RuntimeException("converting has been broken");
        }

    }

    private void checkIfSerializable(Object object) {
        if (Objects.isNull(object)) {
            throw new JsonObjectException("Das zu serialisierende Objekt ist null");
        }

        Class<?> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(JsonObject.class)) {
            throw new JsonObjectException("Die Klasse "
                    + clazz.getSimpleName()
                    + "ist nicht mit @JsonObject kommentiert");
        }
    }

    private String getJsonToString(Object o) throws IllegalAccessException {

        Class<?> c = o.getClass();

        Map<String,String> jsonElements = new HashMap<>();

        for (Field field : c.getDeclaredFields()){
            field.setAccessible(true); // for private field
            if(field.isAnnotationPresent(JsonElement.class)){
                jsonElements.put(getKey(field),(String) field.get(o));
            }
        }

        String jsonString = jsonElements.entrySet()
                .stream()
                .map(entry -> "\"" + entry.getKey() + "\":\""
                        + entry.getValue() + "\"")
                .collect(Collectors.joining(","));
        return "{" + jsonString + "}";

    }

    private String getKey(Field field){
        JsonElement jsonElement = field.getAnnotation(JsonElement.class);
        String key = jsonElement.key();

        if(key.equals("")){ // если пустой возвращать нэйм
            return field.getName();
        }else
            return key;
    }


    private void initObject(Object object) throws Exception {
        Class<?> clazz = object.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Init.class)) {
                method.setAccessible(true); // private Init()
                method.invoke(object);
            }
        }
    }

}
