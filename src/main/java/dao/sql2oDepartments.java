package dao;

import org.sql2o.Sql2o;

public class sql2oDepartments implements DepartmentsDao {

    private sql2oDepartments DepartmentsDao;
    private final Sql2o sql2o;
    public sql2oDepartments(Sql2o sql2o) { this.sql2o = sql2o; }



}
