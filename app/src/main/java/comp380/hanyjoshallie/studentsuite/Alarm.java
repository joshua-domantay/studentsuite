package comp380.hanyjoshallie.studentsuite;

import android.widget.LinearLayout;
import android.widget.Switch;

public class Alarm {
    private int hour, minute, alarmID;
    private LinearLayout layout;
    private Switch layoutSwitch;

    public Alarm(int _hour, int _minute, int _alarmID) {
        hour = _hour;
        minute = _minute;
        alarmID = _alarmID;
    }

    // public void switchOff() { layoutSwitch.setChecked(false); }
    // public boolean switchIsChecked() { return layoutSwitch.isChecked(); }

    // Getters and Setters
    public int getHour() { return hour; }
    public int getMinute() { return minute; }
    public int getAlarmID() { return alarmID; }
    public LinearLayout getLayout() { return layout; }
    public Switch getLayoutSwitch() { return layoutSwitch; }

    public void setAlarmID(int _id) { alarmID = _id; }
    public void setLayout(LinearLayout _layout) { layout = _layout; }
    public void setLayoutSwitch(Switch _switch) { layoutSwitch = _switch; }
}
