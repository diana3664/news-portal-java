import dao.UsersDao;
import dao.sql2oDepartments;
import dao.sql2oUsers;
import models.Departments;
import models.Users;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class sql2oDepartmentsTest {

    private Connection conn;
    private sql2oUsers UsersDao;
    private sql2oDepartments DepartmentsDao;


    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "moringa", "123");
        DepartmentsDao = new sql2oDepartments(sql2o);
        UsersDao =new sql2oUsers(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }


    @Test
    public void Departments_instantiatesCorrectly() {
        Departments department = new Departments("hr", "recruiting");
        assertTrue(department instanceof Departments);
    }



    @Test
    public void getAllDept() {
        Departments department=setDepartment();

        Departments otherDepartment=new Departments("hr","recruiting");
        DepartmentsDao.addDept(department);
        DepartmentsDao.addDept(otherDepartment);
        Assert.assertEquals(department,DepartmentsDao.getAllDept().get(0));
        Assert.assertEquals(otherDepartment,DepartmentsDao.getAllDept().get(1));
    }


    @Test
    public void DepartmentFindById() {
        Departments department=setDepartment();
        Departments otherDepartment=new Departments("hr","recruiting");
        DepartmentsDao.addDept(department);
        DepartmentsDao.addDept(otherDepartment);
        Assert.assertEquals(department,DepartmentsDao.findById(department.getId()));
        Assert.assertEquals(otherDepartment,DepartmentsDao.findById(otherDepartment.getId()));

    }

//many to many relationship

    @Test
    public void DepertmenReturnsUserstypesCorrectly() throws Exception {
        Users user=new Users("dennis","hr","recruiting");
        UsersDao.addUser(user);

        Users otherUser= new Users("dennis","developer","Coding");
        UsersDao.addUser(otherUser);

        Departments departmentTest=setDepartment();
        DepartmentsDao.addDept(departmentTest);
        DepartmentsDao.addUserIntoDept(user,departmentTest);
        DepartmentsDao.addUserIntoDept(otherUser,departmentTest);

        Users[] userstype = {user, otherUser}; //oh hi what is this? Observe how we use its assertion below.

        assertEquals(Arrays.asList(userstype), DepartmentsDao.getAllUsersInDepartment(departmentTest.getId()));
    }


    //helpers
    private Departments setDepartment() {
        return new Departments("hr", "recruiting");
    }
}
