package org.vz.backend_bookview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.vz.backend_bookview.model.Role;
import org.vz.backend_bookview.model.Users;
import org.vz.backend_bookview.repo.UserRepo;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;


@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Value("${ADMIN_SECRET}")
    private String ADMIN_SECRET;

    BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public ResponseEntity<Users> addUser(Users user) {
        try{
            if (user.getRole() == Role.ADMIN) {
                if (user.getAdminPassword() == null || !user.getAdminPassword().equals(ADMIN_SECRET)) {
                    return new ResponseEntity("Enter correct Admin Password!" , HttpStatus.FORBIDDEN);
                }
            }

            if (userRepo.existsByUsername(user.getUsername())) {
                throw new Exception("Username already exists!");
            }
            if (userRepo.existsByEmail(user.getEmail())) {
                throw new Exception("Email already exists!");
            }

            user.setPassword(encoder.encode(user.getPassword()));
            userRepo.save(user);
            return new ResponseEntity(user,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity("Failed to add User!" + e.getMessage() , HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Users>> getAllUsers() {
        try{
            List<Users> list=userRepo.findAll();
            return ResponseEntity.ok(list);
        }
        catch (Exception e){
            return new ResponseEntity("Failed to fetch!"+e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('USER')")
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

    @PreAuthorize("hasRole('ADMIN')")
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

    public ResponseEntity<Map<String, String>> verify(Users user) {
        Users loggedInUser=userRepo.getUsersByEmail(user.getEmail());

        Authentication auth =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loggedInUser.getUsername(),user.getPassword()));

        String result=null;
        if(auth.isAuthenticated())
            result= jwtService.generateToken(loggedInUser.getUsername());
        else
            result="Fail";

        if ("Fail".equals(result)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
        }


        Map<String, String> response = new HashMap<>();
        response.put("token", result);
        response.put("role", loggedInUser.getRole().name());
        response.put("userId" , Integer.toString(loggedInUser.getUserId()));

        return ResponseEntity.ok(response);
    }
}
