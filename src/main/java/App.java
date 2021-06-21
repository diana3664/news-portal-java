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
    }




}
