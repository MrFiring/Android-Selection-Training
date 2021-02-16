package mrfiring.selectiontraining.tests;

import java.util.ArrayList;

/**
 * Created by mrfiring on 25.03.17.
 */

public class TestsHolder {
    private ArrayList<Test> tests;
    private int curTestNum;

    private boolean isFemale;
    private boolean isLoseWeight;

    public TestsHolder(){
        tests = new ArrayList<>();
        curTestNum = 0;
        isFemale = false;
        isLoseWeight = false;
    }


    public boolean isFemale() {
        return isFemale;
    }

    public void setFemale(boolean female) {
        isFemale = female;
    }

    public boolean isLoseWeight() {
        return isLoseWeight;
    }

    public void setLoseWeight(boolean loseWeight) {
        isLoseWeight = loseWeight;
    }

    public void addTest(Test test){
        tests.add(test);
    }

    public void addTest(String question, String[] answers){
        tests.add(new Test(question, answers));
    }

    public int getCurTestNum() {
        return curTestNum;
    }

    public Test getNextTest(){
        return tests.get(curTestNum++);
    }

    public boolean isFinish(){
        return curTestNum == tests.size() ? true : false;
    }

}
