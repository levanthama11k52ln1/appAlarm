package models;

public class ShakeDetail {
    private int idAlarm;
    private int difficulty;
    private int numberOfProblem;

    public static ShakeDetail obtain(int idAlarm){
        return new ShakeDetail(idAlarm, ShakeDifficulty.MODERATE, 50);
    }
    public static ShakeDetail obtainAlternative(int idAlarm){
        return new ShakeDetail(idAlarm, ShakeDifficulty.HARD, 200);
    }

    public ShakeDetail(int idAlarm, int difficulty, int numberOfProblem) {
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

    public static final class ShakeDifficulty{
        public static final int EASY = 1;
        public static final int MODERATE = 2;
        public static final int HARD = 3;
    }
}
