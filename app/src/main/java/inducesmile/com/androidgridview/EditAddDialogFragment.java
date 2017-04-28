package inducesmile.com.androidgridview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class EditAddDialogFragment extends DialogFragment {

    private EditText EditTextTitle;
    private RatingBar ratingBar;
    private TextView ratingText;
    private EditText EditTextDate;

    public EditAddDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditAddDialogFragment newInstance(String title,int date,float score) {
        EditAddDialogFragment frag = new EditAddDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("date",date);
        args.putFloat("score",score);
        frag.setArguments(args);
        return frag;
    }

    public static EditAddDialogFragment newInstance(String title) {
        EditAddDialogFragment frag = new EditAddDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_add, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString("title", "Enter Title");
        int date = getArguments().getInt("date",-1);
        float score = getArguments().getFloat("score",-1f);


        //Cancel button
        Button mCancelButton = (Button) view.findViewById(R.id.editCancelButton);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
        }
        });

        //Rating Bar rating number update
        EditTextTitle = (EditText) view.findViewById(R.id.editTextTitle);
        EditTextDate = (EditText) view.findViewById(R.id.editTextDate);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        ratingText=(TextView) view.findViewById(R.id.ratingText);

        if (date != -1) {
            EditTextTitle.setText(title);
            EditTextDate.setText(""+date);
            ratingBar.setRating(score/2);
            ratingText.setText(score+"/10");
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating*2 % 1 == 0) ratingText.setText(Math.round(rating*2)+"/10");
                    else ratingText.setText(rating*2+"/10");
            }
        });

        // Fetch arguments from bundle and set title

        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        EditTextTitle.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

}
