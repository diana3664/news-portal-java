package dao;

import models.Departments;
import models.Users;

import java.util.List;

public interface UsersDao {

    //create
    void  addUser(Users user);
    void addDeptToUser( Users user,Departments department);

    //read


    //update
   List<Users> getAll();
    List<Departments> getAllDepartmentsUsers(int user_id);
      Users findById(int id);

    //delete
      void clearAllUsers();
}
