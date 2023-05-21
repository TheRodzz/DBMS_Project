public class Media {
    int tid, duration;
    String title, poster_url, trailer_url, release_date;

    public Media(int tid, int duration, String title, String release_date, String poster_url, String trailer_url) {
        this.tid = tid;
        this.duration = duration;
        this.title = title;
        this.release_date = release_date;
        this.poster_url = poster_url;
        this.trailer_url = trailer_url;
    }

    @Override
    public String toString() {
        return "tid= " + getTid() +
                ", duration= " + getDuration() +
                ", title= " + getTitle() +
                ", poster_url= " + getPoster_url() +
                ", trailer_url= " + getTrailer_url() +
                ", release_date= " + getRelease_date();
    }

    public int getTid() {
        return this.tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return this.release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_url() {
        return this.poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public String getTrailer_url() {
        return this.trailer_url;
    }

    public void setTrailer_url(String trailer_url) {
        this.trailer_url = trailer_url;
    }

}