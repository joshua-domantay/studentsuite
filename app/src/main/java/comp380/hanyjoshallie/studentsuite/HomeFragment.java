package comp380.hanyjoshallie.studentsuite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    private MainActivity main;

    public HomeFragment(MainActivity _main) {
        main = _main;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View _v = inflater.inflate(R.layout.fragment_home, container, false);
        _v.findViewById(R.id.homeAlarmBtn).setOnClickListener(item -> {
            main.setScreen(Screen.ALARM);
            main.setNavMenuSelected(R.id.menuAlarm);
        });
        _v.findViewById(R.id.homeCalendarBtn).setOnClickListener(item -> {
            main.setScreen(Screen.CALENDAR);
            main.setNavMenuSelected(R.id.menuCalendar);
        });
        _v.findViewById(R.id.homeTaskBtn).setOnClickListener(item -> {
            main.setScreen(Screen.TASK);
            main.setNavMenuSelected(R.id.menuTask);
        });
        _v.findViewById(R.id.homeNoteBtn).setOnClickListener(item -> {
            main.setScreen(Screen.NOTE);
            main.setNavMenuSelected(R.id.menuNote);
        });
        return _v;
    }
}
