package models;

public class News {

    private int id;
    private String news_type;
    private int department_id;
    private int user_id;
    private String title;
    private String description;
    private final String TYPE_OF_NEWS="Normal";


    public News(String title, String description,int user_id) {
        this.title = title;
        this.description = description;
        this.user_id=user_id;
        this.news_type=TYPE_OF_NEWS;
        this.department_id=0;
    }

    public News(String title, String description,int department_id, int user_id){
        this.title = title;
        this.description = description;
        this.user_id=user_id;
        this.department_id = department_id;
        this.news_type="department";
    }
    //getters


    public int getDeptid() {
        return department_id;
    }

    public int getUid() {
        return user_id;
    }

    public int getId() {
        return id;
    }

    public String getNews_type() {
        return news_type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTYPE_OF_NEWS() {
        return TYPE_OF_NEWS;
    }

    //setters

    public void setId(int id) {
        this.id = id;
    }

    //override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;

        News news = (News) o;

        if (getId() != news.getId()) return false;
        if (!getNews_type().equals(news.getNews_type())) return false;
        if (!getTitle().equals(news.getTitle())) return false;
        if (!getDescription().equals(news.getDescription())) return false;
        return getTYPE_OF_NEWS().equals(news.getTYPE_OF_NEWS());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getNews_type().hashCode();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getTYPE_OF_NEWS().hashCode();
        return result;
    }


}
