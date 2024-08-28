package models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.appbaothuc.DatabaseHandler;
import com.example.appbaothuc.Music;
import com.example.appbaothuc.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Alarm implements Comparable<Alarm>, Parcelable {
    private int idAlarm;
    private boolean enable;
    private int hour;
    private int minute;
    private List<Boolean> listRepeatDay;
    private Music ringtone;
    private String label;
    private int snoozeTime;
    private boolean vibrate;
    private int volume;
    private ChallengeType challengeType;

    // Non-database property
    private int dayOfWeek;
    private String describeRepeatDay;

    public static Alarm obtain(){
        List<Boolean> listRepeatDay = Arrays.asList(true, true, true, true, true, true, true);
        return new Alarm(true, R.integer.default_hour, R.integer.default_minute, listRepeatDay, new Music(Music.defaultRingtoneUrl, Music.defaultRingtoneName), null, 60, true, 750, ChallengeType.SHAKE);
    }

    public Alarm(int idAlarm, boolean enable, int hour, int minute, List<Boolean> listRepeatDay, Music ringtone, String label, int snoozeTime, boolean vibrate, int volume, ChallengeType challengeType) {
        this.idAlarm = idAlarm;
        this.enable = enable;
        this.hour = hour;
        this.minute = minute;
        this.listRepeatDay = listRepeatDay;
        this.ringtone = ringtone;
        this.label = label;
        this.snoozeTime = snoozeTime;
        this.vibrate = vibrate;
        this.volume = volume;
        this.challengeType = challengeType;

        generateDescribeRepeatDay();
    }

    public Alarm(boolean enable, int hour, int minute, List<Boolean> listRepeatDay, Music ringtone, String label, int snoozeTime, boolean vibrate, int volume, ChallengeType challengeType) {
        this.enable = enable;
        this.hour = hour;
        this.minute = minute;
        this.listRepeatDay = listRepeatDay;
        this.ringtone = ringtone;
        this.label = label;
        this.snoozeTime = snoozeTime;
        this.vibrate = vibrate;
        this.volume = volume;
        this.challengeType = challengeType;

        generateDescribeRepeatDay();
    }

    public Alarm(int idAlarm, boolean enable, int hour, int minute, List<Boolean> listRepeatDay) {
        this.idAlarm = idAlarm;
        this.enable = enable;
        this.hour = hour;
        this.minute = minute;
        this.listRepeatDay = listRepeatDay;

        generateDescribeRepeatDay();
    }

    public Alarm(boolean enable, int hour, int minute, List<Boolean> listRepeatDay) {
        this.enable = enable;
        this.hour = hour;
        this.minute = minute;
        this.listRepeatDay = listRepeatDay;

        generateDescribeRepeatDay();
    }

    public int getIdAlarm() {
        return idAlarm;
    }

    public void setIdAlarm(int idAlarm) {
        this.idAlarm = idAlarm;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public List<Boolean> getListRepeatDay() {
        return listRepeatDay;
    }

    public void setListRepeatDay(List<Boolean> listRepeatDay) {
        this.listRepeatDay = listRepeatDay;
        generateDescribeRepeatDay();
    }

    public Music getRingtone() {
        return ringtone;
    }

    public void setRingtone(Music ringtone) {
        this.ringtone = ringtone;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getSnoozeTime() {
        return snoozeTime;
    }

    public void setSnoozeTime(int snoozeTime) {
        this.snoozeTime = snoozeTime;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public ChallengeType getChallengeType() {
        return challengeType;
    }

    public void setChallengeType(ChallengeType challengeType) {
        this.challengeType = challengeType;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDescribeRepeatDay() {
        return describeRepeatDay;
    }

    public void setDescribeRepeatDay(String describeRepeatDay) {
        this.describeRepeatDay = describeRepeatDay;
    }

    private void generateDescribeRepeatDay(){
        int amount = 0;
        for(int i = 0; i < 7; i++){
            if(listRepeatDay.get(i)){
                amount++;
            }
        }
        if(amount == 7){
            this.describeRepeatDay = "Everyday";
        }
        else if(amount == 5 && !listRepeatDay.get(5) && !listRepeatDay.get(6)){
            this.describeRepeatDay = "Weekdays";
        }
        else if(amount == 2 && listRepeatDay.get(5) && listRepeatDay.get(6)){
            this.describeRepeatDay = "Weekends";
        }
        else if(amount == 0){
            this.describeRepeatDay = "Never";
        }
        else{
            this.describeRepeatDay = "";
            for(int i = 0; i < 7; i++){
                if(listRepeatDay.get(i)){
                    switch (i){
                        case 0:
                            this.describeRepeatDay = this.describeRepeatDay.concat("Mon, ");
                            break;
                        case 1:
                            this.describeRepeatDay = this.describeRepeatDay.concat("Tue, ");
                            break;
                        case 2:
                            this.describeRepeatDay = this.describeRepeatDay.concat("Wed, ");
                            break;
                        case 3:
                            this.describeRepeatDay = this.describeRepeatDay.concat("Thu, ");
                            break;
                        case 4:
                            this.describeRepeatDay = this.describeRepeatDay.concat("Fri, ");
                            break;
                        case 5:
                            this.describeRepeatDay = this.describeRepeatDay.concat("Sat, ");
                            break;
                        case 6:
                            this.describeRepeatDay = this.describeRepeatDay.concat("Sun, ");
                            break;
                    }
                }
            }
            this.describeRepeatDay = this.describeRepeatDay.substring(0, this.describeRepeatDay.lastIndexOf(','));
        }
    }
    public boolean validateRingtoneUrl(Context context){
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        File file = new File(this.getRingtone().getUrl());
        if (!file.exists()){
            this.setRingtone(new Music(Music.defaultRingtoneUrl, Music.defaultRingtoneName));
            databaseHandler.updateAlarmSetDefaultRingtone(this.getIdAlarm());
            return false;
        }
        databaseHandler.close();
        return true;
    }
    // Comparable
    @Override
    public int compareTo(Alarm o) {
        if(this.isEnable()){
            if(!o.isEnable()){
                return -1;
            }
            else if(this.getHour() < o.getHour()){
                return -1;
            }
            else if(this.getHour() == o.getHour()){
                if(this.getMinute() < o.getMinute()){
                    return -1;
                }
                else if(this.getMinute() == o.getMinute()){
                    return 0;
                }
                else{
                    return 1;
                }
            }
            else{
                return 1;
            }
        }
        else{
            if(o.isEnable()){
                return 1;
            }
            else if(this.getHour() < o.getHour()){
                return -1;
            }
            else if(this.getHour() == o.getHour()){
                if(this.getMinute() < o.getMinute()){
                    return -1;
                }
                else if(this.getMinute() == o.getMinute()){
                    return 0;
                }
                else{
                    return 1;
                }
            }
            else{
                return 1;
            }
        }
    }



    // Parcelable
    protected Alarm(Parcel in) {
        idAlarm = in.readInt();
        enable = in.readByte() != 0;
        hour = in.readInt();
        minute = in.readInt();
        listRepeatDay = new ArrayList<>();
        in.readList(listRepeatDay, Alarm.class.getClassLoader());
        ringtone = in.readParcelable(getClass().getClassLoader());
        label = in.readString();
        snoozeTime = in.readInt();
        vibrate = in.readByte() != 0;
        volume = in.readInt();
        challengeType = (ChallengeType)in.readSerializable();
        dayOfWeek = in.readInt();
        describeRepeatDay = in.readString();
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idAlarm);
        dest.writeByte((byte) (enable ? 1 : 0));
        dest.writeInt(hour);
        dest.writeInt(minute);
        dest.writeList(listRepeatDay);
        dest.writeParcelable(ringtone, 0);
        dest.writeString(label);
        dest.writeInt(snoozeTime);
        dest.writeByte((byte) (vibrate ? 1 : 0));
        dest.writeInt(volume);
        dest.writeSerializable(challengeType);
        dest.writeInt(dayOfWeek);
        dest.writeString(describeRepeatDay);
    }

    public static byte[] toByteArray(Parcelable parcelable){
        Parcel parcel = Parcel.obtain();
        parcelable.writeToParcel(parcel, 0);
        byte[] result = parcel.marshall();
        parcel.recycle();
        return result;
    }

    public static <T> T toParcelable(byte[] byteArray, Creator<T> creator){
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(byteArray, 0, byteArray.length);
        parcel.setDataPosition(0);
        T result = creator.createFromParcel(parcel);
        parcel.recycle();
        return result;
    }
}