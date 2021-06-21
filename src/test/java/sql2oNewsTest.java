import dao.*;
import models.Departments;
import models.News;
import models.Users;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotSame;

public class sql2oNewsTest {
    private static Connection conn;
    private static sql2oNews NewsDao;
    private static sql2oDepartments DepartmentsDao;
    private static sql2oUsers UsersDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/newsportal_test"; //connect to postgres test database
        Sql2o sql2o = new Sql2o(connectionString, "moringa", "123");
        NewsDao = new sql2oNews(sql2o);
        DepartmentsDao = new sql2oDepartments(sql2o);
        UsersDao =new sql2oUsers(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        DepartmentsDao.clearAllDept();
        UsersDao.clearAllUsers();
        NewsDao.clearAllNews();
        System.out.println("clearing database");
    }

    @AfterClass
    public static void shutDown() {
        conn.close();
        System.out.println("connection closed");
    }


    @Test
    public void addNews() {
        Users users=setNewUser();
        UsersDao.addUser(users);
        Departments departments=setDepartment();
        DepartmentsDao.addDept(departments);
        News news=new News("payments","paying salary",departments.getId(),users.getId());
        NewsDao.addNews(news);
        assertNotSame(4, news.getId());
    }

    @Test
    public void getAll() {

        Departments departments=setDepartment();
        DepartmentsDao.addDept(departments);
        News news=new News("payments","paying salary",departments.getId());
        NewsDao.addNews(news);
        News news2=new News("payments","paying salary",departments.getId());
        NewsDao.addNews(news2);
        assertEquals(2, NewsDao.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectUser() throws Exception {
        Departments departments=setDepartment();
        DepartmentsDao.addDept(departments);
        News news=new News("payments","paying salary",departments.getId());
        NewsDao.addNews(news);
        Assert.assertEquals(news, NewsDao.findById(news.getId()));
    }



    //helpers
    private Departments setDepartment() {
        return new Departments("sale","marketing");
    }
    private Users setNewUser() {
        return new Users("John","Clerk","Sending and recieving messages");
    }

}
