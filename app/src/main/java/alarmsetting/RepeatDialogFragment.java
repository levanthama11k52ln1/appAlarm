package alarmsetting;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.appbaothuc.R;
import com.example.appbaothuc.models.Alarm;

import java.util.ArrayList;

public class RepeatDialogFragment extends DialogFragment implements Animation.AnimationListener {
    private Alarm alarm;
    private Button btnOK, btnCancel, btnDays, btnWeekend;
    private CheckBox checkBoxT2, checkBoxT3, checkBoxT4, checkBoxT5, checkBoxT6,
            checkBoxT7, checkBoxCN;
    public static ArrayList<Boolean> listDays = new ArrayList<>();
    private LinearLayout layoutRepeatDialog;
    private Animation animFadein;

    public RepeatDialogFragment(){
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

    public interface RepeatDialogListener{
        void onFinishCheckDialog(ArrayList<Boolean> arrayList);
    }

    private RepeatDialogListener listener;
    public void setListener(SettingAlarmFragment settingAlarmFragment){
        this.alarm = settingAlarmFragment.getAlarm();
        this.listener = settingAlarmFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View viewDialog = inflater.inflate(R.layout.fragment_repeat_dialog, container);
        btnOK = viewDialog.findViewById(R.id.btnOK);
        btnCancel = viewDialog.findViewById(R.id.btnCancel);
        btnDays = viewDialog.findViewById(R.id.btnDays);
        btnWeekend = viewDialog.findViewById(R.id.btnWeekend);

        layoutRepeatDialog = viewDialog.findViewById(R.id.layoutRepeatDialog);
        animFadein = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        animFadein.setAnimationListener(this);
        layoutRepeatDialog.startAnimation(animFadein);

        checkBoxT2 = viewDialog.findViewById(R.id.checkBoxT2);
        checkBoxT3 = viewDialog.findViewById(R.id.checkBoxT3);
        checkBoxT4 = viewDialog.findViewById(R.id.checkBoxT4);
        checkBoxT5 = viewDialog.findViewById(R.id.checkBoxT5);
        checkBoxT6 = viewDialog.findViewById(R.id.checkBoxT6);
        checkBoxT7 = viewDialog.findViewById(R.id.checkBoxT7);
        checkBoxCN = viewDialog.findViewById(R.id.checkBoxCN);
        for(int i = 0; i < 7; i++){
            listDays.add(i, false);
        }
        if(alarm.getListRepeatDay().get(0)) {
            listDays.set(0, true);
            checkBoxT2.setChecked(true);
            checkDays();
        }
        if(alarm.getListRepeatDay().get(1)) {
            listDays.set(1, true);
            checkBoxT3.setChecked(true);
            checkDays();
        }
        if(alarm.getListRepeatDay().get(2)) {
            listDays.set(2, true);
            checkBoxT4.setChecked(true);
            checkDays();
        }
        if(alarm.getListRepeatDay().get(3)) {
            listDays.set(3, true);
            checkBoxT5.setChecked(true);
            checkDays();
        }
        if(alarm.getListRepeatDay().get(4)) {
            listDays.set(4, true);
            checkBoxT6.setChecked(true);
            checkDays();
        }
        if(alarm.getListRepeatDay().get(5)) {
            listDays.set(5, true);
            checkBoxT7.setChecked(true);
            checkDays();
        }
        if(alarm.getListRepeatDay().get(6)) {
            listDays.set(6, true);
            checkBoxCN.setChecked(true);
            checkDays();
        }




        checkBoxT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxT2.isChecked() == true) listDays.set(0, true);
                else listDays.set(0, false);
                checkDays();
            }
        });
        checkBoxT3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxT3.isChecked() == true) listDays.set(1, true);
                else listDays.set(1, false);
                checkDays();
            }
        });
        checkBoxT4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxT4.isChecked() == true) listDays.set(2, true);
                else listDays.set(2, false);
                checkDays();
            }
        });
        checkBoxT5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxT5.isChecked() == true) listDays.set(3, true);
                else listDays.set(3, false);
                checkDays();
            }
        });
        checkBoxT6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxT6.isChecked() == true) listDays.set(4, true);
                else listDays.set(4, false);
                checkDays();
            }
        });
        checkBoxT7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxT7.isChecked() == true) listDays.set(5, true);
                else listDays.set(5, false);
                checkDays();
            }
        });
        checkBoxCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxCN.isChecked() == true) listDays.set(6, true);
                else listDays.set(6, false);
                checkDays();
            }
        });

        btnDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnDays.getCurrentTextColor() == Color.WHITE) {
                    for(int i = 0; i < 5; i++){
                        listDays.set(i, false);
                    }
                    colorButtonOff(btnDays);
                }
                else {
                    for(int i = 0; i < 5; i++){
                        listDays.set(i, true);
                    }
                    colorButtonOn(btnDays);
                }
                if(checkBoxT2.isChecked()== true && checkBoxT3.isChecked()== true &&
                        checkBoxT4.isChecked()== true && checkBoxT5.isChecked()== true &&
                        checkBoxT6.isChecked()== true)
                {
                    checkBoxT2.setChecked(false);
                    checkBoxT3.setChecked(false);
                    checkBoxT4.setChecked(false);
                    checkBoxT5.setChecked(false);
                    checkBoxT6.setChecked(false);
                }
                else{
                    checkBoxT2.setChecked(true);
                    checkBoxT3.setChecked(true);
                    checkBoxT4.setChecked(true);
                    checkBoxT5.setChecked(true);
                    checkBoxT6.setChecked(true);
                }
            }
        });
        btnWeekend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnWeekend.getCurrentTextColor() == Color.WHITE) {
                    listDays.set(5, false);
                    listDays.set(6, false);
                    colorButtonOff(btnWeekend);
                }
                else {
                    listDays.set(5, true);
                    listDays.set(6, true);
                    colorButtonOn(btnWeekend);
                }
                if(checkBoxT7.isChecked()== true && checkBoxCN.isChecked()== true)
                {
                    checkBoxT7.setChecked(false);
                    checkBoxCN.setChecked(false);
                }
                else{
                    checkBoxT7.setChecked(true);
                    checkBoxCN.setChecked(true);
                }
            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFinishCheckDialog(listDays);
                getDialog().dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return viewDialog;
    }
    private void checkDays(){
        if(checkBoxT2.isChecked()== true && checkBoxT3.isChecked()== true &&
                checkBoxT4.isChecked()== true && checkBoxT5.isChecked()== true &&
                checkBoxT6.isChecked()== true){
            colorButtonOn(btnDays);
        }
        else {
            colorButtonOff(btnDays);
        }
        if(checkBoxT7.isChecked()== true && checkBoxCN.isChecked()== true) {
            colorButtonOn(btnWeekend);
        }
        else {
            colorButtonOff(btnWeekend);
        }
    }
    private void colorButtonOn(Button btnD){
        btnD.setBackgroundColor(getResources().getColor(R.color.colorBlue));
        btnD.setTextColor(Color.WHITE);
    }
    private void colorButtonOff(Button btnD){
        btnD.setBackgroundColor(getResources().getColor(R.color.backgroundlayout));
        btnD.setTextColor(getResources().getColor(R.color.colorBlue));
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //this.listener = (RepeatDialogListener) context;
    }
}
