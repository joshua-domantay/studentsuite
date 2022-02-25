package comp380.hanyjoshallie.studentsuite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavView;
    private Menu navMenu;
    private Screen screenCurrent;
    private SubScreen subScreenClock;
    private SubScreen subScreenCalendar;
    private SubScreen subScreenTask;
    private SubScreen subScreenNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavView = findViewById(R.id.bottomNavView);
        navMenu = bottomNavView.getMenu();

        if(getSupportActionBar() != null) { this.getSupportActionBar().hide(); }    // Hide action bar above app

        bottomNavView.setBackground(null);  // Remove background

        // Setting screens
        subScreenClock = SubScreen.CL_ALARM;        // Default CLOCK subScreen is ALARM
        subScreenCalendar = SubScreen.CA_MONTHLY;   // Default CALENDAR subScreen is MONTHLY view
        subScreenNote = SubScreen.N_SELECT;         // Default NOTE subScreen is SELECT view
        setScreen(Screen.CLOCK);    // Default screen on launch would be CLOCK
    }

    private MenuItem addMenuItem(int _title, int _icon) {
        MenuItem _item = navMenu.add("");
        _item.setTitle(_title);
        _item.setIcon(_icon);
        return _item;
    }

    public void setScreen(Screen _newScreen) {      // TODO: Joshua - Set fragments + FrameLayout on xml
        if(_newScreen == screenCurrent) { return; }

        screenCurrent = _newScreen;
        switch(_newScreen) {
            case CLOCK:
                break;
            case CALENDAR:
                break;
            case TASK:
                break;
            default:    // NOTES
                break;
        }
        setMenu();
    }

    private void setMenu() {
        MenuItem _item;         // TODO: Joshua - Make each button do specific things (Change current screen to the another screen)
        navMenu.clear();

        switch(screenCurrent) {
            case CLOCK:
                _item = addMenuItem(R.string.alarm, R.drawable.ic_test);
                navMenu.add("");    // Placeholder
                _item = addMenuItem(R.string.timer, R.drawable.ic_test);
                break;
            case CALENDAR:
                _item = addMenuItem(R.string.monthly_view, R.drawable.ic_test);
                navMenu.add("");    // Placeholder
                _item = addMenuItem(R.string.list_view, R.drawable.ic_test);
                break;
            case TASK:
                break;
            default:    // NOTES
                break;
        }
    }
}