package comp380.hanyjoshallie.studentsuite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavView;
    private Menu navMenu;
    private Screen screenCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigning variables
        bottomNavView = findViewById(R.id.bottomNavView);
        navMenu = bottomNavView.getMenu();

        if(getSupportActionBar() != null) { this.getSupportActionBar().hide(); }    // Hide action bar above app

        //bottomNavView.setBackground(null);  // Remove background of bottom navigation view

        setScreen(Screen.ALARM);    // Set default screen on launch would be CLOCK
        setNavMenuOnClick();
    }

    public void setScreen(Screen _newScreen) {
        if(_newScreen == screenCurrent) { return; }

        Fragment _newFragment = null;
        screenCurrent = _newScreen;
        switch(_newScreen) {
            case ALARM:
                _newFragment = new AlarmFragment();
                break;
            case CALENDAR:
                _newFragment = new CalendarFragment();
                break;
            case TASK:
                _newFragment = new TaskFragment();
                break;
            default:    // NOTES
                _newFragment = new NoteFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, _newFragment).commit();
    }

    public void setNavMenuOnClick() {
        bottomNavView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuAlarm:
                    setScreen(Screen.ALARM);
                    return true;
                case R.id.menuCalendar:
                    setScreen(Screen.CALENDAR);
                    return true;
                case R.id.menuTask:
                    setScreen(Screen.TASK);
                    return true;
                default:
                    setScreen(Screen.NOTE);
                    return true;
            }
        });
    }
}