package comp380.hanyjoshallie.studentsuite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        MenuItem _item;         // TODO: Joshua - Make each button do specific things (Change current screen to the another screen)
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

    public void setScreen(Screen _newScreen) {      // TODO: Joshua - Set fragments + FrameLayout on xml
        if(_newScreen == screenCurrent) { return; }

        TextView _txt = findViewById(R.id.testTextView);    // _TEST_

        screenCurrent = _newScreen;
        switch(_newScreen) {
            case CLOCK:
                _txt.setText("CLOCK SCREEN");       // _TEST_
                break;
            case CALENDAR:
                _txt.setText("CALENDAR SCREEN");    // _TEST_
                break;
            case TASK:
                _txt.setText("TASK SCREEN");        // _TEST_
                break;
            default:    // NOTES
                _txt.setText("NOTES SCREEN");       // _TEST_
                break;
        }
        setMenu();
    }

    private void setNavButtons() {
        FloatingActionButton _fab;

        // Home menu button
        _fab = findViewById(R.id.fab_home);
        _fab.setOnClickListener((View v) -> showNavMenu(!showNavMenuOn));

        // Clock menu button
        _fab = findViewById(R.id.fab_clock);
        _fab.setOnClickListener((View v) -> setScreen(Screen.CLOCK));
        fabMenus[0] = _fab;

        // Calendar menu button
        _fab = findViewById(R.id.fab_calendar);
        _fab.setOnClickListener((View v) -> setScreen(Screen.CALENDAR));
        fabMenus[1] = _fab;

        // Task menu button
        _fab = findViewById(R.id.fab_task);
        _fab.setOnClickListener((View v) -> setScreen(Screen.TASK));
        fabMenus[2] = _fab;

        // Note menu button
        _fab = findViewById(R.id.fab_note);
        _fab.setOnClickListener((View v) -> setScreen(Screen.NOTE));
        fabMenus[3] = _fab;
    }

    private void showNavMenu(boolean _on) {
        showNavMenuOn = _on;
        for(FloatingActionButton _btn : fabMenus) { _btn.setVisibility(_on ? View.VISIBLE : View.INVISIBLE); }
    }
}