package inducesmile.com.androidgridview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by Nick on 4/29/2017.
 */

public class PreviewDialogFragment extends DialogFragment {

    private TextView TextImage;
    private TextView Title;
    private TextView Date;
    private RatingBar ratingBar;
    private TextView ratingText;
    private Button OKButton;


    public PreviewDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to movieAdd arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static PreviewDialogFragment newInstance(String title,int date,float score) {
        PreviewDialogFragment frag = new PreviewDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("date",date);
        args.putFloat("score",score);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preview, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString("title", "Enter Title");
        int date = getArguments().getInt("date", -1);
        float score = getArguments().getFloat("score", -1f);

        TextImage = (TextView) view.findViewById(R.id.previewTextImage);
        Title = (TextView) view.findViewById(R.id.previewTextTitle);
        Date = (TextView) view.findViewById(R.id.previewTextDate);
        ratingBar = (RatingBar) view.findViewById(R.id.previewRatingBar);
        ratingText=(TextView) view.findViewById(R.id.previewRatingText);

        //Set Fields
        TextImage.setText(title.substring(0, 1));
        Title.setText(title);
        Date.setText(""+date);
        ratingBar.setRating(score);
        ratingText.setText(score*2+"/10");
        //OK button
        OKButton = (Button) view.findViewById(R.id.previewOkButton);
        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


    }

}
