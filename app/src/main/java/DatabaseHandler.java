import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appbaothuc.models.Alarm;
import com.example.appbaothuc.models.ChallengeType;
import com.example.appbaothuc.models.MathDetail;
import com.example.appbaothuc.models.MovingDetail;
import com.example.appbaothuc.models.ShakeDetail;
import com.peanut.androidlib.view.MeasurementPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "APPBAOTHUC.db";
    private static final CursorFactory CURSOR_FACTORY = null;
    private static final int DATABASE_VERSION = 1;
    private Context context;
    private String databaseName;
    private CursorFactory cursorFactory;
    private int databaseVersion;
    private SQLiteDatabase db;
    private String sql;
    private String sqlFormat;
    private List<String> listAlarmColumn;
    private HashMap<String, Class> hashMapAlarmColumn;

    public DatabaseHandler(Context context, String databaseName, CursorFactory cursorFactory, int databaseVersion) {
        super(context, databaseName, cursorFactory, databaseVersion);
        this.context = context;
        this.databaseName = databaseName;
        this.cursorFactory = cursorFactory;
        this.databaseVersion = databaseVersion;
        db = getWritableDatabase();
        initializeTables();
    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, CURSOR_FACTORY, DATABASE_VERSION);
        db = getWritableDatabase();
        initializeTables();
    }

    private void initializeTables() {
        hashMapAlarmColumn = new HashMap<>();
        Integer.class.getTypeParameters();
        hashMapAlarmColumn.put("IdAlarm", Integer.class);
        hashMapAlarmColumn.put("Enable", Boolean.class);
        hashMapAlarmColumn.put("Hour", Integer.class);
        hashMapAlarmColumn.put("Minute", Integer.class);
        hashMapAlarmColumn.put("Monday", Boolean.class);
        hashMapAlarmColumn.put("Tuesday", Boolean.class);
        hashMapAlarmColumn.put("Wednesday", Boolean.class);
        hashMapAlarmColumn.put("Thursday", Boolean.class);
        hashMapAlarmColumn.put("Friday", Boolean.class);
        hashMapAlarmColumn.put("Saturday", Boolean.class);
        hashMapAlarmColumn.put("Sunday", Boolean.class);
        hashMapAlarmColumn.put("RingtoneUrl", String.class);
        hashMapAlarmColumn.put("RingtoneName", String.class);
        hashMapAlarmColumn.put("Label", String.class);
        hashMapAlarmColumn.put("Vibrate", Boolean.class);
        hashMapAlarmColumn.put("SnoozeTime", Integer.class);
        hashMapAlarmColumn.put("Volume", Integer.class);
        hashMapAlarmColumn.put("ChallengeType", Integer.class);
//        listAlarmColumn = new ArrayList<>();
//        listAlarmColumn.add("IdAlarm");
//        listAlarmColumn.add("Enable");
//        listAlarmColumn.add("Hour");
//        listAlarmColumn.add("Minute");
//        listAlarmColumn.add("Monday");
//        listAlarmColumn.add("Tuesday");
//        listAlarmColumn.add("Wednesday");
//        listAlarmColumn.add("Thursday");
//        listAlarmColumn.add("Friday");
//        listAlarmColumn.add("Saturday");
//        listAlarmColumn.add("Sunday");
//        listAlarmColumn.add("RingtoneUrl");
//        listAlarmColumn.add("RingtoneName");
//        listAlarmColumn.add("Label");
//        listAlarmColumn.add("Vibrate");
//        listAlarmColumn.add("SnoozeTime");
//        listAlarmColumn.add("Volume");
//        listAlarmColumn.add("ChallengeType");

        sql = "create table if not exists Alarm(" +
                "IdAlarm integer primary key autoincrement," +
                "Enable bit not null," +
                "Hour integer not null," +
                "Minute integer not null," +
                "Monday bit not null," +
                "Tuesday bit not null," +
                "Wednesday bit not null," +
                "Thursday bit not null," +
                "Friday bit not null," +
                "Saturday bit not null," +
                "Sunday bit not null," +
                "RingtoneUrl nvarchar(256)," +
                "RingtoneName nvarchar(256)," +
                "Label nvarchar(256)," +
                "Vibrate bit," +
                "SnoozeTime integer," +
                "Volume integer," +
                "ChallengeType integer)";
        db.execSQL(sql);

        sql = "create table if not exists MathDetail(" +
                "IdAlarm integer primary key," +
                "Difficulty integer," +
                "NumberOfProblem integer," +
                "constraint FK_MathDetail foreign key (IdAlarm) references Alarm(IdAlarm))";
        db.execSQL(sql);

        sql = "create table if not exists ShakeDetail(" +
                "IdAlarm integer primary key," +
                "Difficulty integer," +
                "NumberOfProblem integer," +
                "constraint FK_MathDetail foreign key (IdAlarm) references Alarm(IdAlarm))";
        db.execSQL(sql);

        sql = "create table if not exists MovingDetail(" +
                "IdAlarm integer primary key," +
                "Distance real," +
                "Measurement integer," +
                "AlternativeChallenge integer," +
                "constraint FK_MovingDetail foreign key (IdAlarm) references Alarm(IdAlarm))";
        db.execSQL(sql);
    }

    public void insertAlarm(boolean enable, int hour, int minute, List<Boolean> listRepeatDay, String ringtoneUrl, String ringtoneName,
                            boolean vibrate, String label, int snoozeTime, int volume, int challengeType) {
        sqlFormat = "insert into Alarm (" +
                " Enable, Hour, Minute," +
                " Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday," +
                " RingtoneUrl, RingtoneName, Label, Vibrate, SnoozeTime, Volume, ChallengeType)" +
                " values (" +
                " '%b', %d, %d," +
                " '%b', '%b', '%b', '%b', '%b', '%b', '%b'," +
                " '%s', '%s', '%s', '%b', %d, %d, %d)";
        sql = String.format(sqlFormat,
                enable, hour, minute,
                listRepeatDay.get(0), listRepeatDay.get(1), listRepeatDay.get(2), listRepeatDay.get(3), listRepeatDay.get(4), listRepeatDay.get(5), listRepeatDay.get(6),
                ringtoneUrl, ringtoneName, label, vibrate, snoozeTime, volume, challengeType);
        db.execSQL(sql);
    }

    public void insertAlarm(boolean enable, int hour, int minute, List<Boolean> listRepeatDay) {
        sqlFormat = "insert into Alarm (" +
                " Enable, Hour, Minute," +
                " Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday)" +
                " values (" +
                " '%b', %d, %d," +
                " '%b', '%b', '%b', '%b', '%b', '%b', '%b')";
        sql = String.format(sqlFormat,
                enable, hour, minute,
                listRepeatDay.get(0), listRepeatDay.get(1), listRepeatDay.get(2), listRepeatDay.get(3), listRepeatDay.get(4), listRepeatDay.get(5), listRepeatDay.get(6));
        db.execSQL(sql);
    }

    public void insertAlarm(Alarm alarm) {
        boolean enable = alarm.isEnable();
        int hour = alarm.getHour();
        int minute = alarm.getMinute();
        List<Boolean> listRepeatDay = alarm.getListRepeatDay();
        String ringtoneUrl = alarm.getRingtone().getUrl();
        String ringtoneName = alarm.getRingtone().getName();
        String label = alarm.getLabel();
        boolean vibrate = alarm.isVibrate();
        int snoozeTime = alarm.getSnoozeTime();
        int volume = alarm.getVolume();
        ChallengeType challengeType = alarm.getChallengeType();

        sqlFormat = "insert into Alarm (" +
                " Enable, Hour, Minute," +
                " Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday," +
                " RingtoneUrl, RingtoneName, Label, Vibrate, SnoozeTime, Volume, ChallengeType)" +
                " values (" +
                " '%b', %d, %d," +
                " '%b', '%b', '%b', '%b', '%b', '%b', '%b'," +
                " '%s', '%s', '%s', '%b', %d, %d, %d)";
        sql = String.format(sqlFormat,
                enable, hour, minute,
                listRepeatDay.get(0), listRepeatDay.get(1), listRepeatDay.get(2), listRepeatDay.get(3), listRepeatDay.get(4), listRepeatDay.get(5), listRepeatDay.get(6),
                ringtoneUrl, ringtoneName, label, vibrate, snoozeTime, volume, challengeType.getValue());
        db.execSQL(sql);
    }
    public void updateAlarm(Alarm updatedAlarm){
        int idAlarm = updatedAlarm.getIdAlarm();
        boolean enable = updatedAlarm.isEnable();
        int hour = updatedAlarm.getHour();
        int minute = updatedAlarm.getMinute();
        List<Boolean> listRepeatDay = updatedAlarm.getListRepeatDay();
        String ringtoneUrl = updatedAlarm.getRingtone().getUrl();
        String ringtoneName = updatedAlarm.getRingtone().getName();
        String label = updatedAlarm.getLabel();
        boolean vibrate = updatedAlarm.isVibrate();
        int snoozeTime = updatedAlarm.getSnoozeTime();
        int volume = updatedAlarm.getVolume();
        ChallengeType challengeType = updatedAlarm.getChallengeType();
        sqlFormat = "update Alarm set" +
                " Enable = '%b', Hour = %d, Minute = %d," +
                " Monday = '%b', Tuesday = '%b', Wednesday = '%b', Thursday = '%b', Friday = '%b', Saturday = '%b', Sunday = '%b'," +
                " RingtoneUrl = '%s', RingtoneName = '%s', Label = '%s', Vibrate = '%b', SnoozeTime = %d, Volume = %d, ChallengeType = %d" +
                " where IdAlarm = %d";
        sql = String.format(sqlFormat,
                enable, hour, minute,
                listRepeatDay.get(0), listRepeatDay.get(1), listRepeatDay.get(2), listRepeatDay.get(3), listRepeatDay.get(4), listRepeatDay.get(5), listRepeatDay.get(6),
                ringtoneUrl, ringtoneName, label, vibrate, snoozeTime, volume, challengeType.getValue(), idAlarm);
        db.execSQL(sql);
    }
    public void updateAlarmEnable(Alarm updatedAlarm){
        int idAlarm = updatedAlarm.getIdAlarm();
        boolean enable = updatedAlarm.isEnable();
        sqlFormat = "update Alarm set" +
                " Enable = '%b'" +
                " where IdAlarm = %d";
        sql = String.format(sqlFormat, enable, idAlarm);
        db.execSQL(sql);
    }
    public void updateAlarmEnable(int idAlarm, boolean enable){
        sqlFormat = "update Alarm set" +
                " Enable = '%b'" +
                " where IdAlarm = %d";
        sql = String.format(sqlFormat, enable, idAlarm);
        db.execSQL(sql);
    }
    public void updateAlarmSetDefaultRingtone(int idAlarm){
        sqlFormat = "update Alarm set RingtoneUrl = '%s', RingtoneName = '%s' where IdAlarm = %d";
        sql = String.format(sqlFormat, Music.defaultRingtoneUrl, Music.defaultRingtoneName, idAlarm);
        db.execSQL(sql);
    }
    public void deleteAlarm(Alarm alarm){
        sqlFormat = "delete from Alarm where IdAlarm = %d";
        sql = String.format(sqlFormat, alarm.getIdAlarm());
        db.execSQL(sql);
        deleteChallengeDetail(alarm.getIdAlarm(), alarm.getChallengeType());
    }

    public Alarm getRecentAddedAlarm() {
        sql = "select * from Alarm order by IdAlarm desc limit 1";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();
        return buildAlarmFromCursor(cursor, true);
    }

    public Alarm getTodayNextAlarm() {
        Calendar now = Calendar.getInstance();
        int nowWeekDay = now.get(Calendar.DAY_OF_WEEK);
        int nowHour = now.get(Calendar.HOUR_OF_DAY);
        int nowMinute = now.get(Calendar.MINUTE);
        String dayOfWeek = getDayOfWeekInString(nowWeekDay);
        sqlFormat = "select * from Alarm" +
                " where Enable = 'true' and %s = 'true' and ((Hour > %d) or (Hour = %d and Minute > %d))" +
                " order by Hour, Minute";
        sql = String.format(sqlFormat, dayOfWeek, nowHour, nowHour, nowMinute);
        Cursor cursor = db.rawQuery(sql, null);
        if (!cursor.moveToNext()) {
            return null;
        }
        return buildAlarmFromCursor(cursor, nowWeekDay, true);
    }
    public List<Alarm> getAllAlarm(){
        sql = "select * from Alarm";
        Cursor cursor = db.rawQuery(sql, null);
        return buildListAlarmFromCursor(cursor, true);
    }
    public List<Alarm> getAllEnabledAlarmInOrder(){
        List<Alarm> listAlarm = new ArrayList<>();
        sql = "select * from Alarm where Enable = 'true' order by Hour, Minute";
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            listAlarm.add(buildAlarmFromCursor(cursor, false));
        }
        cursor.close();
        return listAlarm;
    }
    public List<Alarm> getAllDisabledAlarmInOrder(){
        List<Alarm> listAlarm = new ArrayList<>();
        sql = "select * from Alarm where Enable = 'false' order by Hour, Minute";
        Cursor cursor = db.rawQuery(sql, null);
        return buildListAlarmFromCursor(cursor, true);
    }

    public Alarm getTheNearestAlarm() {
        Cursor cursor;
        Calendar now = Calendar.getInstance();
        int nowWeekDay = now.get(Calendar.DAY_OF_WEEK);
        int nowHour = now.get(Calendar.HOUR_OF_DAY);
        int nowMinute = now.get(Calendar.MINUTE);

        String weekDayToCompare = getDayOfWeekInString(nowWeekDay);
        sqlFormat = "select * from Alarm" +
                " where Enable = 'true' and %s = 'true' and ((Hour > %d) or (Hour = %d and Minute > %d))" +
                " order by Hour, Minute";
        sql = String.format(sqlFormat, weekDayToCompare, nowHour, nowHour, nowMinute);
        cursor = db.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            return buildAlarmFromCursor(cursor, nowWeekDay, true);
        }


        sqlFormat = "select * from Alarm" +
                " where Enable = 'true' and %s = 'true'" +
                " order by Hour, Minute";
        for (int i = nowWeekDay + 1; i <= 7; i++) {
            weekDayToCompare = getDayOfWeekInString(i);
            sql = String.format(sqlFormat, weekDayToCompare);
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                return buildAlarmFromCursor(cursor, i, true);
            }
        }

        for (int i = 8; i < nowWeekDay + 7; i++) {
            int mod = i % 7;
            weekDayToCompare = getDayOfWeekInString(mod);
            sql = String.format(sqlFormat, weekDayToCompare);
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                return buildAlarmFromCursor(cursor, mod, true);
            }
        }
        return null;
    }
    private Alarm buildAlarmFromCursor(Cursor cursor, int dayOfWeek, boolean closeCursor){
        if (cursor.isBeforeFirst()) {
            throw new RuntimeException("Cursor is at position -1. Either there is not any row in the query or the cursor has not moved to the first row in the result set yet.");
        }
        List<Boolean> listRepeatDay = new ArrayList<>();
        int idAlarm = getValueAtColumn(cursor, "IdAlarm", Integer.class);
        boolean enable = getValueAtColumn(cursor, "Enable", Boolean.class);
        int hour = getValueAtColumn(cursor, "Hour", Integer.class);
        int minute = getValueAtColumn(cursor, "Minute", Integer.class);
        listRepeatDay.add(getValueAtColumn(cursor, "Monday", Boolean.class));
        listRepeatDay.add(getValueAtColumn(cursor, "Tuesday", Boolean.class));
        listRepeatDay.add(getValueAtColumn(cursor, "Wednesday", Boolean.class));
        listRepeatDay.add(getValueAtColumn(cursor, "Thursday", Boolean.class));
        listRepeatDay.add(getValueAtColumn(cursor, "Friday", Boolean.class));
        listRepeatDay.add(getValueAtColumn(cursor, "Saturday", Boolean.class));
        listRepeatDay.add(getValueAtColumn(cursor, "Sunday", Boolean.class));
        String ringtoneUrl = getValueAtColumn(cursor, "RingtoneUrl", String.class);
        String ringtoneName = getValueAtColumn(cursor, "RingtoneName", String.class);
        String label = getValueAtColumn(cursor, "Label", String.class);
        boolean vibrate = getValueAtColumn(cursor, "Vibrate", Boolean.class);
        int snoozeTime = getValueAtColumn(cursor, "SnoozeTime", Integer.class);
        int volume = getValueAtColumn(cursor, "Volume", Integer.class);
        ChallengeType challengeType = ChallengeType.newInstanceFromValue(getValueAtColumn(cursor, "ChallengeType", Integer.class));
        if (closeCursor) {
            cursor.close();
        }
        Alarm alarm = new Alarm(idAlarm, enable, hour, minute, listRepeatDay, new Music(ringtoneUrl, ringtoneName), label, snoozeTime, vibrate, volume, challengeType);
        alarm.setDayOfWeek(dayOfWeek);
        return alarm;
    }
    private Alarm buildAlarmFromCursor(Cursor cursor, boolean closeCursor) {
        if (cursor.isBeforeFirst()) {
            throw new RuntimeException("Cursor is at position -1. Either there is not any row in the query or the cursor has not moved to the first row in the result set yet.");
        }
        List<Boolean> listRepeatDay = new ArrayList<>();
        int idAlarm = getValueAtColumn(cursor, "IdAlarm", Integer.class);
        boolean enable = getValueAtColumn(cursor, "Enable", Boolean.class);
        int hour = getValueAtColumn(cursor, "Hour", Integer.class);
        int minute = getValueAtColumn(cursor, "Minute", Integer.class);
        listRepeatDay.add(getValueAtColumn(cursor, "Monday", Boolean.class));
        listRepeatDay.add(getValueAtColumn(cursor, "Tuesday", Boolean.class));
        listRepeatDay.add(getValueAtColumn(cursor, "Wednesday", Boolean.class));
        listRepeatDay.add(getValueAtColumn(cursor, "Thursday", Boolean.class));
        listRepeatDay.add(getValueAtColumn(cursor, "Friday", Boolean.class));
        listRepeatDay.add(getValueAtColumn(cursor, "Saturday", Boolean.class));
        listRepeatDay.add(getValueAtColumn(cursor, "Sunday", Boolean.class));
        String ringtoneUrl = getValueAtColumn(cursor, "RingtoneUrl", String.class);
        String ringtoneName = getValueAtColumn(cursor, "RingtoneName", String.class);
        String label = getValueAtColumn(cursor, "Label", String.class);
        boolean vibrate = getValueAtColumn(cursor, "Vibrate", Boolean.class);
        int snoozeTime = getValueAtColumn(cursor, "SnoozeTime", Integer.class);
        int volume = getValueAtColumn(cursor, "Volume", Integer.class);
        ChallengeType challengeType = ChallengeType.newInstanceFromValue(getValueAtColumn(cursor, "ChallengeType", Integer.class));
        if (closeCursor) {
            cursor.close();
        }
        return new Alarm(idAlarm, enable, hour, minute, listRepeatDay, new Music(ringtoneUrl, ringtoneName), label, snoozeTime, vibrate, volume, challengeType);
    }
    private List<Alarm> buildListAlarmFromCursor(Cursor cursor, boolean closeCursor){
        List<Alarm> listAlarm = new ArrayList<>();
        while(cursor.moveToNext()){
            List<Boolean> listRepeatDay = new ArrayList<>();
            int idAlarm = getValueAtColumn(cursor, "IdAlarm", Integer.class);
            boolean enable = getValueAtColumn(cursor, "Enable", Boolean.class);
            int hour = getValueAtColumn(cursor, "Hour", Integer.class);
            int minute = getValueAtColumn(cursor, "Minute", Integer.class);
            listRepeatDay.add(getValueAtColumn(cursor, "Monday", Boolean.class));
            listRepeatDay.add(getValueAtColumn(cursor, "Tuesday", Boolean.class));
            listRepeatDay.add(getValueAtColumn(cursor, "Wednesday", Boolean.class));
            listRepeatDay.add(getValueAtColumn(cursor, "Thursday", Boolean.class));
            listRepeatDay.add(getValueAtColumn(cursor, "Friday", Boolean.class));
            listRepeatDay.add(getValueAtColumn(cursor, "Saturday", Boolean.class));
            listRepeatDay.add(getValueAtColumn(cursor, "Sunday", Boolean.class));
            String ringtoneUrl = getValueAtColumn(cursor, "RingtoneUrl", String.class);
            String ringtoneName = getValueAtColumn(cursor, "RingtoneName", String.class);
            String label = getValueAtColumn(cursor, "Label", String.class);
            boolean vibrate = getValueAtColumn(cursor, "Vibrate", Boolean.class);
            int snoozeTime = getValueAtColumn(cursor, "SnoozeTime", Integer.class);
            int volume = getValueAtColumn(cursor, "Volume", Integer.class);
            ChallengeType challengeType = ChallengeType.newInstanceFromValue(getValueAtColumn(cursor, "ChallengeType", Integer.class));
            Alarm alarm = new Alarm(idAlarm, enable, hour, minute, listRepeatDay, new Music(ringtoneUrl, ringtoneName), label, snoozeTime, vibrate, volume, challengeType);
            listAlarm.add(alarm);
        }
        if (closeCursor) {
            cursor.close();
        }
        return listAlarm;
    }










    public void deleteChallengeDetail(int idAlarm, ChallengeType challengeType){
        switch(challengeType){
            case DEFAULT:
                return;
            case MATH:
                deleteMathDetail(idAlarm);
                break;
            case SHAKE:
                deleteShakeDetail(idAlarm);
                break;
            case MOVING:
                deleteMovingDetail(idAlarm);
                break;
        }
    }
    public void insertMathDetail(MathDetail mathDetail){
        sqlFormat = "insert into MathDetail(IdAlarm, Difficulty, NumberOfProblem)" +
                " values(%d, %d, %d)";
        sql = String.format(sqlFormat, mathDetail.getIdAlarm(), mathDetail.getDifficulty(), mathDetail.getNumberOfProblem());
        db.execSQL(sql);
    }
    public void insertMathDetail(int idAlarm, int difficulty, int numberOfProblem){
        sqlFormat = "insert into MathDetail(IdAlarm, Difficulty, NumberOfProblem)" +
                " values(%d, %d, %d)";
        sql = String.format(sqlFormat, idAlarm, difficulty, numberOfProblem);
        db.execSQL(sql);
    }
    public void updateMathDetail(MathDetail mathDetail){
        sqlFormat = "update MathDetail set Difficulty = %d, NumberOfProblem = %d where IdAlarm = %d";
        sql = String.format(sqlFormat, mathDetail.getDifficulty(), mathDetail.getNumberOfProblem(), mathDetail.getIdAlarm());
        db.execSQL(sql);
    }
    public void deleteMathDetail(int idAlarm){
        sqlFormat = "delete from MathDetail where IdAlarm = %d";
        sql = String.format(sqlFormat, idAlarm);
        db.execSQL(sql);
    }
    public MathDetail getAlarmMathDetail(int idAlarm){
        sqlFormat = "select * from MathDetail where IdAlarm = %d";
        sql = String.format(sqlFormat, idAlarm);
        Cursor cursor = db.rawQuery(sql, null);
        if(!cursor.moveToNext()){
            cursor.close();
            return null;
        }
        int difficulty = getValueAtColumn(cursor, "Difficulty", Integer.class);
        int numberOfProblem = getValueAtColumn(cursor, "NumberOfProblem", Integer.class);
        cursor.close();
        return new MathDetail(idAlarm, difficulty, numberOfProblem);
    }









    public void insertShakeDetail(int idAlarm, int difficulty, int numberOfProblem){
        sqlFormat = "insert into ShakeDetail(IdAlarm, Difficulty, NumberOfProblem)" +
                " values(%d, %d, %d)";
        sql = String.format(sqlFormat, idAlarm, difficulty, numberOfProblem);
        db.execSQL(sql);
    }
    public void insertShakeDetail(ShakeDetail shakeDetail){
        sqlFormat = "insert into ShakeDetail(IdAlarm, Difficulty, NumberOfProblem)" +
                " values(%d, %d, %d)";
        sql = String.format(sqlFormat, shakeDetail.getIdAlarm(), shakeDetail.getDifficulty(), shakeDetail.getNumberOfProblem());
        db.execSQL(sql);
    }
    public void updateShakeDetail(ShakeDetail shakeDetail){
        sqlFormat = "update ShakeDetail set Difficulty = %d, NumberOfProblem = %d where IdAlarm = %d";
        sql = String.format(sqlFormat, shakeDetail.getDifficulty(), shakeDetail.getNumberOfProblem(), shakeDetail.getIdAlarm());
        db.execSQL(sql);
    }
    public void deleteShakeDetail(int idAlarm){
        sqlFormat = "delete from ShakeDetail where IdAlarm = %d";
        sql = String.format(sqlFormat, idAlarm);
        db.execSQL(sql);
    }
    public ShakeDetail getAlarmShakeDetail(int idAlarm){
        sqlFormat = "select * from ShakeDetail where IdAlarm = %d";
        sql = String.format(sqlFormat, idAlarm);
        Cursor cursor = db.rawQuery(sql, null);
        if(!cursor.moveToNext()){
            cursor.close();
            return null;
        }
        int difficulty = getValueAtColumn(cursor, "Difficulty", Integer.class);
        int numberOfProblem = getValueAtColumn(cursor, "NumberOfProblem", Integer.class);
        cursor.close();
        return new ShakeDetail(idAlarm, difficulty, numberOfProblem);
    }





    public void insertMovingDetail(int idAlarm, float distance, MeasurementPicker.Measurement measurement, ChallengeType alternativeChallenge){
        sqlFormat = "insert into MovingDetail(IdAlarm, Distance, Measurement, AlternativeChallenge)" +
                " values(%d, %d, %d, %d)";
        sql = String.format(sqlFormat, idAlarm, distance, measurement.getIntValue(), alternativeChallenge.getValue());
        db.execSQL(sql);
    }
    public void insertMovingDetail(MovingDetail movingDetail){
        sqlFormat = "insert into MovingDetail(IdAlarm, Distance, Measurement, AlternativeChallenge)" +
                " values(%d, %d, %d, %d)";
        sql = String.format(sqlFormat, movingDetail.getIdAlarm(), movingDetail.getDistance(), movingDetail.getMeasurement().getIntValue(), movingDetail.getAlternativeChallenge().getValue());
        db.execSQL(sql);
        switch(movingDetail.getAlternativeChallenge()){
            case MATH:
                movingDetail.getMathDetail().setIdAlarm(movingDetail.getIdAlarm());
                insertMathDetail(movingDetail.getMathDetail());
                break;
            case SHAKE:
                movingDetail.getShakeDetail().setIdAlarm(movingDetail.getIdAlarm());
                insertShakeDetail(movingDetail.getShakeDetail());
                break;
        }
    }
    public void updateMovingDetail(MovingDetail movingDetail){
        sqlFormat = "update MovingDetail set Distance = %d, Measurement = %d, AlternativeChallenge = %d where IdAlarm = %d";
        sql = String.format(sqlFormat, movingDetail.getDistance(), movingDetail.getMeasurement().getIntValue(), movingDetail.getAlternativeChallenge().getValue(), movingDetail.getIdAlarm());
        db.execSQL(sql);
    }
    public void deleteMovingDetail(int idAlarm){
        sqlFormat = "delete from MathDetail where IdAlarm = %d";
        sql = String.format(sqlFormat, idAlarm);
        db.execSQL(sql);
        sqlFormat = "delete from ShakeDetail where IdAlarm = %d";
        sql = String.format(sqlFormat, idAlarm);
        db.execSQL(sql);
        sqlFormat = "delete from MovingDetail where IdAlarm = %d";
        sql = String.format(sqlFormat, idAlarm);
        db.execSQL(sql);
    }
    public MovingDetail getAlarmMovingDetail(int idAlarm){
        sqlFormat = "select * from MovingDetail where IdAlarm = %d";
        sql = String.format(sqlFormat, idAlarm);
        Cursor cursor = db.rawQuery(sql, null);
        if(!cursor.moveToNext()){
            cursor.close();
            return null;
        }
        int distance = getValueAtColumn(cursor, "Distance", Integer.class);
        MeasurementPicker.Measurement measurement = MeasurementPicker.Measurement.newInstanceFromIntValue(getValueAtColumn(cursor, "Measurement", Integer.class));
        ChallengeType alternativeChallenge = ChallengeType.newInstanceFromValue(getValueAtColumn(cursor, "AlternativeChallenge", Integer.class));
        cursor.close();
        return new MovingDetail(idAlarm, distance, measurement, alternativeChallenge);
    }





    private <T> T getValueAtColumn(Cursor cursor, String columnName, Class<T> columnDataType){
        int columnIndex = cursor.getColumnIndex(columnName);
        if(columnIndex == -1){
            throw new RuntimeException("No such column name: " + columnName + ".");
        }
        if(columnDataType == Integer.class){
            return columnDataType.cast(cursor.getInt(columnIndex));
        }
        else if(columnDataType == Float.class){
            return columnDataType.cast(cursor.getFloat(columnIndex));
        }
        else if(columnDataType == Boolean.class){
            return columnDataType.cast(cursor.getString(columnIndex).equals("true"));
        }
        else if(columnDataType == String.class){
            return columnDataType.cast(cursor.getString(columnIndex));
        }
        else{
            // TODO: else block here just to avoid required return statement outside this if-else sequence
            return columnDataType.cast(cursor.getDouble(columnIndex));
        }
    }
    public String getDayOfWeekInString(int dayOfWeekInInteger) {
        switch (dayOfWeekInInteger) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
            default:
                return "";
        }
    }

    public boolean checkIfThereIsAnyAlarm() {
        sql = "select * from Alarm where Enable = 'true'";
        Cursor cursor = db.rawQuery(sql, null);
        if (!cursor.moveToNext()) {
            return false;
        } else {
            cursor.close();
            return true;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}