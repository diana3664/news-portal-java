package models;

public class News {

    private int id;
    private String news_type;
    private String title;
    private String description;
    private final String TYPE_OF_NEWS="Normal";


    public News(String title, String description) {
        this.title = title;
        this.description = description;
        this.news_type=TYPE_OF_NEWS;

    }

    //getters

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
