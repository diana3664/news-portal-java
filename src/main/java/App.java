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
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
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


        // creating the landing page
        get("/",(request, response) ->{
            Map<String,Object> model = new HashMap<>();
            return new ModelAndView(model,"index.hbs");
        },new HandlebarsTemplateEngine());


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

        get("/user/:id/departments","application/json",(request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(UsersDao.getAllDepartmentsUsers(id).size()>0){
                return gson.toJson(UsersDao.getAllDepartmentsUsers(id));
            }
            else {
                return "{\"message\":\"but user Not in the departments.\"}";
            }
        });

        get("/department/:id/users","application/json",(request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(DepartmentsDao.getAllUsersInDepartment(id).size()>0){
                return gson.toJson(DepartmentsDao.getAllUsersInDepartment(id));
            }
            else {
                return "{\"message\":\"Department not available:\"}";
            }
        });

        post("/add/user/:user_id/department/:department_id","application/json",(request, response) -> {

            int user_id=Integer.parseInt(request.params("user_id"));
            int department_id=Integer.parseInt(request.params("department_id"));
            Departments departments=DepartmentsDao.findById(department_id);
            Users users=UsersDao.findById(user_id);
            if(departments==null){
                throw new ApiExceptions(404, String.format("No department with the id: \"%s\" exists",
                        request.params("department_id")));
            }
            if(users==null){
                throw new ApiExceptions(404, String.format("No user with the id: \"%s\" exists",
                        request.params("user_id")));
            }
            DepartmentsDao.addUserIntoDept(users,departments);

            List<Users> departmentUsers=DepartmentsDao.getAllUsersInDepartment(departments.getId());

            response.status(201);
            return gson.toJson(departmentUsers);
        });

        get("/department/:id","application/json",(request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(DepartmentsDao.findById(id)==null){
                throw new ApiExceptions(404, String.format("No department with the id: \"%s\" exists",
                        request.params("id")));
            }
            else {
                return gson.toJson(DepartmentsDao.findById(id));
            }
        });

        post("/department/:id","application/json",(request, response) -> {
            Departments departments =gson.fromJson(request.body(),Departments.class);
            DepartmentsDao.addDept(departments);
            response.status(201);
            return gson.toJson(departments);
        });

        get("/users/:id", "application/json", (request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(UsersDao.findById(id)==null){
                throw new ApiExceptions(404, String.format("User not available: \"%s\" exists",
                        request.params("id")));
            }
            else {
                return gson.toJson(UsersDao.findById(id));
            }
        });

        post("/users/:id","application/json",(request, response) -> {
            Users user=gson.fromJson(request.body(),Users.class);
            UsersDao.addUser(user);
            response.status(201);
            return gson.toJson(user);
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
