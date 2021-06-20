package dao;

import models.News;

import java.util.List;

public interface NewsDao {

    //create

    void addNews(News news);

    //read
    List<News> getAll();

    News findById(int id);

    //update

    //delete

    void clearAllNews();
}
