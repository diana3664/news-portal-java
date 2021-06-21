import dao.*;
import models.Departments;
import models.Users;
import org.junit.*;
import org.sql2o.Sql2o;
import org.sql2o.Connection;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;
import static org.junit.Assert.*;

public class sql2oUsersTest {

    private static Connection conn;
    private static sql2oUsers UsersDao;
    private static sql2oDepartments DepartmentsDao;


    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/newsportal_test"; //connect to postgres test database
        Sql2o sql2o = new Sql2o(connectionString, "moringa", "123");


        UsersDao = new sql2oUsers(sql2o);
        DepartmentsDao = new sql2oDepartments(sql2o);

        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        DepartmentsDao.clearAllDept();
        UsersDao.clearAllUsers();
        System.out.println("clearing database");
    }

    @AfterClass
    public static void shutDown() {
        conn.close();
        System.out.println("connection closed");
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
        assertNotSame(1, testUser.getId());

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


    //instances
    @Test
    public void UserInstances() {

        Users users=setNewUser();
        Users otherUser= new Users("dennis","hr","recruiting");
        UsersDao.addUser(users);
        UsersDao.addUser(otherUser);
        assertEquals(users.getName(),UsersDao.getAll().get(0).getName());
        assertEquals(otherUser.getName(),UsersDao.getAll().get(1).getName());
    }

    //find by id

    @Test
    public void findByIdReturnsCorrectUser() throws Exception {
        Users testUser = setNewUser();
        UsersDao.addUser(testUser);
        Users otherUser = setNewUser();
        Assert.assertEquals(testUser, UsersDao.findById(testUser.getId()));
    }

    @Test
    public void clearAll() throws Exception {
        Users review1 = setNewUser();
        UsersDao.addUser(review1);
        Users review2 = setNewUser();
        UsersDao.addUser(review2);
        UsersDao.clearAllUsers();
        Assert.assertEquals(0, UsersDao.getAll().size());
    }

    @Test
    public void addDeptToUserAddsTypeCorretly() throws Exception {

        Departments testdepartment=setDepartment();

        DepartmentsDao.addDept(testdepartment);

        Users user=new Users("dennis","hr","recruiting");

        UsersDao.addUser(user);

        UsersDao.addDeptToUser(user,testdepartment);

        assertEquals(1, UsersDao.getAllDepartmentsUsers(user.getId()).size());

    }




    private Departments setDepartment() {
        return new Departments("hr", "recruiting");
    }


    //helpers
    private Users setNewUser() {
        return new Users("John","Clerk","Sending and recieving messages");
    }
}
