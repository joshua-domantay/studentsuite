package comp380.hanyjoshallie.studentsuite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

// Saving old navigation menu
public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavView;
    private Menu navMenu;
    private Screen screenCurrent;
    private SubScreen subScreenClock;
    private SubScreen subScreenCalendar;
    private SubScreen subScreenTask;
    private SubScreen subScreenNote;

    private FloatingActionButton[] fabMenus;
    private boolean showNavMenuOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigning variables
        bottomNavView = findViewById(R.id.bottomNavView);
        navMenu = bottomNavView.getMenu();
        fabMenus = new FloatingActionButton[4];

        if(getSupportActionBar() != null) { this.getSupportActionBar().hide(); }    // Hide action bar above app

        bottomNavView.setBackground(null);  // Remove background

        // Setting bottom navigation menu
        setNavButtons();
        showNavMenu(false);

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

    private void setMenu() {
        MenuItem _item;
        navMenu.clear();

        showNavMenu(false);
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

    public void setScreen(Screen _newScreen) {
        if(_newScreen == screenCurrent) { return; }

        Fragment _newFragment = null;
        screenCurrent = _newScreen;
        switch(_newScreen) {
            case CLOCK:
                _newFragment = new ClockFragment();
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
        setMenu();
    }

    private void setNavButtons() {
        findViewById(R.id.fab_home).setOnClickListener((View v) -> showNavMenu(!showNavMenuOn));    // Home menu button

        setNavButtonsH(R.id.fab_clock, Screen.CLOCK, 0);        // Clock menu button
        setNavButtonsH(R.id.fab_calendar, Screen.CALENDAR, 1);  // Calendar menu button
        setNavButtonsH(R.id.fab_task, Screen.TASK, 2);          // Task menu button
        setNavButtonsH(R.id.fab_note, Screen.NOTE, 3);          // Note menu button
    }

    private void setNavButtonsH(int _id, Screen _newScreen, int _index) {
        FloatingActionButton _fab = findViewById(_id);
        _fab.setOnClickListener((View v) -> setScreen(_newScreen));
        fabMenus[_index] = _fab;
    }

    private void showNavMenu(boolean _on) {
        showNavMenuOn = _on;
        for(FloatingActionButton _btn : fabMenus) { _btn.setVisibility(_on ? View.VISIBLE : View.INVISIBLE); }
    }
}