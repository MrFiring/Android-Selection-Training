package mrfiring.selectiontraining;

import java.util.ArrayList;

/**
 * Created by mrfiring on 27.03.17.
 */

public class ProgramController {
    private ArrayList<TrainingProgram> programms;

    public ProgramController(){
        this.programms = new ArrayList<>();


    }

    public void addProgram(TrainingProgram program){
        programms.add(program);
    }

    @Deprecated
    public void addProgram(String target_groups, String exercises_html, String rest){
        programms.add(new TrainingProgram(target_groups, exercises_html, rest));
    }
    public void addProgram(String target_groups, String[] exercises, String rest){
        programms.add(new TrainingProgram(target_groups, exercises, rest));
    }



    public TrainingProgram getProgram(int index){

        return programms.get(index);
    }

}
