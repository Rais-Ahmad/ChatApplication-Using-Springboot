package com.chatapplicationspringBoot.Controller;

import com.chatapplicationspringBoot.Model.Chat;
import com.chatapplicationspringBoot.Model.User;
import com.chatapplicationspringBoot.Service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/Chat")
public class ChatController {
    private String key = "da6d27f1-a033-44a9-88aa-a8a5f64a85db";

    public boolean Authorization(String checkKey){
        if(checkKey .equals(key)){
            return true;
        }else return false;
    }


   final
   ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("")
    public List<Chat> list() {
        return chatService.listAllUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chat> get(@RequestHeader("Authorization") String checkKey ,@PathVariable Integer id) {
        try {

            if(Authorization(checkKey) == true) {
                Chat chat = chatService.getChat(id);
                return new ResponseEntity<Chat>(chat, HttpStatus.OK);
            }
            else return new ResponseEntity<Chat>(HttpStatus.NOT_FOUND);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/")
    public void add(@RequestHeader("Authorization") String checkKey ,@RequestBody Chat chat) {

        if(Authorization(checkKey) == true) {
            chatService.saveChat(chat);
        }
        else new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    }


   @GetMapping("/question")
   public ResponseEntity<Chat> getQuestionById(@RequestHeader("Authorization") String checkKey , @RequestParam("question" ) Long id){

       if(Authorization(checkKey) == true) {
           return chatService.GetQuestionById(id);
       }
       else return new ResponseEntity<Chat>(HttpStatus.NOT_FOUND);


   }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String checkKey ,@RequestBody Chat chat, @PathVariable Integer id) {

        if(Authorization(checkKey) == true) {
            try {
                Chat existChat = chatService.getChat(id);
                chat.setId(id);
                chatService.saveChat(chat);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (NoSuchElementException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        else return new ResponseEntity<Chat>(HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("/{id}")
    public void delete(@RequestHeader("Authorization") String checkKey , @PathVariable Integer id) {


        if(Authorization(checkKey) == true) {
            chatService.deleteChat(id);
            new ResponseEntity<User>(HttpStatus.OK);
        }
        else new ResponseEntity<User>(HttpStatus.NOT_FOUND);


    }


}
