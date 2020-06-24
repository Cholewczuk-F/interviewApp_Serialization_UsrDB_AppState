import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class setupState {
    static User currentUser = null;
    static ArrayList<String[]> questions;
    static int score;
    static int questionsAnswered;

    public static void deserializeState(){
        if(new File("state.ser").isFile()) {//there is a  file
            try {
                ObjectInputStream readService = new ObjectInputStream(new FileInputStream("state.ser"));
                currentUser = (User) readService.readObject();


                if(currentUser != null) {
                    questions = (ArrayList<String[]>) readService.readObject();
                    score = (int) readService.readObject();
                    questionsAnswered = (int) readService.readObject();
                }else{
                    questions = readQuestions();
                    score = 0;
                    questionsAnswered = 0;
                }
                readService.close();
                System.out.println("Log: state read.");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{//there's no file, setup new state
            questions = readQuestions();
            score = 0;
            questionsAnswered = 0;
        }
    }


    public static void serializeState() {
        try {
            ObjectOutputStream writeService = new ObjectOutputStream(new FileOutputStream("state.ser"));
            if(currentUser != null){
                writeService.writeObject(currentUser);
                writeService.writeObject(questions);
                writeService.writeObject(score);
                writeService.writeObject(questionsAnswered);
            }else{
                writeService.writeObject(currentUser);
            }
            writeService.close();
            System.out.println("Log: state written.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String[]> readQuestions() {
        ArrayList<String[]> questions = new ArrayList<String[]>();
        try {
            FileReader readService = new FileReader("questions");
            BufferedReader readBuffer = new BufferedReader(readService);

            String line = null;
            while((line = readBuffer.readLine()) != null) {
                questions.add(line.split(";"));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(questions, new Random());
        return questions;
    }
}
