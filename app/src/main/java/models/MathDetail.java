package models;

public class MathDetail {
    private int idAlarm;
    private int difficulty;
    private int numberOfProblem;

    public static MathDetail obtain(int idAlarm){
        return new MathDetail(idAlarm, MathDifficulty.MODERATE, 3);
    }
    public static MathDetail obtainAlternative(int idAlarm){
        return new MathDetail(idAlarm, MathDifficulty.INSANE, 10);
    }

    public MathDetail(int idAlarm, int difficulty, int numberOfProblem) {
        this.idAlarm = idAlarm;
        this.difficulty = difficulty;
        this.numberOfProblem = numberOfProblem;
    }

    public int getIdAlarm() {
        return idAlarm;
    }

    public void setIdAlarm(int idAlarm) {
        this.idAlarm = idAlarm;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getNumberOfProblem() {
        return numberOfProblem;
    }

    public void setNumberOfProblem(int numberOfProblem) {
        this.numberOfProblem = numberOfProblem;
    }


    public static final class MathDifficulty{
        public static final int EASY = 1;
        public static final int MODERATE = 2;
        public static final int HARD = 3;
        public static final int INSANE = 4;
        public static final int NIGHTMARE = 5;
        public static final int INFERNAL = 6;
    }
}
