
@JsonObject
public class User {

    @JsonElement(key = "userName")
    private String name;

    @JsonElement
    private String lastname;

    private Integer age;

    @Init
    private void init() {
        this.name = this.name.substring(0, 1).toUpperCase()
                + this.name.substring(1);
        this.lastname = this.lastname.substring(0, 1).toUpperCase()
                + this.lastname.substring(1);
    }

    public User(String name, String lastname, Integer age) {
        this.name = name;
        this.lastname = lastname;
        this.age = age;
    }

    public User(){

    }
}
