package com.uchamod.user.Service;

import com.uchamod.commonmodules.DTO.UserWrapper;
import com.uchamod.user.Model.User;

import com.uchamod.user.Reposotory.UserRepo;
import com.uchamod.user.Service.Kproducer.UserDeleteProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UserDeleteProducer userDeleteProducer;
    //get all users
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try{
            List<User> users=userRepo.findAll();
            List<UserWrapper> userWrappers=users.stream()
                    .map(user -> new UserWrapper(
                            user.getUserName(),
                            user.getUserEmail(),
                            user.getUserRole(),
                            user.getUserStatus(),
                            user.getUserPhone(),
                            user.getUserAddress()
                    )).collect(Collectors.toList());
            return  ResponseEntity.ok(userWrappers);
        }catch (Exception e){
            System.out.println("faild to get all users");
            return ResponseEntity.internalServerError().build();
        }
    }
   //get user by email
    public ResponseEntity<UserWrapper> getUserByEmail(String email) {
        try{
            User user=userRepo.findByUserEmail(email);
            UserWrapper userWrapper=new UserWrapper(
                    user.getUserName(),
                    user.getUserAddress(),
                    user.getUserRole(),
                    user.getUserStatus(),
                    user.getUserPhone(),
                    user.getUserEmail()
            );
            return ResponseEntity.ok(userWrapper);
        }catch (Exception e){
            System.out.println("failed to get user data"+e.getMessage());
           return ResponseEntity.internalServerError().build();
        }
    }
//get user by id
    public ResponseEntity<UserWrapper> getUserById(UUID uuid) {
        try{
            if(uuid == null){
                System.out.println("no userId provided");
                return ResponseEntity.badRequest().build();
            }
            Optional<User> user= userRepo.findById(uuid);
            if(user.isEmpty()){
                System.out.println("no user releted to userId");
                return ResponseEntity.internalServerError().build();
            }
            UserWrapper userWrapper=new UserWrapper(
                    user.get().getUserName(),
                    user.get().getUserEmail(),
                    user.get().getUserRole(),
                    user.get().getUserStatus(),
                    user.get().getUserPhone(),
                    user.get().getUserAddress());
            return ResponseEntity.ok(userWrapper);
        }catch (Exception e){
            System.out.println("internal server error"+e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
//get all customers
    public ResponseEntity<List<UserWrapper>> getAllCustomers(String userRole) {
        try{
            List<User> users=  userRepo.findUserByUserRole(userRole);
            if(users.isEmpty()){
                System.out.println("no users");
                return ResponseEntity.notFound().build();
            }

            List<UserWrapper> userWrappers=users.stream()
                    .map(user -> new UserWrapper(
                            user.getUserName(),
                            user.getUserEmail(),
                            user.getUserRole(),
                            user.getUserStatus(),
                            user.getUserPhone(),
                            user.getUserAddress()
                    )).collect(Collectors.toList());
            return  ResponseEntity.ok(userWrappers);
        }catch (Exception e){
            System.out.println("faild to get all Customers");
            return ResponseEntity.internalServerError().build();
        }
    }
    //clear all user data
    @Transactional
    public ResponseEntity<String> deteteUser(UUID userId) {
        try{
            if(userId == null){
                System.out.println("no userId provided");
                return ResponseEntity.badRequest().build();
            }
            //delete user by userid
            userRepo.deleteById(userId);

            userDeleteProducer.sendUserDeleteEvent(userId);
            //delete product by userid(if exists)
            //detete cart by userid(if exists)
            //delete order by userid(if exists)


            return ResponseEntity.ok("user deleted successfully");
        }catch (Exception e){
            System.out.println("faild to delete user");
            return ResponseEntity.internalServerError().build();
        }
    }

}
