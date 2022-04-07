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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class AlarmFragment extends Fragment {
    private TimePicker timePicker;
    private LinearLayout linearLayout;
    private AlarmManager manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View _v = inflater.inflate(R.layout.fragment_alarm, container, false);

        timePicker = _v.findViewById(R.id.alarmTimePicker);
        linearLayout = _v.findViewById(R.id.alarmLinearLayout);
        // manager = getSystemService(ALARM_SERVICE);
        setAddAlarmBtn(_v);
        return _v;
    }

    private void setAddAlarmBtn(View _v) {
        Button _addAlarmBtn = _v.findViewById(R.id.addAlarmBtn);
        _addAlarmBtn.setOnClickListener(item -> {
            addAlarmToList(timePicker.getHour(), timePicker.getMinute());
        });
    }

    private void addAlarmToList(int _hour, int _minute) {
        int _period = R.string.am;
        if(_hour < 12) {
            _period = R.string.am;
            if(_hour <= 0) { _hour = 12; }
        } else {
            _period = R.string.pm;
            if(_hour > 12) { _hour -= 12; }
        }

        String _timeString = "";
        _timeString += timeToStr(_hour) + ":";
        _timeString += timeToStr(_minute) + " " + getResources().getString(_period);

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

        LinearLayout _linearParent = new LinearLayout(getContext());
        _linearParent.setLayoutParams(_matchWrap);
        _linearParent.setOrientation(LinearLayout.VERTICAL);

        RelativeLayout _timeAndButtons = new RelativeLayout(getContext());
        _timeAndButtons.setLayoutParams(_matchWrap);

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
        _timeAndButtons.addView(_timeTV);

        LinearLayout _delToggleButtons = new LinearLayout(getContext());
        RelativeLayout.LayoutParams _delToggleButtonsParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        _delToggleButtonsParams.addRule(RelativeLayout.CENTER_VERTICAL);
        _delToggleButtonsParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        _delToggleButtonsParams.setMargins(0, 0, dpToPix(10), 0);
        _delToggleButtons.setLayoutParams(_delToggleButtonsParams);
        _timeAndButtons.addView(_delToggleButtons);

        ImageButton _delBtn = new ImageButton(getContext());
        _delBtn.setLayoutParams(_wrapWrap);
        _delBtn.setImageResource(R.drawable.ic_test);
        _timeAndButtons.addView(_delBtn);

        ToggleButton _onOffBtn = new ToggleButton(getContext());
        _onOffBtn.setLayoutParams(_wrapWrap);
        _timeAndButtons.addView(_onOffBtn);

        _linearParent.addView(_timeAndButtons);


        LinearLayout _weekButtons = new LinearLayout(getContext());
        _weekButtons.setLayoutParams(_matchWrap);
        _weekButtons.setOrientation(LinearLayout.HORIZONTAL);

        CheckBox _sun = createCheckBox(R.string.alarmSun, _weekButtons);
        // onClick

        CheckBox _mon = createCheckBox(R.string.alarmMon, _weekButtons);
        // onClick

        CheckBox _tue = createCheckBox(R.string.alarmTue, _weekButtons);
        // onClick

        CheckBox _wed = createCheckBox(R.string.alarmWed, _weekButtons);
        // onClick

        CheckBox _thur = createCheckBox(R.string.alarmThur, _weekButtons);
        // onClick

        CheckBox _fri = createCheckBox(R.string.alarmFri, _weekButtons);
        // onClick

        CheckBox _sat = createCheckBox(R.string.alarmSat, _weekButtons);
        // onClick

        _linearParent.addView(_weekButtons);

        linearLayout.addView(_linearParent);
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

    private CheckBox createCheckBox(int _str, LinearLayout _parent) {
        LinearLayout.LayoutParams _wrapWrap = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );

        CheckBox _cb = new CheckBox(getContext());
        _cb.setLayoutParams(_wrapWrap);
        _cb.setText(_str);
        _cb.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        _parent.addView(_cb);
        return _cb;
    }
}
