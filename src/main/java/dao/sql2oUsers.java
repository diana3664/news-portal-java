package dao;

import models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

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

}
