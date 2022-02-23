package comp380.hanyjoshallie.studentsuite;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide action bar above app
        if(getSupportActionBar() != null) { this.getSupportActionBar().hide(); }

        BottomNavigationView bottomNavView = findViewById(R.id.bottomNavView);
        bottomNavView.setBackground(null);
    }
}