package dao;

import models.Users;

import java.util.List;

public interface UsersDao {

    //create
    void  addUser(Users user);

    //read


    //update
   List<Users> getAll();
    // List<Departments> getAllUserDepartments(int user_id);
      Users findById(int id);

    //delete
    //   void clearAllUsers();
}
