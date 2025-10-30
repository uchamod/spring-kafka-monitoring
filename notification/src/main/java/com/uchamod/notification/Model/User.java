package com.uchamod.notification.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

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
