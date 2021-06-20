package dao;

import models.Departments;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class sql2oDepartments implements DepartmentsDao {

    private sql2oDepartments DepartmentsDao;
    private final Sql2o sql2o;
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

}
