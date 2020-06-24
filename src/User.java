import java.io.Serializable;

public class User implements Serializable {
    String name;
    int points;

    public User(String namePassed){
        name = namePassed;
        points = 0;
    }
}

