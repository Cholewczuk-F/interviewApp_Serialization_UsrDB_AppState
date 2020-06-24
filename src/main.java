import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

//for serialize state: if(user!=null) -> questions, currentUser, questionsAnswered, score

public class main {

    public static void main(String[] args){
        setupState.deserializeState();
        ArrayList<User> users = (ArrayList<User>) setupUserDatabase.deserializeUsers();
//users collection setup
        if(users == null){
            users = new ArrayList<User>();
        }

//user authentication setup
        String username = "";
        if(setupState.currentUser != null) {
            System.out.println("Continued as user: " + setupState.currentUser.name);
        }else{
            JFrame frame = new JFrame("ID");
            do {
                try{
                    username = JOptionPane.showInputDialog(frame, "What's your identificator?");
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                if(username == null){
                    break;
                }
            }while(username.equals(""));
        }
        if(username != null && !username.equals("")){
            setupState.currentUser = new User(username);
            if(username.equals("admin")){
                setupState.questionsAnswered = setupState.questions.size();
            }
        }

//questions asked
        ArrayList<String[]> questions = setupState.questions;
        String title = "Answering user: " + setupState.currentUser.name;
        for(int i = setupState.questionsAnswered; i < questions.size(); ++i)
        {
            String question = questions.get(i)[1];
            question = "Question " + (i + 1) + ": " + question;


            String[] answers = Arrays.copyOfRange(questions.get(i), 2, 6);
            String correctAnswer = questions.get(i)[questions.get(i).length - 1];

            int answer = JOptionPane.showOptionDialog(null, question, title, JOptionPane.OK_CANCEL_OPTION, 0, null, answers, answers[3]);

            //check if answer is correct
            if(answer == -1) {
                break;
            }else{
                setupState.questionsAnswered++;
            }
            if(answers[answer].equals(correctAnswer)) {
                setupState.currentUser.points++;
            }
        }

        if(setupState.questionsAnswered == questions.size() && !setupState.currentUser.name.equals("admin")){
            String[] button = {"ok"};
            String message = "Your results " + setupState.currentUser.name + ": " + setupState.currentUser.points + " points.";
            JOptionPane.showOptionDialog(null, message, "Summary Listing.", JOptionPane.OK_CANCEL_OPTION, 0, null, button, 0);
        }

        if(setupState.questionsAnswered == questions.size() && !setupState.currentUser.name.equals("admin")){
            users.add(setupState.currentUser);
            setupState.currentUser = null;
        }else if(setupState.currentUser.name.equals("admin"))//if it is admin - show database
        {
            if(!users.isEmpty()){
                String usersListing = "";
                for (User userStored : users) {
                    usersListing += "User: " + userStored.name + " - Points: " + userStored.points + "\n";
                }

                String[] button = {"ok"};
                JOptionPane.showOptionDialog(null, usersListing, "Admin user listing.", JOptionPane.OK_CANCEL_OPTION, 0, null, button, 0);
            }
            setupState.currentUser = null;
        }

        setupUserDatabase.serializeUsers(users);
        setupState.serializeState();
    }
}
