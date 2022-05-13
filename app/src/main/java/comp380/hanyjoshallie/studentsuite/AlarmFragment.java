package comp380.hanyjoshallie.studentsuite;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private TimePicker timePicker;
    private LinearLayout linearLayout;
    private ArrayList<Alarm> alarmList;
    private boolean loaded;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_alarm, container, false);
        settings = getContext().getSharedPreferences("comp380.hanyjoshallie.studentsuite.alarmPrefs", 0);
        editor = settings.edit();
        timePicker = v.findViewById(R.id.alarmTimePicker);
        linearLayout = v.findViewById(R.id.alarmLinearLayout);
        alarmList = new ArrayList<>();
        loaded = false;

        setAddAlarmBtn();
        loadAlarms();
        return v;
    }

    @Override
    public void onResume() {
        loadAlarms();
        super.onResume();
    }

    @Override
    public void onPause() {
        saveAlarms();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        saveAlarms();
        super.onDestroyView();
    }

    private void setAddAlarmBtn() {
        Button _addAlarmBtn = v.findViewById(R.id.addAlarmBtn);
        _addAlarmBtn.setOnClickListener(item -> {
            if(alarmList.size() < 5) {         // HARD LIMIT OF 5 FOR NOW
                Alarm _newAlarm = new Alarm(timePicker.getHour(), timePicker.getMinute(), -1);
                boolean _found = getAlarmID(_newAlarm);
                if(_found) {
                    alarmList.add(_newAlarm);
                    addAlarmToListView(_newAlarm);
                }
            }
            saveAlarms();
        });
    }

    private void saveAlarms() {
        for(int i = 0; i < 5; i++) {
            if(i < alarmList.size()) {
                editor.putInt("alarmNum" + i + "Hour", alarmList.get(i).getHour());
                editor.putInt("alarmNum" + i + "Min", alarmList.get(i).getMinute());
            } else {
                editor.putInt("alarmNum" + i + "Hour", -1);
                editor.putInt("alarmNum" + i + "Min", -1);
            }
        }
        editor.apply();
    }

    private void loadAlarms() {
        if(!loaded) {
            for (int i = 0; i < 5; i++) {
                int _hour = settings.getInt("alarmNum" + i + "Hour", -1);
                if (_hour == -1) {
                    break;
                } else {
                    int _min = settings.getInt("alarmNum" + i + "Min", -1);
                    Alarm _newAlarm = new Alarm(_hour, _min, -1);
                    getAlarmID(_newAlarm);
                    alarmList.add(_newAlarm);
                    addAlarmToListView(_newAlarm);
                }
            }
            loaded = true;
        }
    }

    private boolean getAlarmID(Alarm _alarm) {
        // Acquiring alarmID
        int _alarmID = Integer.MAX_VALUE;
        boolean _found = false;
        while(!_found) {
            _found = true;
            for(int i = 0; i < alarmList.size(); i++) {
                if(alarmList.get(i).getAlarmID() == _alarmID) {
                    _found = false;
                    break;
                }
            }
            if(!_found) {
                if(_alarmID != Integer.MIN_VALUE) {
                    _alarmID--;
                } else {
                    break;
                }
            }
        }
        _alarm.setAlarmID(_alarmID);
        return _found;
    }

    private void addAlarmToListView(Alarm _alarm) {
        int _hour = 0, _period = R.string.am;
        if(_alarm.getHour() < 12) {
            _period = R.string.am;
            _hour = _alarm.getHour();
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
        _delToggleButtonsParams.setMargins(0, 0, dpToPix(10), 0);
        _delToggleButtons.setOrientation(LinearLayout.HORIZONTAL);
        _delToggleButtons.setLayoutParams(_delToggleButtonsParams);

        // Delete button
        ImageButton _delBtn = new ImageButton(getContext());
        LinearLayout.LayoutParams _wrapWrapDel = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        _wrapWrapDel.setMargins(0, 0, dpToPix(10), 0);
        _delBtn.setLayoutParams(_wrapWrapDel);
        _delBtn.setImageResource(R.drawable.ic_delete);
        _delBtn.setOnClickListener(item -> {        // Set deletion of alarm from list
            deleteAlarmFromList(_alarm.getAlarmID());
        });
        _delToggleButtons.addView(_delBtn);

        // On/Off alarm button
        Switch _onOffSwitch = new Switch(getContext());
        LinearLayout.LayoutParams _switchParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        _onOffSwitch.setLayoutParams(_switchParams);
        _onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cancelAlarm();      // Cancel alarm regardless
                if(b) {
                    for(int i = 0; i < alarmList.size(); i++) {
                        alarmList.get(i).getLayoutSwitch().setChecked(false);
                    }
                    _alarm.getLayoutSwitch().setChecked(true);
                    setAlarm(_alarm);
                }
                saveAlarms();
            }
        });
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
                if(alarmList.get(i).getLayoutSwitch().isChecked()) { cancelAlarm(); }
                linearLayout.removeView(alarmList.get(i).getLayout());
                alarmList.remove(i);
                break;
            }
        }
    }

    private void cancelAlarm() {
        AlarmManager _alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent _intent = new Intent(getActivity(), AlarmReceiver.class);
        PendingIntent _pIntent = PendingIntent.getBroadcast(getActivity(), 1, _intent, 0);
        _alarmManager.cancel(_pIntent);
    }

    private void setAlarm(Alarm _alarm) {
        Calendar _c = Calendar.getInstance();
        _c.set(Calendar.HOUR_OF_DAY, _alarm.getHour());
        _c.set(Calendar.MINUTE, _alarm.getMinute());
        _c.set(Calendar.SECOND, 0);
        startAlarm(_c);
    }

    private void startAlarm(Calendar _c) {
        AlarmManager _alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent _intent = new Intent(getActivity(), AlarmReceiver.class);
        PendingIntent _pIntent = PendingIntent.getBroadcast(getActivity(), 1, _intent, 0);
        if (_c.before(Calendar.getInstance())) { _c.add(Calendar.DATE, 1); }
        _alarmManager.setExact(AlarmManager.RTC_WAKEUP, _c.getTimeInMillis(), _pIntent);
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
