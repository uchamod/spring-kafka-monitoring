package com.uchamod.user.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private Date userRegisterDate;
    private String userRole;
    private String userStatus;
    private String userPhone;
    private String userAddress;
}

//{
//        "userName": "Chamod Udara",
//        "userEmail": "uchamod52@gmail.com",
//        "userPassword": "uchamod1234",
//        "userRegisterDate": "24-10-2025",
//        "userRole": "SELLER",
//        "userStatus": "ACTIVE",
//        "userPhone": "+94712345678",
//        "userAddress": "No. 45, Main Street, Colombo"
//        }

/*
{
        "userName": "Prime time",
        "userEmail": "hailprimarch@gmail.com",
        "userPassword": "primarch1234",
        "userRegisterDate": "24-10-2025",
        "userRole": "CUSTOMER",
        "userStatus": "ACTIVE",
        "userPhone": "+94782349678",
        "userAddress": "No. 45, Main Street, Kaluthara"
        }*/
/*
{
        "userName": "Natalie",
        "userEmail": "branchnatalie8@gmail.com",
        "userPassword": "Natalie1234",
        "userRegisterDate": "24-10-2025",
        "userRole": "CUSTOMER",
        "userStatus": "ACTIVE",
        "userPhone": "+9478834978",
        "userAddress": "No. 45, wall street, USA"
        }*/

/*{
        "userName": "diddugoda",
        "userEmail": "diddugodageudara@gmail.com",
        "userPassword": "diddugoda1234",
        "userRegisterDate": "24-10-2025",
        "userRole": "SELLER",
        "userStatus": "ACTIVE",
        "userPhone": "+9478534878",
        "userAddress": "No. 45, Maharishi, India"
        }*/
