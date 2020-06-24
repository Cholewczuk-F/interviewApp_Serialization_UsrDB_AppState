import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class setupUserDatabase {
    public static List<User> deserializeUsers() {
        List<User> users = null;
        if (new File("users.ser").isFile()){
            try {
                ObjectInputStream strumienWejsciowy = new ObjectInputStream(new FileInputStream("users.ser"));
                users = (List<User>) strumienWejsciowy.readObject();
                strumienWejsciowy.close();
                System.out.println("Log: users read.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return users;
        }else{
            return null;
        }
    }

    public static void serializeUsers(ArrayList<User> _users) {
        if (!_users.isEmpty()) {
            try {
                ObjectOutputStream strumienWyjsciowy = new ObjectOutputStream(new FileOutputStream("users.ser"));
                strumienWyjsciowy.writeObject(_users);
                strumienWyjsciowy.close();
                System.out.println("Log: users written.");
            } catch (IOException exc) {
                exc.printStackTrace();
                System.exit(1);
            }
        }
    }
}
