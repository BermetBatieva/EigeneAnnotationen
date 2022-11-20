import java.io.Serial;

public class JsonObjectException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    public JsonObjectException(String message) {
        super(message);
    }
}