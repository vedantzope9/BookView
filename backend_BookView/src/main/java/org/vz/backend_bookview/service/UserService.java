package org.vz.backend_bookview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vz.backend_bookview.model.Users;
import org.vz.backend_bookview.repo.UserRepo;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    public ResponseEntity<String> addUser(Users user) {
        try{
            userRepo.save(user);
            return ResponseEntity.ok("User Added!");
        }
        catch (Exception e){
            return new ResponseEntity<>("Failed to add User!" + e.getMessage() , HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public ResponseEntity<List<Users>> getAllUsers() {
        try{
            List<Users> list=userRepo.findAll();
            return ResponseEntity.ok(list);
        }
        catch (Exception e){
            return new ResponseEntity("Failed to fetch!"+e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Users> getUsersById(int id) {
        try{
            Optional<Users> users=userRepo.findById(id);
            if(users.isPresent())
                return ResponseEntity.ok(users.get());
            else
                return new ResponseEntity("User not found!" , HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity("Failed to fetch!"+e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity updateUsers(int id, Users user) {

        try{
            return userRepo.findById(id).map(existingUser -> {
                if(!user.getRole().equals(existingUser.getRole())){
                    return new ResponseEntity("Can't change Role!" , HttpStatus.NOT_ACCEPTABLE);
                }

                existingUser.setEmail(user.getEmail());
                existingUser.setPassword(user.getPassword());
                existingUser.setUsername(user.getUsername());

                userRepo.save(existingUser);

                return ResponseEntity.ok("User Updated!");
            }).orElse(new ResponseEntity<>("user not found!", HttpStatus.NOT_FOUND));
        }
        catch (Exception e){
            return new ResponseEntity("Failed to update!"+e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> deleteUsers(int id) {
        try {
            if(userRepo.findById(id).isPresent()){
                userRepo.deleteById(id);
                return ResponseEntity.ok("User Deleted!");
            }
            else
                return new ResponseEntity("User not present!" , HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity("Failed to delete!"+e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }
}
