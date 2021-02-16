package mrfiring.selectiontraining;

/**
 * Created by mrfiring on 27.03.17.
 */


//Класс хранящий упражнения по дням тренировок
public class TrainingProgram {
    private String target_groups;
    private String rest;
    private String exercise_html;
    private String[] exercises;


    public TrainingProgram(){}

    public TrainingProgram(String target_groups, String exercise_html, String rest){
        this.target_groups = target_groups;
        this.exercise_html = exercise_html;
        this.rest = rest;
    }


    public TrainingProgram(String target_groups, String[] exercises, String rest){
        this.target_groups = target_groups;
        this.exercises = exercises;
        this.rest = rest;
    }

    public String getTarget_groups() {
        return target_groups;
    }

    public String getRest() {
        return rest;
    }

    public String getExercise_html() {
        return exercise_html;
    }

    public String[] getExercises(){

        return exercises;
    }

}
