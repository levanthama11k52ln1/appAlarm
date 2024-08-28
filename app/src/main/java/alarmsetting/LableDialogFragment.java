package alarmsetting;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.appbaothuc.R;
import com.example.appbaothuc.models.Alarm;

public class LableDialogFragment extends DialogFragment implements Animation.AnimationListener {
    private SettingAlarmFragment settingAlarmFragment;
    private Alarm alarm;
    private Button btnOk;
    private Button btnCancel;
    private EditText mEditText;
    private LinearLayout edit_name;
    private Animation animFadein;


    public void configure(SettingAlarmFragment settingAlarmFragment, Alarm alarm){
        this.settingAlarmFragment = settingAlarmFragment;
        this.alarm = alarm;
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

    public interface LabelDialogListener {
        void onFinishEditDialog(String inputText);
    }
    private LabelDialogListener listener;
    public void setListener(SettingAlarmFragment settingAlarmFragment){
        this.listener = settingAlarmFragment;
    }

    public LableDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_name, container);
        mEditText = view.findViewById(R.id.txt_your_name);
        mEditText.setText(alarm.getLabel());

        edit_name = view.findViewById(R.id.edit_name);
        animFadein = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        animFadein.setAnimationListener(this);
        edit_name.startAnimation(animFadein);

        btnOk = view.findViewById(R.id.btnOk);
        btnCancel = view.findViewById(R.id.btnCancel);

        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFinishEditDialog(mEditText.getText().toString());
                getDialog().dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //this.listener = (LabelDialogListener) context;
    }
}
