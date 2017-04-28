package inducesmile.com.androidgridview;

/**
 * Created by jklaz on 12-Apr-17.
 */


public class Movie {

    private long id;
    private String title;
    private int releaseDate;
    private float rating;

    public void Movie(int id, String title, int releasedate, float rating) {
        this.id = id;
        this.title = title;
        this.releaseDate = releasedate;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseDate(){
        return releaseDate;
    }

    public void setReleaseDate(int releasedate){
        this.releaseDate =releasedate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
