package inducesmile.com.androidgridview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    //GridView lvMovies;
    ArrayList<Movie> movies;
    //MovieAdapter movieAdapter;
    CustomAdapter customAdapter;
    Sqlfunc db;
    Random rnd; //TODO Remove

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        rnd = new Random();
        //final List<ItemObject> allItems =
        db = new Sqlfunc(this);
        movies = db.fetch();
        customAdapter = new CustomAdapter(MainActivity.this, movies);
        gridview.setAdapter(customAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                previewLayout();//TODO fragment
                //Toast.makeText(MainActivity.this, "Position: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        final Activity mContext = this;

        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                final String title = movies.get(position).getTitle();
                final int date = movies.get(position).getReleaseDate();
                final float rating = movies.get(position).getRating();

                builder.setTitle(title)
                        .setItems(new String[] {"Edit","Delete"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        if (which == 0) showEditDialog(title,date,rating*2,position); //TODO pass arguments
                        else {
                            Movie movie = (Movie) gridview.getItemAtPosition(position);
                            db.delete(movie);
                            customAdapter.remove(movie);
                        }
                    }
                });
                builder.create().show();
                ;





                //previewLayout();
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
                Toast.makeText(MainActivity.this, "Pressed Favorite", Toast.LENGTH_SHORT).show();
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
        customAdapter.add(movie);
    }

    public void movieUpdate(String title, int date, float rating,int pos) {
        //int pos = customAdapter.getPosition();
        //Toast.makeText(MainActivity.this, "Position: " + pos, Toast.LENGTH_SHORT).show();
        Movie movie = customAdapter.getItem(pos);
        movie.setTitle(title);
        movie.setReleaseDate(date);
        movie.setRating(rating);
        db.update(movie);
        customAdapter.update(movie);
    }

    private void previewLayout() { //TODO Replace with Fragment
        Intent intent = new Intent(this,ObjectPreview.class);
        startActivity(intent);
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditAddDialogFragment editAddDialogFragment = EditAddDialogFragment.newInstance("New Movie");
        editAddDialogFragment.show(fm, "fragment_edit_name");
    }

    private void showEditDialog(String title,int date,float score,int pos) {
        FragmentManager fm = getSupportFragmentManager();
        EditAddDialogFragment editAddDialogFragment = EditAddDialogFragment.newInstance(title,date,score,pos);
        editAddDialogFragment.show(fm, "fragment_edit_name");
    }





}

