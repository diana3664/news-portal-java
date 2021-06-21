package dao;

import models.Departments;
import models.News;
import models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class sql2oDepartments implements DepartmentsDao {

    private sql2oDepartments DepartmentsDao;
    private final Sql2o sql2o;
    private sql2oUsers UsersDao;


    public sql2oDepartments(Sql2o sql2o) { this.sql2o = sql2o; }

    @Override
    public void addDept(Departments department) {
        try(Connection con=sql2o.open()) {
            String sql="INSERT INTO departments (name,description,size) VALUES (:name,:description,:size)";
            int id=(int) con.createQuery(sql,true)
                    .bind(department)
                    .executeUpdate()
                    .getKey();
            department.setId(id);

        }catch (Sql2oException e){
            System.out.println(e);
        }

    }

    @Override
    public List<Departments> getAllDept() {
        try (Connection con=sql2o.open()){
            String sql= "SELECT * FROM departments";
            return con.createQuery(sql)
                    .executeAndFetch(Departments.class);

        }
    }

    @Override
    public Departments findById(int id) {
        try (Connection con=sql2o.open()){
            String sql= "SELECT * FROM departments WHERE id=:id";
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Departments.class);

        }
    }

    @Override
    public void clearAllDept() {
        try (Connection con=sql2o.open()){
            String sql="DELETE FROM departments";
            String sqlUsersDepartments="DELETE FROM users_departments";
            con.createQuery(sql).executeUpdate();
            con.createQuery(sqlUsersDepartments).executeUpdate();

        }catch (Sql2oException e){
            System.out.println(e);
        }
    }

    //many to many between user and depertment
    @Override
    public void addUserIntoDept(Users user, Departments department) {

        try(Connection con=sql2o.open()) {
            String sql="INSERT INTO users_departments (user_id,department_id) VALUES (:user_id,:department_id)";
            con.createQuery(sql)
                    .addParameter("user_id",user.getId())
                    .addParameter("department_id",department.getId())
                    .executeUpdate();
        }catch (Sql2oException e){
            System.out.println(e);
        }

    }

    //many to many between user and depertment

    @Override
    public List<Users> getAllUsersInDepartment(int department_id) {

        List<Users> users=new ArrayList<>();
        try (Connection con=sql2o.open()){
            String sql= "SELECT user_id FROM users_departments WHERE department_id=:department_id";
            List<Integer> userIds=con.createQuery(sql)
                    .addParameter("department_id",department_id)
                    .executeAndFetch(Integer.class);

            for(Integer id : userIds){
                String userResults="SELECT * FROM staff WHERE id=:id";
                users.add(con.createQuery(userResults)
                        .addParameter("id",id)
                        .executeAndFetchFirst(Users.class));

            }

            return users;
        }

    }

//    @Override
//    public List<News> getDepartmentNews(int id) {
//        try(Connection con=sql2o.open()) {
//            String sql="SELECT * FROM news WHERE id=:id ";
//            return con.createQuery(sql)
//                    .addParameter("id",id)
//                    .executeAndFetch(News.class);
//        }
//
//    }


}
