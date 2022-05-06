package comp380.hanyjoshallie.studentsuite;

import android.app.AlarmManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class AlarmFragment extends Fragment {
    private View v;
    private TimePicker timePicker;
    private LinearLayout linearLayout;
    private ArrayList<Alarm> alarmList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_alarm, container, false);
        alarmList = new ArrayList<>();

        timePicker = v.findViewById(R.id.alarmTimePicker);
        linearLayout = v.findViewById(R.id.alarmLinearLayout);
        setAddAlarmBtn();
        return v;
    }

    private void setAddAlarmBtn() {
        Button _addAlarmBtn = v.findViewById(R.id.addAlarmBtn);
        _addAlarmBtn.setOnClickListener(item -> {
            // Acquiring alarmID
            int _alarmID = Integer.MAX_VALUE;
            boolean found = false;
            while(!found) {
                found = true;
                for (int i = 0; i < alarmList.size(); i++) {
                    if(alarmList.get(i).getAlarmID() == _alarmID) {
                        found = false;
                        break;
                    }
                }
                if(!found) {
                    if(_alarmID != Integer.MIN_VALUE) {
                        _alarmID--;
                    } else { break; }
                }
            }

            if(found) {
                Alarm _newAlarm = new Alarm(timePicker.getHour(), timePicker.getMinute(), _alarmID);
                alarmList.add(_newAlarm);
                addAlarmToListView(_newAlarm);
            }
        });
    }

    private void addAlarmToListView(Alarm _alarm) {
        int _hour = 0, _period = R.string.am;
        if(_alarm.getHour() < 12) {
            _period = R.string.am;
            if(_alarm.getHour() <= 0) { _hour = 12; }
        } else {
            _period = R.string.pm;
            if(_alarm.getHour() > 12) { _hour = _alarm.getHour() - 12; }
        }

        String _timeString = "";
        _timeString += timeToStr(_hour) + ":";
        _timeString += timeToStr(_alarm.getMinute()) + " " + getResources().getString(_period);

        /*
        TextView _test = new TextView(getContext());
        _test.setText(_timeString);
        _test.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        _test.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        linearLayout.addView(_test);
        */

        LinearLayout.LayoutParams _matchWrap = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        LinearLayout.LayoutParams _wrapWrap = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        // LinearLayout parent
        LinearLayout _linearParent = new LinearLayout(getContext());
        _linearParent.setLayoutParams(_matchWrap);
        _linearParent.setOrientation(LinearLayout.VERTICAL);

        // RelativeLayout for time TextView, delete ImageButton, and on/off ToggleButton
        RelativeLayout _timeAndButtons = new RelativeLayout(getContext());
        _timeAndButtons.setLayoutParams(_matchWrap);

        // Time TextView
        TextView _timeTV = new TextView(getContext());
        RelativeLayout.LayoutParams _timeTVParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        _timeTVParams.addRule(RelativeLayout.CENTER_VERTICAL);
        _timeTVParams.setMargins(dpToPix(20), dpToPix(10), 0, dpToPix(10));
        _timeTV.setLayoutParams(_timeTVParams);
        _timeTV.setText(_timeString);
        _timeTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
        _timeTV.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        _timeAndButtons.addView(_timeTV);       // Add TextView to RelativeLayout

        // LinearLayout for delete and on/off buttons
        LinearLayout _delToggleButtons = new LinearLayout(getContext());
        RelativeLayout.LayoutParams _delToggleButtonsParams = new RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        _delToggleButtonsParams.addRule(RelativeLayout.CENTER_VERTICAL);
        _delToggleButtonsParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        _delToggleButtonsParams.setMargins(0, 0, dpToPix(15), 0);
        _delToggleButtons.setOrientation(LinearLayout.HORIZONTAL);
        _delToggleButtons.setLayoutParams(_delToggleButtonsParams);

        // Delete button
        ImageButton _delBtn = new ImageButton(getContext());
        LinearLayout.LayoutParams _wrapWrapDel = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        _wrapWrapDel.setMargins(0, 0, dpToPix(5), 0);
        _delBtn.setLayoutParams(_wrapWrapDel);
        _delBtn.setImageResource(R.drawable.ic_delete);
        _delBtn.setOnClickListener(item -> {        // Set deletion of alarm from list
            deleteAlarmFromList(_alarm.getAlarmID());

            _timeTV.setText(" " + _alarm.getAlarmID());
        });
        _delToggleButtons.addView(_delBtn);

        // On/Off alarm button
        Switch _onOffSwitch = new Switch(getContext());
        LinearLayout.LayoutParams _switchParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        _onOffSwitch.setLayoutParams(_switchParams);
        _delToggleButtons.addView(_onOffSwitch);

        _timeAndButtons.addView(_delToggleButtons);     // Add RelativeLayout to parent LinearLayout
        _linearParent.addView(_timeAndButtons);     // Add LinearLayout for buttons to parent LinearLayout

        linearLayout.addView(_linearParent);        // Add LinearLayout parent to View LinearLayout

        // Setting alarm
        _alarm.setLayout(_linearParent);
        _alarm.setLayoutSwitch(_onOffSwitch);
    }

    private void deleteAlarmFromList(int _alarmID) {
        for(int i = 0; i < alarmList.size(); i++) {
            if(alarmList.get(i).getAlarmID() == _alarmID) {
                // TODO: Check if alarm is active or not
                linearLayout.removeView(alarmList.get(i).getLayout());
                alarmList.remove(i);
                break;
            }
        }
    }

    private String timeToStr(int _x) {
        if(_x >= 10) { return ("" + _x); }
        return ("0" + _x);
    }

    private int dpToPix(int _dp) {
        int _pix = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                _dp,
                getResources().getDisplayMetrics()
        );
        return _pix;
    }
}
