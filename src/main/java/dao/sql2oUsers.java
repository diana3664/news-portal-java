package dao;

import models.Departments;
import models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class sql2oUsers implements UsersDao {

    private sql2oUsers UsersDao;
    private final Sql2o sql2o;
    public sql2oUsers(Sql2o sql2o) { this.sql2o = sql2o; }



    @Override
    public void addUser(Users user) {
        String sql ="INSERT INTO staff (name,position,role) VALUES (:name,:position,:role)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(user)
                    .executeUpdate()
                    .getKey();
            user.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Users> getAll() {
        try (Connection con=sql2o.open()){
            String sql=("SELECT * FROM staff");
            return con.createQuery(sql)
                    .executeAndFetch(Users.class);
        }
    }

    @Override
    public Users findById(int id) {
        try (Connection con=sql2o.open()){
            String sql=("SELECT * FROM staff WHERE id=:id");
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Users.class);
        }
    }

    @Override
    public void clearAllUsers() {
        String sql = "DELETE from staff";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void addDeptToUser( Users user,Departments department){
        //do stuff here.
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


    @Override
    public List<Departments> getAllDepartmentsUsers(int user_id) {
        List<Departments> departments = new ArrayList<>();
        try (Connection con = sql2o.open()) {
            String sql = "SELECT department_id FROM users_departments WHERE user_id=:user_id";
            List<Integer> departmentIds = con.createQuery(sql)
                    .addParameter("user_id", user_id)
                    .executeAndFetch(Integer.class);

            for (Integer id : departmentIds) {
                String userResults = "SELECT * FROM departments WHERE id=:id";
                departments.add(con.createQuery(userResults)
                        .addParameter("id", id)
                        .executeAndFetchFirst(Departments.class));

            }

            return departments;
        }


    }

}
