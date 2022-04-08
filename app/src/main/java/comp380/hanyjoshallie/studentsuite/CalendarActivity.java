package comp380.hanyjoshallie.studentsuite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity implements CalendarFragment.FragmentAListener, NewEventFragment.FragmentBListener{
    // ----- To send data between fragments -----:
    private CalendarFragment fragmentA;
    private NewEventFragment fragmentB;
    // -------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // ----- To send data between fragments -----:
        fragmentA = new CalendarFragment();
        fragmentB = new NewEventFragment();

        // Place the two fragments in the two FrameLayouts:
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_a, fragmentA)
                .replace(R.id.container_b, fragmentB)
                .commit();
        // -------------------------------------------
    }

    // ----- To send data between fragments -----:
    @Override
    public void onInputASent(CharSequence input) {
        fragmentB.updateEditText(input);
    }

    @Override
    public void onInputBSent(CharSequence input) {
        fragmentA.updateEditText(input);
        //fragmentA.custom_method_that_updates_arraylist();
    }
    // -------------------------------------------
}