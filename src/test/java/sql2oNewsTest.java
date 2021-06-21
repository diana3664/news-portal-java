import dao.*;
import models.Departments;
import models.News;
import models.Users;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class sql2oNewsTest {
    private Connection conn;
    private sql2oNews NewsDao;
    private sql2oDepartments DepartmentsDao;
    private sql2oUsers UsersDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "moringa", "123");
        NewsDao = new sql2oNews(sql2o);
        DepartmentsDao = new sql2oDepartments(sql2o);
        UsersDao =new sql2oUsers(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }


    @Test
    public void addNews() {
        Users users=setNewUser();
        UsersDao.addUser(users);
        Departments departments=setDepartment();
        DepartmentsDao.addDept(departments);
        News news=new News("payments","paying salary",departments.getId(),users.getId());
        NewsDao.addNews(news);
        assertEquals(1, news.getId());
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
