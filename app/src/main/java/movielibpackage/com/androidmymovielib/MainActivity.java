package movielibpackage.com.androidmymovielib;

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
    Random rnd; //TODO Remove
    GridView gridViewMain;
    FrameLayout frameLayoutMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridViewMain = (GridView) findViewById(R.id.gridViewMain);
        rnd = new Random();
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
                //Toast.makeText(MainActivity.this, "Position: " + position, Toast.LENGTH_SHORT).show();
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
                        .setItems(new String[] {"Edit","Delete"}, new DialogInterface.OnClickListener() {
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
                builder.create().show();

                //Toast.makeText(MainActivity.this, "Position: " + position, Toast.LENGTH_SHORT).show();

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
                //Toast.makeText(MainActivity.this, "Pressed Favorite", Toast.LENGTH_SHORT).show();
                //TODO fetch TOP and BOTTOM
                ArrayList<Movie> moviesTop = new ArrayList<Movie>();
                ArrayList<Movie> moviesBottom = new ArrayList<Movie>();
                moviesTop.add(new Movie(1,"The Ring",2000,3.6f));
                moviesTop.add(new Movie(1,"Avatar",2009,4.7f));
                moviesBottom.add(new Movie(1,"Moonlight",2017,2.2f));
                showTopBottomDialog(moviesTop,moviesBottom);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }


    public void addRandom(){

            Movie movie = new Movie();

            NumberFormat formatter = new DecimalFormat("#0.0");

            movie.setTitle("Movie " + rnd.nextInt(100));
            movie.setRating(Float.parseFloat(formatter.format((rnd.nextFloat() * 10)/2)));
            movie.setReleaseDate(rnd.nextInt(67)+1950);
            long movieID = db.insert(movie);
            movie.setId(movieID);
            customAdapter.add(movie);
    }

    public void movieAdd(String title, int date, float rating){
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setReleaseDate(date);
        movie.setRating(rating);
        long movieID = db.insert(movie);
        movie.setId(movieID);
        if (movieID!=-1)customAdapter.add(movie);//Check if add was successful then add to adapter
        else
        {
            Toast.makeText(MainActivity.this, "Movie already exists", Toast.LENGTH_SHORT).show();
            showEditDialog(); //TODO Check: possible bad practice to call from here
        }
        updateIntro();
    }

    public void movieUpdate(String title, int date, float rating,int pos) {
        Movie movie = customAdapter.getItem(pos);
        movie.setTitle(title);
        movie.setReleaseDate(date);
        movie.setRating(rating);
        if (db.update(movie)!=-1) {

            customAdapter.update(movie);
            Toast.makeText(MainActivity.this, "Movie Updated", Toast.LENGTH_SHORT).show();
        }
        else
        {

            Toast.makeText(MainActivity.this, "Movie already exists", Toast.LENGTH_SHORT).show();

        }
    }



    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditAddDialogFragment editAddDialogFragment = EditAddDialogFragment.newInstance("New Movie");
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

    private void updateIntro() {
        if (!movies.isEmpty()) frameLayoutMain.setVisibility(View.INVISIBLE);
        else frameLayoutMain.setVisibility(View.VISIBLE);
    }

}

