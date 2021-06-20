package dao;
import models.Departments;
import models.Users;

import java.util.List;

public interface DepartmentsDao {

    //create
    void addDept(Departments department);

    //read

    List<Departments> getAllDept();
    Departments findById(int id);
    //update
    //delete
    void clearAllDept();

}
