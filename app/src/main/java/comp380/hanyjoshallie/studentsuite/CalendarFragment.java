// Has ListView, "New Event" Button, and CalendarView

package comp380.hanyjoshallie.studentsuite;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class CalendarFragment extends Fragment {

    // ----- To send data between fragments -----:
    private FragmentAListener listener;
    private EditText editTextCalendar;
    private Button buttonNewEvent;

    public interface FragmentAListener{
        void onInputASent(CharSequence input);
    }
    // -------------------------------------------

    // ----- To update ArrayList -----:
    public ArrayList<String> eventsArrayList;
    public ListView listViewCalendar;
    public ArrayAdapter<String> listViewAdapter;
    // -------------------------------

    // ----- To do stuff with CalendarView -----:
    public CalendarView calendarView;
    // -----------------------------------------

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        // ----- To use the ListView -----:
        //String[] eventsArray = {"Event One", "Event Two"};
        eventsArrayList = new ArrayList<String>();
        listViewCalendar = (ListView) view.findViewById(R.id.listViewCalendar);
        listViewAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.note_list_item, R.id.noteTextView, eventsArrayList);
        listViewCalendar.setAdapter(listViewAdapter);
        // -------------------------------

        // ----- To send data between fragments --------------------:
        editTextCalendar = view.findViewById(R.id.editTextCalendar);
        buttonNewEvent = view.findViewById(R.id.buttonNewEvent);

        buttonNewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence input = editTextCalendar.getText();
                listener.onInputASent(input);
            }
        });
        // ----------------------------------------------------------

        // ----- To do stuff with CalendarView -----:
        calendarView = view.findViewById(R.id.calendarView);
        // -----------------------------------------

        return view;
    }

    // ----- To send data between fragments -----:

    public void updateEditText(CharSequence newText){
        //editTextCalendar.setText(newText);
        // ----- To update ArrayList -----:
        eventsArrayList.add(newText.toString());
        listViewAdapter.notifyDataSetChanged();
        // -------------------------------
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Making sure our activity (context) implements
        // the interface FragmentAListener:
        if(context instanceof FragmentAListener){
            listener = (FragmentAListener) context;
        } else{
            throw new RuntimeException(context.toString()
            + " must implement FragmentAListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
    // -------------------------------------------------------
}
