import static com.example.appbaothuc.appsetting.AppSettingFragment.HOUR_MODE_24;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbaothuc.alarmsetting.SettingAlarmFragment;
import com.example.appbaothuc.appsetting.AppSettingFragment;
import com.example.appbaothuc.models.Alarm;

import java.util.Collections;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {
    private Context context;
    private UpcomingAlarmFragment upcomingAlarmFragment;
    private List<Alarm> listAlarm;
    private SettingAlarmFragment settingAlarmFragment;
    private FragmentManager fragmentManager;

    public AlarmAdapter(Context context, UpcomingAlarmFragment upcomingAlarmFragment, List<Alarm> listAlarm) {
        this.context = context;
        this.upcomingAlarmFragment = upcomingAlarmFragment;
        this.listAlarm = listAlarm;
        this.settingAlarmFragment = new SettingAlarmFragment();
        this.fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
    }

    @Override
    public int getItemCount() {
        return this.listAlarm.size();
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View alarmView = layoutInflater.inflate(R.layout.item_alarm, viewGroup, false);
        return new AlarmViewHolder(alarmView);
    }

    @Override
    public void onBindViewHolder(@NonNull final AlarmViewHolder alarmViewHolder, int i) {
        final Alarm alarm = listAlarm.get(i);
        final ConstraintLayout constraintLayoutParent = alarmViewHolder.constraintLayoutParent;
        final SwitchCompat swcEnable = alarmViewHolder.swcEnable;
        TextView tvHour = alarmViewHolder.tvHour;
        TextView tvMinute = alarmViewHolder.tvMinute;
        TextView tvAMPM = alarmViewHolder.tvAMPM;
        TextView tvRepeatDay = alarmViewHolder.tvRepeatDay;
        ImageView btnAlarmType = alarmViewHolder.btnAlarmType;

        final Animation animItemAlarm=AnimationUtils.loadAnimation(context, R.anim.anim_item_alarm_press);

        swcEnable.setChecked(alarm.isEnable());

        int hour = alarm.getHour();
        if (AppSettingFragment.hourMode == HOUR_MODE_24) {
            tvAMPM.setText("");
            if (hour < 10) {
                tvHour.setText("0" + hour);
            } else {
                tvHour.setText(String.valueOf(hour));
            }
        } else {
            if (hour < 12) {
                tvAMPM.setText("AM");
                if (hour == 0) {
                    tvHour.setText("12");
                } else if (hour < 10) {
                    tvHour.setText("0" + hour);
                } else tvHour.setText("" + hour);
            } else {//hour >=12
                tvAMPM.setText("PM");
                if (hour == 12) tvHour.setText("" + hour);
                else {
                    if ((hour - 12) < 10) {
                        tvHour.setText("0" + (hour - 12));
                    } else tvHour.setText("" + (hour - 12));
                }
            }
        }

        if (alarm.getMinute() < 10) {
            tvMinute.setText("0" + alarm.getMinute());
        } else {
            tvMinute.setText("" + alarm.getMinute());
        }

        tvRepeatDay.setText(alarm.getDescribeRepeatDay());

        swcEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm.setEnable(swcEnable.isChecked());
                upcomingAlarmFragment.updateAlarmEnable(alarm);
                listAlarm.set(alarmViewHolder.getAdapterPosition(), alarm);
                Collections.sort(listAlarm);
                AlarmAdapter.this.notifyDataSetChanged();
            }
        });
        constraintLayoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingAlarmFragment.configure(upcomingAlarmFragment, alarm);
                fragmentManager.beginTransaction()
                        .add(R.id.full_screen_fragment_container, settingAlarmFragment)
                        .addToBackStack(null).commit();
            }
        });
        btnAlarmType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingAlarmFragment.configure(upcomingAlarmFragment, alarm);
                fragmentManager.beginTransaction().add(R.id.full_screen_fragment_container, settingAlarmFragment).addToBackStack(null).commit();
            }
        });
        switch (alarm.getChallengeType()) {
            case DEFAULT:
                btnAlarmType.setImageDrawable(context.getDrawable(R.drawable.ic_alarm_60dp));
                break;
            case MATH:
                btnAlarmType.setImageDrawable(context.getDrawable(R.drawable.ic_math_36));
                break;
            case SHAKE:
                btnAlarmType.setImageDrawable(context.getDrawable(R.drawable.icons8_shake_phone_60));
                break;
            case MOVING:
                btnAlarmType.setImageDrawable(context.getDrawable(R.drawable.ic_move_white_64px));
                break;
        }
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout constraintLayoutParent;
        private SwitchCompat swcEnable;
        private TextView tvHour;
        private TextView tvMinute;
        private TextView tvRepeatDay;
        private TextView tvAMPM;
        private ImageButton btnAlarmType;

        AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayoutParent = itemView.findViewById(R.id.constraintLayoutParent);
            swcEnable = itemView.findViewById(R.id.switchCompatEnable);
            tvHour = itemView.findViewById(R.id.textViewHour);
            tvMinute = itemView.findViewById(R.id.textViewMinute);
            tvRepeatDay = itemView.findViewById(R.id.textViewRepeatDay);
            btnAlarmType = itemView.findViewById(R.id.buttonAlarmType);
            tvAMPM = itemView.findViewById(R.id.textViewAMPM);
        }
    }
}