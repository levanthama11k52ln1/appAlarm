package models;

import com.peanut.androidlib.view.MeasurementPicker;

public class MovingDetail {
    private int idAlarm;
    private int distance;
    private MeasurementPicker.Measurement measurement;
    private ChallengeType alternativeChallenge;

    // Non-database properties
    private ShakeDetail shakeDetail;
    private MathDetail mathDetail;
    public static MovingDetail obtain(int idAlarm){
        MovingDetail movingDetail = new MovingDetail(idAlarm, 100, MeasurementPicker.Measurement.METER, ChallengeType.SHAKE);
        movingDetail.shakeDetail = ShakeDetail.obtainAlternative(idAlarm);
        return movingDetail;
    }

    public MovingDetail(int idAlarm, int distance, MeasurementPicker.Measurement measurement, ChallengeType alternativeChallenge) {
        this.idAlarm = idAlarm;
        this.distance = distance;
        this.measurement = measurement;
        this.alternativeChallenge = alternativeChallenge;
    }

    public int getIdAlarm() {
        return idAlarm;
    }

    public void setIdAlarm(int idAlarm) {
        this.idAlarm = idAlarm;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public MeasurementPicker.Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(MeasurementPicker.Measurement measurement) {
        this.measurement = measurement;
    }

    public ChallengeType getAlternativeChallenge() {
        return alternativeChallenge;
    }

    public void setAlternativeChallenge(ChallengeType alternativeChallenge) {
        this.alternativeChallenge = alternativeChallenge;
    }

    public ShakeDetail getShakeDetail() {
        return shakeDetail;
    }

    public void setShakeDetail(ShakeDetail shakeDetail) {
        this.shakeDetail = shakeDetail;
    }

    public MathDetail getMathDetail() {
        return mathDetail;
    }

    public void setMathDetail(MathDetail mathDetail) {
        this.mathDetail = mathDetail;
    }
}
