import Exceptions.ApiExceptions;
import com.google.gson.Gson;
import dao.*;
import dao.sql2oNews;
import dao.sql2oDepartments;
import dao.sql2oUsers;
import models.*;
import models.News;
import models.Departments;
import models.Users;
import static spark.Spark.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {


         Connection conn;
         sql2oNews NewsDao;
         sql2oDepartments DepartmentsDao;
         sql2oUsers UsersDao;
        Gson gson = new Gson();

        String connectionString = "jdbc:h2:~/newsportal.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "moringa", "123");
        DepartmentsDao = new sql2oDepartments(sql2o);
        UsersDao =new sql2oUsers(sql2o);
        NewsDao =new sql2oNews(sql2o);

        conn = sql2o.open();


        post("/users/new","application/json",(request, response) -> {
            Users user=gson.fromJson(request.body(),Users.class);
            UsersDao.addUser(user);
            response.status(201);
            return gson.toJson(user);
        });

        get("/users", "application/json", (request, response) -> {

            if(DepartmentsDao.getAllDept().size() > 0){
                return gson.toJson(NewsDao.getAll());
            }
            else {
                return "{\"message\":\"User not registered.\"}";
            }
        });

        post("/departments/new","application/json",(request, response) -> {
            Departments departments =gson.fromJson(request.body(),Departments.class);
            DepartmentsDao.addDept(departments);
            response.status(201);
            return gson.toJson(departments);
        });


        get("/departments","application/json",(request, response) -> {
            if(DepartmentsDao.getAllDept().size()>0){
                return gson.toJson(DepartmentsDao.getAllDept());
            }
            else {
                return "{\"message\":\"departments Not currently listed in the database.\"}";
            }
        });


        post("/news/new/general","application/json",(request, response) -> {

            News news =gson.fromJson(request.body(),News.class);
            NewsDao.addNews(news);
            response.status(201);
            return gson.toJson(news);
        });


        get("/news/general","application/json",(request, response) -> {
            if(NewsDao.getAll().size()>0){
                return gson.toJson(NewsDao.getAll());
            }
            else {
                return "{\"message\":\"No news available.\"}";
            }
        });


        post("/news/new/department","application/json",(request, response) -> {
            News department_news =gson.fromJson(request.body(),News.class);
            Departments departments=DepartmentsDao.findById(department_news.getDeptid());
            Users users=UsersDao.findById(department_news.getUid());
            if(departments==null){
                throw new ApiExceptions(404, String.format("No department with the id: \"%s\" exists",
                        request.params("id")));
            }
            if(users==null){
                throw new ApiExceptions(404, String.format("No user with the id: \"%s\" exists",
                        request.params("id")));
            }
            NewsDao.addNews(department_news);
            response.status(201);
            return gson.toJson(department_news);
        });


        post("/news/new/general","application/json",(request, response) -> {

            News news =gson.fromJson(request.body(),News.class);
            NewsDao.addNews(news);
            response.status(201);
            return gson.toJson(news);
        });



        //FILTERS
        exception(ApiExceptions.class, (exception, request, response) -> {
            ApiExceptions err = exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            response.type("application/json");
            response.status(err.getStatusCode());
            response.body(gson.toJson(jsonMap));
        });
    }

}
