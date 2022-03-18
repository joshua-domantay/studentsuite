package comp380.hanyjoshallie.studentsuite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AlarmFragment extends Fragment {
    private TimePicker timePicker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View _v = inflater.inflate(R.layout.fragment_alarm, container, false);
        timePicker = _v.findViewById(R.id.alarmTimePicker);
        setAddAlarmBtn(_v);
        return _v;
    }

    private void setAddAlarmBtn(View _v) {
        Button _addAlarmBtn = _v.findViewById(R.id.addAlarmBtn);
        _addAlarmBtn.setOnClickListener(item -> {
            ((TextView) _v.findViewById(R.id.alarmTest)).setText(timePicker.getHour() + " : " + timePicker.getMinute());
        });
    }
}
