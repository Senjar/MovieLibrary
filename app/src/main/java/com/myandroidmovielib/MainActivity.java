package com.myandroidmovielib;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends ActionBarActivity {

    ArrayList<Movie> movies;
    CustomAdapter customAdapter;
    Sqlfunc db;
    GridView gridViewMain;
    FrameLayout frameLayoutMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridViewMain = (GridView) findViewById(R.id.gridViewMain);
        db = new Sqlfunc(this);
        movies = db.fetch();

        customAdapter = new CustomAdapter(MainActivity.this, movies);
        gridViewMain.setAdapter(customAdapter);

        frameLayoutMain = (FrameLayout)findViewById(R.id.frameLayoutMain);

        updateIntro();

        gridViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String title = movies.get(position).getTitle();
                final int date = movies.get(position).getReleaseDate();
                final float rating = movies.get(position).getRating();
                showPreviewDialog(title,date,rating);
            }
        });

        final Activity mContext = this;

        gridViewMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                final String title = movies.get(position).getTitle();
                final int date = movies.get(position).getReleaseDate();
                final float rating = movies.get(position).getRating();

                builder.setTitle(title)
                        .setItems(new String[] {getString(R.string.edit),getString(R.string.delete)}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (which == 0) showEditDialog(title,date,rating,position);
                        else {
                            Movie movie = (Movie) gridViewMain.getItemAtPosition(position);
                            db.delete(movie);
                            customAdapter.remove(movie);
                            updateIntro();
                        }
                    }
                });
                builder.create().show();//Show Edit/Delete Dialog
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                //addRandom();
                showEditDialog();
                return true;

            case R.id.action_favorites:
                ArrayList<Movie> moviesTop = db.fetchTop3();
                ArrayList<Movie> moviesBottom = db.fetchBottom3();

                showTopBottomDialog(moviesTop,moviesBottom);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }


    public void movieAdd(String title, int date, float rating){
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setReleaseDate(date);
        movie.setRating(rating);
        long movieID = db.insert(movie);
        movie.setId(movieID);
        if (movieID!=-1)customAdapter.add(movie);//Check if add was successful and then add to adapter
        else
        {
            Toast.makeText(MainActivity.this, R.string.movie_already_exists, Toast.LENGTH_SHORT).show();
            showEditDialog(); //TODO Check: possible bad practice to call from here
        }
        updateIntro();
    }

    public void movieUpdate(String title, int date, float rating,int pos) {
        Movie movie = customAdapter.getItem(pos);
        String tempTitle = movie.getTitle();
        int tempDate = movie.getReleaseDate();
        float tempRating = movie.getRating();
        movie.setTitle(title);
        movie.setReleaseDate(date);
        movie.setRating(rating);
        if (db.update(movie)!=-1) {
            //Movie doesn't exist -> can update
            customAdapter.update(movie);
            Toast.makeText(MainActivity.this, R.string.movie_updated, Toast.LENGTH_SHORT).show();
        }
        else
        {
            //Movie exists -> can't update -> reset
            movie.setTitle(tempTitle);
            movie.setReleaseDate(tempDate);
            movie.setRating(tempRating);
            Toast.makeText(MainActivity.this, R.string.movie_already_exists, Toast.LENGTH_SHORT).show();
        }
    }



    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditAddDialogFragment editAddDialogFragment = EditAddDialogFragment.newInstance(getString(R.string.new_movie));
        editAddDialogFragment.show(fm, "fragment_edit_name");
    }

    private void showEditDialog(String title,int date,float rating,int pos) {
        FragmentManager fm = getSupportFragmentManager();
        EditAddDialogFragment editAddDialogFragment = EditAddDialogFragment.newInstance(title,date,rating,pos);
        editAddDialogFragment.show(fm, "fragment_edit_name");
    }

    public void showPreviewDialog(String title,int date,float rating) {
        FragmentManager fm = getSupportFragmentManager();
        PreviewDialogFragment previewDialogFragment = PreviewDialogFragment.newInstance(title,date,rating);
        previewDialogFragment.show(fm, "fragment_preview");
    }

    private void showTopBottomDialog(ArrayList<Movie> moviesTop,ArrayList<Movie> moviesBottom) {
        FragmentManager fm = getSupportFragmentManager();
        TopBottomDialogFragment topBottomDialogFragment = TopBottomDialogFragment.newInstance(moviesTop,moviesBottom);
        topBottomDialogFragment.show(fm, "fragment_top_bottom");
    }

    private void updateIntro() { //Updates the "Add movies to appear here!" visibility.
        if (!movies.isEmpty()) frameLayoutMain.setVisibility(View.INVISIBLE);
        else frameLayoutMain.setVisibility(View.VISIBLE);
    }

}

