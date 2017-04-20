package inducesmile.com.androidgridview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.gridview);

        final List<ItemObject> allItems = getAllItemObject();
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, allItems);
        gridview.setAdapter(customAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                previewLayout();
                //Toast.makeText(MainActivity.this, "Position: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        final Activity mContext = this;

        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("<Movie Name>") //TODO movie actual name
                        .setItems(new String[] {"Edit","Delete"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) showEditDialog(); //TODO pass arguments
                        //TODO else delete
                    }
                });
                builder.create().show();






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
        //TODO search
        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<ItemObject> getAllItemObject(){
        ItemObject itemObject = null;
        List<ItemObject> items = new ArrayList<>();
        items.add(new ItemObject("Very very Long Movie Title 1", "five"));
        items.add(new ItemObject("Movie Title 2", "five"));
        items.add(new ItemObject("Movie Title 3", "five"));
        items.add(new ItemObject("Very very very very Long Movie Movie Title 451", "five"));
        items.add(new ItemObject("Movie Title 5", "five"));
        items.add(new ItemObject("Movie Title 6", "five"));
        items.add(new ItemObject("Movie Title 7", "five"));
        items.add(new ItemObject("Movie Title 8", "five"));
        items.add(new ItemObject("Movie Title 9", "five"));
        items.add(new ItemObject("Movie Title 10", "five"));
        items.add(new ItemObject("Movie Title 11", "five"));
        items.add(new ItemObject("Movie Title 12", "five"));
        items.add(new ItemObject("Movie Title 13", "five"));
        items.add(new ItemObject("Movie Title 14", "five"));
        items.add(new ItemObject("Movie Title 15", "five"));
        items.add(new ItemObject("Movie Title 16", "five"));
        items.add(new ItemObject("Movie Title 17", "five"));
        items.add(new ItemObject("Movie Title 18", "five"));
        items.add(new ItemObject("Movie Title 19", "five"));
        items.add(new ItemObject("Movie Title 20", "five"));
        items.add(new ItemObject("Movie Title 21", "five"));
        items.add(new ItemObject("Movie Title 22", "five"));
        items.add(new ItemObject("Movie Title 23", "five"));
        items.add(new ItemObject("Movie Title 24", "five"));
        return items;
    }

    private void previewLayout() {
        Intent intent = new Intent(this,ObjectPreview.class);
        startActivity(intent);
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditAddDialogFragment editAddDialogFragment = EditAddDialogFragment.newInstance("Movie Info");
        editAddDialogFragment.show(fm, "fragment_edit_name");
    }






}

