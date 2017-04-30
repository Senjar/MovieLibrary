package movielibpackage.com.androidmymovielib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private LayoutInflater layoutinflater;
    private List<Movie> movies;


    private class ViewHolder{
        TextView tvImage;
        TextView tvTitle;
        TextView tvDate;
        RatingBar rbRating;
    }


    public CustomAdapter(Context context, List<Movie> movies) {
        super(context, R.layout.gridview_item, movies);
        this.context = context;
        layoutinflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.movies = movies;
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return movies.get(position).getId();
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            convertView = layoutinflater.inflate(R.layout.gridview_item, parent, false);
            holder = new ViewHolder();

            holder.tvImage = (TextView)convertView.findViewById(R.id.tvImage);
            holder.tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
            holder.tvDate = (TextView)convertView.findViewById(R.id.tvDate);
            holder.rbRating = (RatingBar)convertView.findViewById(R.id.rbRatingIndicator);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        Movie movie = (Movie) getItem(position);
        holder.tvImage.setText(movie.getTitle().substring(0, 1));//Keep first letter of Title
        holder.tvTitle.setText(movie.getTitle());
        holder.tvDate.setText(String.valueOf(movie.getReleaseDate()));
        holder.tvDate.setText(String.valueOf(movie.getReleaseDate()));
        holder.rbRating.setRating(movie.getRating());
        return convertView;

    }
    @Override
    public void add(Movie movie) {
        super.add(movie);
        //movies.movieAdd(movie);
        notifyDataSetChanged();
    }

    public void update(Movie movie) {
        int pos = movies.indexOf(movie);
        movies.set(pos, movie);
        notifyDataSetChanged();
    }

    @Override
    public void remove(Movie movie) {
        super.remove(movie);
        //movies.remove(movie);
        notifyDataSetChanged();
    }

}
