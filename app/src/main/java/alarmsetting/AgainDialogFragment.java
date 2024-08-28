package alarmsetting;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.appbaothuc.R;

public class AgainDialogFragment extends DialogFragment implements Animation.AnimationListener {
    private Button btnOK, btnCancel;
    private RadioGroup radioGroupAgain;
    private LinearLayout baseLayout;
    private Animation animFadein;

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public interface AgainDialogListener{
        void onFinishChoiceDialog(Integer input);
    }
    private AgainDialogListener listener;
    public void setListener(SettingAlarmFragment settingAlarmFragment){
        this.listener = settingAlarmFragment;
    }
//    public AgainDialogFragment() {
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View viewDialog = inflater.inflate(R.layout.fragment_again_dialog, container);
        btnOK = viewDialog.findViewById(R.id.btnOK);
        btnCancel = viewDialog.findViewById(R.id.btnCancel);
        radioGroupAgain = viewDialog.findViewById(R.id.radioGroupAgain);

        baseLayout = viewDialog.findViewById(R.id.baseLayout);
        animFadein = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        animFadein.setAnimationListener(this);
        baseLayout.startAnimation(animFadein);

        //getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroupAgain.getCheckedRadioButtonId(); //get text on RadioButton checked
                RadioButton radioButton = viewDialog.findViewById(selectedId);
                Integer index = radioGroupAgain.indexOfChild(radioButton), input;
                if(index == 0) input = 0;
                else if(index == 1) input = 1;
                else input = (index - 1) * 5;
                 listener.onFinishChoiceDialog(input);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //this.listener = (AgainDialogListener) context;
    }
}