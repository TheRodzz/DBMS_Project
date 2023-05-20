public class Media {
    int tid;
    String title,date;

    public Media(int tid, String title, String date) {
        this.tid = tid;
        this.title = title;
        this.date = date;
    }

    public int getTid() {
        return this.tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}