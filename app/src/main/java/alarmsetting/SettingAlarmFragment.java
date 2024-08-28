package alarmsetting;

import static com.example.appbaothuc.appsetting.AppSettingFragment.HOUR_MODE_24;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.appbaothuc.Music;
import com.example.appbaothuc.R;
import com.example.appbaothuc.UpcomingAlarmFragment;
import com.example.appbaothuc.appsetting.AppSettingFragment;
import com.example.appbaothuc.models.Alarm;
import com.example.appbaothuc.models.ChallengeType;
import com.example.appbaothuc.models.MathDetail;
import com.example.appbaothuc.models.MovingDetail;
import com.example.appbaothuc.models.ShakeDetail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SettingAlarmFragment extends Fragment implements LableDialogFragment.LabelDialogListener,
        AgainDialogFragment.AgainDialogListener, RepeatDialogFragment.RepeatDialogListener,
        View.OnClickListener, Animation.AnimationListener {
    private Context context;
    private UpcomingAlarmFragment upcomingAlarmFragment;
    private SettingAlarmMode settingAlarmMode; //enum ADD EDIT
    private Alarm alarm;
    private ChallengeType currentChallengeType; // Loai thu thach
    private MathDetail mathDetail;
    private ShakeDetail shakeDetail;
    
    private Animation animFadein, animBlink;
    private MovingDetail movingDetail;
    private Music ringtone;

    private TimePicker timePicker; // Chọn giờ
    private Button btnPlayMusic, btnCancel, btnDelete, btnOk; //Phát nhạc đang chọn, Hủy thao tác, Xóa báo thức, Hoàn tất
    private LinearLayout linearLayoutLabel, linearLayoutType, linearLayoutRingTone,
            linearLayoutRepeat, linearLayoutAgain, layoutSettingAlarm;
    private TextView textViewPlus10M, textViewMinus10M, textViewPlus1H,
            textViewMinus1H;
    private TextView textViewType, textViewRepeat,
            textViewRingtone, textViewAgain, textViewLabel;
    private ImageView imageViewType, imageView4, imageView2; //cái hình điện thoại rung
    private SeekBar seekBar; // thanh âm lượng
    private Switch aSwitch; // bật tắt rung

//    private Music music;
    private String label, outputAgain, outputRepeat;
    private int snoozeTime;
    private List<Boolean> listRepeatDay; // List cac ngay lap lai

    private TypeFragment typeFragment;
    private RepeatDialogFragment repeatDialogFragment;
    private FragmentManager fragmentManager;
    private MusicPickerFragment musicPickerFragment;

    private MediaPlayer mediaPlayer;

    public void configure(UpcomingAlarmFragment upcomingAlarmFragment, Alarm alarm){
        this.upcomingAlarmFragment = upcomingAlarmFragment;
        this.alarm = alarm;
        if(alarm == null){
            this.settingAlarmMode = SettingAlarmMode.ADD_NEW;
            this.alarm = Alarm.obtain();
            this.shakeDetail = ShakeDetail.obtain(this.alarm.getIdAlarm());
        }
        else{
            this.settingAlarmMode = SettingAlarmMode.EDIT;
        }
        this.currentChallengeType = this.alarm.getChallengeType();
        this.ringtone = this.alarm.getRingtone();
        this.typeFragment = new TypeFragment();
        this.musicPickerFragment = new MusicPickerFragment();
        this.repeatDialogFragment = new RepeatDialogFragment();
        this.mediaPlayer = new MediaPlayer();

        this.musicPickerFragment.setOnFinishPickMusicListener(new MusicPickerFragment.OnFinishPickMusicListener() {
            @Override
            public void onFinishPickMusic(Music music) {
                ringtone = music;
                textViewRingtone.setText(music.getName());
            }
        });

        this.typeFragment.setTypeFragmentListener(new TypeFragment.TypeFragmentListener() {
            @Override
            public void getDefaultChallenge() {
                currentChallengeType = ChallengeType.DEFAULT;
                imageViewType.setImageDrawable(context.getDrawable(R.drawable.ic_alarm_60dp));
                textViewType.setText("Default");
            }

            @Override
            public void getMathChallenge(MathDetail mathDetail) {
                currentChallengeType = ChallengeType.MATH;
                SettingAlarmFragment.this.mathDetail = mathDetail;
                imageViewType.setImageDrawable(context.getDrawable(R.drawable.ic_math_36));
                textViewType.setText("Math");
            }

            @Override
            public void getShakeChallenge(ShakeDetail shakeDetail) {
                currentChallengeType = ChallengeType.SHAKE;
                SettingAlarmFragment.this.shakeDetail = shakeDetail;
                imageViewType.setImageDrawable(context.getDrawable(R.drawable.icons8_shake_phone_60));
                textViewType.setText("Shake");
            }

            @Override
            public void getMovingChallenge(MovingDetail movingDetail) {
                currentChallengeType = ChallengeType.MOVING;
                SettingAlarmFragment.this.movingDetail = movingDetail;
                imageViewType.setImageDrawable(context.getDrawable(R.drawable.ic_move_white_64px));
                textViewType.setText("Moving");
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_alarm, container, false);
        setControl(view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.context = null;
        this.alarm = null;
        if(this.mediaPlayer.isPlaying()){
            this.mediaPlayer.release();
        }
    }

    void setControl(View view){
        listRepeatDay = alarm.getListRepeatDay();
        btnPlayMusic = view.findViewById(R.id.btnPlayMusic);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnOk = view.findViewById(R.id.btnOk);
        linearLayoutType = view.findViewById(R.id.linearLayoutType);
        linearLayoutRingTone = view.findViewById(R.id.linearLayoutRingTone);
        linearLayoutRepeat = view.findViewById(R.id.linearLayoutRepeat);
        linearLayoutAgain = view.findViewById(R.id.linearLayoutAgain);
        linearLayoutLabel = view.findViewById(R.id.linearLayoutLabel);
        layoutSettingAlarm = view.findViewById(R.id.layoutSettingAlarm);

        //textViewTimeLeft = view.findViewById(R.id.textViewTimeLeft);
        textViewPlus10M = view.findViewById(R.id.textViewPlus10M);
        textViewMinus10M = view.findViewById(R.id.textViewMinus10M);
        textViewPlus1H = view.findViewById(R.id.textViewPlus1H);
        textViewMinus1H = view.findViewById(R.id.textViewMinus1H);
        textViewType = view.findViewById(R.id.textViewType);
        textViewRepeat = view.findViewById(R.id.textViewRepeat);
        textViewRingtone = view.findViewById(R.id.textViewRingTone);
        textViewAgain = view.findViewById(R.id.textViewAgain);
        textViewLabel = view.findViewById(R.id.textViewLabel);
        imageView4 = view.findViewById(R.id.imageView4);
        imageView2 = view.findViewById(R.id.imageView2);

        imageViewType = view.findViewById(R.id.imageViewType);
        seekBar = view.findViewById(R.id.seekBar);
        aSwitch = view.findViewById(R.id.aSwitch);
        timePicker = view.findViewById(R.id.timePicker);

        if(AppSettingFragment.hourMode == HOUR_MODE_24){
            timePicker.setIs24HourView(true);
        }
        else{
            timePicker.setIs24HourView(false);
        }

        animFadein = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        animFadein.setAnimationListener(this);
        layoutSettingAlarm.startAnimation(animFadein);

        animBlink = AnimationUtils.loadAnimation(getContext(), R.anim.anim_lac);
        animBlink.setAnimationListener(this);
        imageView4.startAnimation(animBlink);


        btnOk.setOnClickListener(new View.OnClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                outputAgain = textViewAgain.getText().toString();
                label = textViewLabel.getText().toString();
//
//                if(Build.VERSION.SDK_INT >= 23){
//                    hour = timePicker.getHour();
//                    minute = timePicker.getMinute();
//                }
//                else{
//                    hour = timePicker.getCurrentHour();
//                    minute = timePicker.getCurrentMinute();
//                }
                timePicker.getCurrentHour();

                alarm.setHour(getHour(timePicker));
                alarm.setMinute(getMinute(timePicker));
                alarm.setRingtone(ringtone);
                alarm.setVibrate(aSwitch.isChecked());
                alarm.setSnoozeTime(snoozeTime);
                alarm.setVolume(seekBar.getProgress());
                alarm.setLabel(label);
                if (settingAlarmMode == SettingAlarmMode.EDIT) {
                    editAlarm();
                } else if (settingAlarmMode == SettingAlarmMode.ADD_NEW) {
                    addAlarm();
                }
                fragmentManager.popBackStack();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(settingAlarmMode == SettingAlarmMode.EDIT){ // EDIT thi moi cho delete
                    upcomingAlarmFragment.deleteAlarm(alarm);
                    fragmentManager.popBackStack();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.popBackStack();
            }
        });
        linearLayoutType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeFragment.configure(alarm, currentChallengeType);
                fragmentManager.beginTransaction().add(R.id.full_screen_fragment_container, typeFragment).addToBackStack(null).commit();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(seekBar.getProgress() == 0) seekBar.setProgress(500);
                else seekBar.setProgress(0);
            }
        });
        linearLayoutRingTone.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    btnPlayMusic.setBackground(context.getDrawable(R.drawable.ic_play_arrow_24dp));
                }
                musicPickerFragment.setAlarm(alarm);
                fragmentManager.beginTransaction().add(R.id.full_screen_fragment_container, musicPickerFragment).addToBackStack(null).commit();
            }
        });
        linearLayoutLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLableDialog();
            }
        });

        linearLayoutAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAgainDialog();
            }
        });

        linearLayoutRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRepeatDialog();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(seekBar.getProgress() == 0) {
                    imageView2.setBackground(getContext().getDrawable(R.drawable.ic_volume_off_black_24dp));
                }
                else {
                    imageView2.setBackground(getContext().getDrawable(R.drawable.ic_volume_up_24dp));
                }
                alarm.setVolume(progress);
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.setVolume(progress/1000f, progress/1000f);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnPlayMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!alarm.validateRingtoneUrl(context)){
                    textViewRingtone.setText(alarm.getRingtone().getName());
                }
                if(!mediaPlayer.isPlaying()){
                    mediaPlayer = MediaPlayer.create(context, Uri.parse(alarm.getRingtone().getUrl()));
                    btnPlayMusic.setBackground(context.getDrawable(R.drawable.ic_pause_black_24dp));
                    mediaPlayer.setLooping(true);
                    mediaPlayer.setVolume(seekBar.getProgress()/1000f,
                            seekBar.getProgress()/1000f);
                    mediaPlayer.start();
                }
                else{
                    btnPlayMusic.setBackground(context.getDrawable(R.drawable.ic_play_arrow_24dp));
                    mediaPlayer.stop();
                }
            }
        });


        switch(alarm.getChallengeType()){
            case DEFAULT:
                imageViewType.setImageDrawable(context.getDrawable(R.drawable.ic_alarm));
                textViewType.setText("Default");
                break;
            case MATH:
                imageViewType.setImageDrawable(context.getDrawable(R.drawable.ic_math_36));
                textViewType.setText("Math");
                break;
            case SHAKE:
                imageViewType.setImageDrawable(context.getDrawable(R.drawable.icons8_shake_phone_60));
                textViewType.setText("Shake");
                break;
            case MOVING:
                imageViewType.setImageDrawable(context.getDrawable(R.drawable.ic_move_white_64px));
                textViewType.setText("Moving");
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mediaPlayer.isPlaying()){
            btnPlayMusic.setBackground(context.getDrawable(R.drawable.ic_play_arrow_24dp));
            mediaPlayer.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(settingAlarmMode == SettingAlarmMode.ADD_NEW){
            Calendar now = Calendar.getInstance();
            now.add(Calendar.MINUTE, 1);
            setHour(timePicker, now.get(Calendar.HOUR_OF_DAY));
            setMinute(timePicker, now.get(Calendar.MINUTE));
        }
        else if(settingAlarmMode == SettingAlarmMode.EDIT){
            setHour(timePicker, alarm.getHour());
            setMinute(timePicker,alarm.getMinute());
        }
        textViewRepeat.setText(alarm.getDescribeRepeatDay());
        seekBar.setProgress(alarm.getVolume());
        aSwitch.setChecked(alarm.isVibrate());
        if(alarm.getLabel() != null && !alarm.getLabel().equals("null")){
            textViewLabel.setText(alarm.getLabel());
        }

        String alarmTextAgain;
        if(alarm.getSnoozeTime() == 0) alarmTextAgain = "Tắt.";
        else alarmTextAgain = alarm.getSnoozeTime() + " Phút.";
        textViewAgain.setText(alarmTextAgain);

        textViewRingtone.setText(alarm.getRingtone().getName());

        textViewMinus1H.setOnClickListener(this); // event trong hàm onClick()
        textViewMinus10M.setOnClickListener(this);
        textViewPlus1H.setOnClickListener(this);
        textViewPlus10M.setOnClickListener(this);

        fragmentManager = getFragmentManager();
        typeFragment.setEnterTransition(new Slide(Gravity.TOP));
        typeFragment.setExitTransition(new Slide(Gravity.TOP));

        //musicPickerFragment = MusicPickerFragment.newInstance(this, alarm);
        musicPickerFragment.setEnterTransition(new Slide(Gravity.BOTTOM));
        musicPickerFragment.setExitTransition(new Slide(Gravity.BOTTOM));

//        String textType = null;

//        if(challengeType == 0) textType = "Default";
//        else if(challengeType == 1) textType = "Math problems";
//        else if(challengeType == 2) textType = "Shake";
//        textViewType.setText(textType);
    }
    private int getHour(TimePicker timePicker){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return timePicker.getHour();
        }
        else {
            return timePicker.getCurrentHour();
        }
    }
    private int getMinute(TimePicker timePicker){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return timePicker.getMinute();
        }
        else {
            return timePicker.getCurrentMinute();
        }
    }
    private void setHour(TimePicker timePicker, int hour){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            timePicker.setHour(hour);
        }
        else {
            timePicker.setCurrentHour(hour);
        }
    }
    private void setMinute(TimePicker timePicker, int minute){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            timePicker.setMinute(minute);
        }
        else {
            timePicker.setCurrentMinute(minute);
        }
    }

    @Override
    public void onClick(View view) {
        int timeHour = getHour(timePicker);
        int timeMinute = getMinute(timePicker);
        switch (view.getId()){
            case R.id.textViewMinus1H:  // bấm trừ 1 giờ
                if(timeHour == 0) setHour(timePicker, 23);
                else  setHour(timePicker, timeHour - 1);
                break;
            case R.id.textViewPlus1H:   // bấm cộng 1 giờ
                if(timeHour == 23) setHour(timePicker, 0);
                else  setHour(timePicker, timeHour + 1);
                break;
            case R.id.textViewMinus10M: // Bấm trừ 10 phút
                if(timeMinute - 10 < 0) {   // nếu trừ 10 phút mà ra số âm thì phải trừ giờ xuống 1.
                    if(timeHour == 0) setHour(timePicker, 23);
                    else  setHour(timePicker, timeHour - 1);
                    setMinute(timePicker, 60 - (10 - timeMinute));   // phút thì còn tầm 5 mươi mấy đó theo công thức
                }
                else setMinute(timePicker, timeMinute - 10);
                break;
            case R.id.textViewPlus10M:
                if(timeMinute + 10 > 59) {
                    if(timeHour == 23) setHour(timePicker, 0);
                    else setHour(timePicker, timeHour + 1);
                    setMinute(timePicker, 10 + timeMinute - 60);
                }
                else setMinute(timePicker, timeMinute + 10);
                break;
        }
    }
    private void showLableDialog() { // show fragment để Sửa tên báo thức
        LableDialogFragment lableDialogFragment = new LableDialogFragment();
        lableDialogFragment.configure(this, alarm);
        lableDialogFragment.setListener(this);
        lableDialogFragment.show(getChildFragmentManager(), "fragment_edit_name");
    }
    @Override
    public void onFinishEditDialog(String inputText) { //get text ở label dialog fragment
        textViewLabel.setText(inputText);
    }


    private void showAgainDialog() { // fragment chọn thời gian báo thức lại
        AgainDialogFragment againDialogFragment = new AgainDialogFragment();
        againDialogFragment.setListener(this);
        againDialogFragment.show(getChildFragmentManager(), "fragment_choice");
    }
    @Override
    public void onFinishChoiceDialog(Integer input) {
        snoozeTime = input;
        if(input == 0) textViewAgain.setText("Tắt.");
        else textViewAgain.setText(input + " Phút.");
    }

    private void showRepeatDialog() { // fragment chọn các ngày báo thức
        repeatDialogFragment.setListener(this);
        repeatDialogFragment.show(getChildFragmentManager(), "fragment_repeat");
    }
    @Override
    public void onFinishCheckDialog(ArrayList<Boolean> input) {
        listRepeatDay = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            listRepeatDay.add(i, false);
            listRepeatDay.set(i, input.get(i));
        }
        //createStringRepeat(input);
        alarm.setListRepeatDay(listRepeatDay);
        createStringRepeat();
    }
    public void createStringRepeat(){
        textViewRepeat.setText(alarm.getDescribeRepeatDay());
    }
    public void createStringRepeat(ArrayList<Boolean> listDays){
        //TODO: aaaa
        String repeatString = "";
        int i = 0;
        for(i = 0; i < 7; i++){
            if(listDays.get(i) == false) break;
        }
        if(i == 7) {
            outputRepeat = "Hằng ngày.";
            textViewRepeat.setText(outputRepeat);
            return;
        }
        else if(i == 5){
            outputRepeat = "Các ngày làm việc.";
            textViewRepeat.setText(outputRepeat);
            return;
        }
        i = 0;
        for(i = 0; i < 5; i++){
            if(listDays.get(i) == true) break;
        }
        if(i == 5 && listDays.get(5) == true && listDays.get(6) == true ){
            outputRepeat = "Cuối tuần.";
            textViewRepeat.setText(outputRepeat);
            return;
        }
        else {
            for(int j = 0; j < 7; j++){
                if(listDays.get(j) == true){
                    if(j == 6) repeatString += " CN";
                    else repeatString += " T" + (j+2);
                }
            }
            outputRepeat = repeatString;
            textViewRepeat.setText(outputRepeat);
        }
    }

    private void addAlarm(){
        switch(currentChallengeType){
            case DEFAULT:
                upcomingAlarmFragment.addAlarm(alarm);
                break;
            case MATH:
                upcomingAlarmFragment.addAlarm(alarm, mathDetail);
                break;
            case SHAKE:
                upcomingAlarmFragment.addAlarm(alarm, shakeDetail);
                break;
            case MOVING:
                upcomingAlarmFragment.addAlarm(alarm, movingDetail);
                break;
        }
    }

    private void editAlarm(){
        switch(currentChallengeType){
            case DEFAULT:
                upcomingAlarmFragment.editAlarm(alarm);
                break;
            case MATH:
                upcomingAlarmFragment.editAlarm(alarm, mathDetail);
                break;
            case SHAKE:
                upcomingAlarmFragment.editAlarm(alarm, shakeDetail);
                break;
            case MOVING:
                upcomingAlarmFragment.editAlarm(alarm, movingDetail);
                break;
        }
    }

    public Alarm getAlarm() {
        return alarm;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    enum SettingAlarmMode{
        ADD_NEW, EDIT
    }
}