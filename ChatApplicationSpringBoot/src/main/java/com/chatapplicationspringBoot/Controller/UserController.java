package com.chatapplicationspringBoot.Controller;

import com.chatapplicationspringBoot.Model.User;
import com.chatapplicationspringBoot.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/Users")
public class UserController {
private String key = "da6d27f1-a033-44a9-88aa-a8a5f64a85db";

public boolean Authorization(String checkKey){
    if(checkKey .equals(key)){
        return true;
    }else return false;
}

final
UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public List<User> list() {
        return userService.listAllUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@RequestHeader("Authorization") String checkKey, @PathVariable Integer id) {
       if(Authorization(checkKey) == true) {
           try {
               User user = userService.getUser(id);
               return new ResponseEntity<User>(user, HttpStatus.OK);
           } catch (NoSuchElementException e) {
               return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
           }
       }
           else return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("/")
    public void add(@RequestHeader("Authorization") String checkKey, @RequestBody User user) {
        if(Authorization(checkKey) == true) {
            userService.saveUser(user);
            new ResponseEntity<User>(HttpStatus.OK);
        }
      else new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String checkKey , @RequestBody User user) {

        if(Authorization(checkKey) == true) {
            try {
                userService.saveUser(user);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (NoSuchElementException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        else return new ResponseEntity<User>(HttpStatus.NOT_FOUND);


    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader("Authorization") String checkKey ,@PathVariable Integer id) {

       if(Authorization(checkKey) == true) {
            userService.deleteUser(id);
            new ResponseEntity<User>(HttpStatus.OK);
        }
        else new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }
}

