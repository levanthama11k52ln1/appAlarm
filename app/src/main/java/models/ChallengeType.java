package models;

public enum ChallengeType {
    DEFAULT(1), MATH(2), SHAKE(3), MOVING(4);
    private final int value;
    ChallengeType(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static ChallengeType newInstanceFromValue(int value){
        switch (value){
            case 1:
                return ChallengeType.DEFAULT;
            case 2:
                return ChallengeType.MATH;
            case 3:
                return ChallengeType.SHAKE;
            case 4:
                return ChallengeType.MOVING;
            default:
                return ChallengeType.DEFAULT;
        }
    }
}