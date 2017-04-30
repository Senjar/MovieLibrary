package movielibpackage.com.androidmymovielib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by Nick on 4/30/2017.
 */

public class TopBottomDialogFragment extends DialogFragment {

    private GridView gridViewTop;
    private GridView gridViewBottom;
    ArrayList<Movie> moviesTop;
    ArrayList<Movie> moviesBottom;
    private Button OKButton;


    public TopBottomDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to movieAdd arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static TopBottomDialogFragment newInstance(ArrayList<Movie> moviesTop,ArrayList<Movie> moviesBottom) {
        TopBottomDialogFragment frag = new TopBottomDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("moviesTop",moviesTop);
        args.putSerializable("moviesBottom",moviesBottom);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top_bottom, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        moviesTop = (ArrayList<Movie>) getArguments().getSerializable("moviesTop");
        moviesBottom = (ArrayList<Movie>) getArguments().getSerializable("moviesBottom");

        gridViewTop = (GridView) view.findViewById(R.id.gridViewTop);
        gridViewBottom = (GridView) view.findViewById(R.id.gridViewBottom);

        gridViewTop.setAdapter(new CustomAdapter(view.getContext(),moviesTop));
        gridViewBottom.setAdapter(new CustomAdapter(view.getContext(), moviesBottom));

        gridViewTop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String title = moviesTop.get(position).getTitle();
                final int date = moviesTop.get(position).getReleaseDate();
                final float rating = moviesTop.get(position).getRating();
                ((MainActivity) getActivity()).showPreviewDialog(title,date,rating);
            }
        });

        gridViewBottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String title = moviesBottom.get(position).getTitle();
                final int date = moviesBottom.get(position).getReleaseDate();
                final float rating = moviesBottom.get(position).getRating();
                ((MainActivity) getActivity()).showPreviewDialog(title,date,rating);
            }
        });

        //OK button
        OKButton = (Button) view.findViewById(R.id.tbOkButton);
        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


    }

}