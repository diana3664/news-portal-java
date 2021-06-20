import dao.sql2oUsers;
import models.Users;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Sql2o;
import org.sql2o.Connection;

import static org.junit.Assert.*;

public class sql2oUsersTest {

    private Connection conn;
    private sql2oUsers UsersDao;


    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "moringa", "123");
        UsersDao = new sql2oUsers(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void users_instantiatesCorrectly(){
        Users user = new Users("John","Clerk","Sending and recieving messages");
        assertTrue(user instanceof Users);
    }


    @Test
    public void addingUsersSetsId() throws Exception {
        Users testUser = setNewUser();
        UsersDao.addUser(testUser);
        assertEquals(1, testUser.getId());

    }

    //getall
    @Test
    public void getAll() throws Exception {
        Users review1 = setNewUser();
        UsersDao.addUser(review1);
        Users review2 = setNewUser();
        UsersDao.addUser(review2);

        assertEquals(2, UsersDao.getAll().size());
    }





    //helpers
    private Users setNewUser() {
        return new Users("John","Clerk","Sending and recieving messages");
    }
}
