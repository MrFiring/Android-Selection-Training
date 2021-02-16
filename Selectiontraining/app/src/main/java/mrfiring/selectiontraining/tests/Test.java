package mrfiring.selectiontraining.tests;

/**
 * Created by mrfiring on 25.03.17.
 */

public class Test {
    private String question;
    private String[] answers;

    public Test(){
        question = "";
        answers = null;

    }

    public Test(String question, String[] answers){
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }
}
