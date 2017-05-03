package movielibpackage.com.androidmymovielib;

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

    private EditText Title;
    private EditText Date;
    private RatingBar ratingBar;
    private TextView ratingText;
    private Button OkButton;
    private Button CancelButton;


    public EditAddDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to movieAdd arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditAddDialogFragment newInstance(String title,int date,float rating,int pos) {
        EditAddDialogFragment frag = new EditAddDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("date",date);
        args.putFloat("rating",rating);
        args.putInt("pos",pos);
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
        String title = getArguments().getString("title", getString(R.string.enter_title));
        int date = getArguments().getInt("date",-1);
        final int pos = getArguments().getInt("pos",-1);
        float rating = getArguments().getFloat("rating",-1f);
        boolean isNew = false;

        if (pos == -1) isNew = true;

        //Cancel button
        CancelButton = (Button) view.findViewById(R.id.editCancelButton);
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
        }
        });

        //Rating Bar rating number update
        Title = (EditText) view.findViewById(R.id.editTextTitle);
        Date = (EditText) view.findViewById(R.id.editTextDate);
        ratingBar = (RatingBar) view.findViewById(R.id.editRatingBar);
        ratingText=(TextView) view.findViewById(R.id.editRatingText);

        if (!isNew) {
            Title.setText(title);
            Date.setText(""+date);
            ratingBar.setRating(rating);
            if(rating*2 % 1 == 0) ratingText.setText(Math.round(rating*2)+"/10");
                else ratingText.setText(rating*2+"/10");
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
        Title.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        OkButton = (Button) view.findViewById(R.id.editOkButton);

        //OK button New
        if (isNew)
        OkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmpty(Title) && !isEmpty(Date)){// Check if fields are empty
                    ((MainActivity) getActivity()).movieAdd(Title.getText().toString(), Integer.parseInt(Date.getText().toString()),ratingBar.getRating());
                    dismiss();
                }
            }
        });

        //OK button Old/Edit
        if (!isNew)
        OkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmpty(Title) && !isEmpty(Date)) {// Check if fields are empty
                    ((MainActivity) getActivity()).movieUpdate(Title.getText().toString(), Integer.parseInt(Date.getText().toString()), ratingBar.getRating(), pos);
                    dismiss();
                }
            }
        });
    }


    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

}
