package comp380.hanyjoshallie.studentsuite;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class NewEventFragment extends Fragment {
    // ----- To send data between fragments -----:

    public FragmentBListener listener;
    private EditText eventEditText;
    private Button submitEventButton;

    public interface FragmentBListener{
        void onInputBSent(CharSequence input);
    }
    // -------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_event, container, false);
        // ----- To send data between fragments --------------------:
        eventEditText = view.findViewById(R.id.eventEditText);
        submitEventButton = view.findViewById(R.id.submitEventButton);

        submitEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence input = eventEditText.getText();
                listener.onInputBSent(input);
            }
        });
        // ----------------------------------------------------------
        return view;
    }

    // ----- To send data between fragments -----:

    public void updateEditText(CharSequence newText){
        eventEditText.setText(newText);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Making sure our activity (context) implements
        // the interface FragmentAListener:
        if(context instanceof NewEventFragment.FragmentBListener){
            listener = (NewEventFragment.FragmentBListener) context;
        } else{
            throw new RuntimeException(context.toString()
                    + " must implement FragmentBListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
    // -------------------------------------------------------
}