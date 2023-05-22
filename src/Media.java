public class Media {
    int tid, duration;
    String title, poster_url, trailer_url, release_date;

    /**
     * Constructor for the Media class.
     *
     * @param tid          The ID of the media.
     * @param duration     The duration of the media.
     * @param title        The title of the media.
     * @param release_date The release date of the media.
     * @param poster_url   The URL of the media's poster.
     * @param trailer_url  The URL of the media's trailer.
     */
    public Media(int tid, int duration, String title, String release_date, String poster_url, String trailer_url) {
        this.tid = tid;
        this.duration = duration;
        this.title = title;
        this.release_date = release_date;
        this.poster_url = poster_url;
        this.trailer_url = trailer_url;
    }

    /**
     * Returns a string representation of the Media object.
     *
     * @return A string representation of the Media object.
     */
    @Override
    public String toString() {
        return "tid= " + getTid() +
                ", duration= " + getDuration() +
                ", title= " + getTitle() +
                ", poster_url= " + getPoster_url() +
                ", trailer_url= " + getTrailer_url() +
                ", release_date= " + getRelease_date();
    }

    /**
     * Returns the ID of the media.
     *
     * @return The ID of the media.
     */
    public int getTid() {
        return this.tid;
    }

    /**
     * Sets the ID of the media.
     *
     * @param tid The ID of the media.
     */
    public void setTid(int tid) {
        this.tid = tid;
    }

    /**
     * Returns the duration of the media.
     *
     * @return The duration of the media.
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Sets the duration of the media.
     *
     * @param duration The duration of the media.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Returns the title of the media.
     *
     * @return The title of the media.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the title of the media.
     *
     * @param title The title of the media.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the release date of the media.
     *
     * @return The release date of the media.
     */
    public String getRelease_date() {
        return this.release_date;
    }

    /**
     * Sets the release date of the media.
     *
     * @param release_date The release date of the media.
     */
    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    /**
     * Returns the URL of the media's poster.
     *
     * @return The URL of the media's poster.
     */
    public String getPoster_url() {
        return this.poster_url;
    }

    /**
     * Sets the URL of the media's poster.
     *
     * @param poster_url The URL of the media's poster.
     */
    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    /**
     * Returns the URL of the media's trailer.
     *
     * @return The URL of the media's trailer.
     */
    public String getTrailer_url() {
        return this.trailer_url;
    }

    /**
     * Sets the URL of the media's trailer.
     *
     * @param trailer_url The URL of the media's trailer.
     */
    public void setTrailer_url(String trailer_url) {
        this.trailer_url = trailer_url;
    }

}
