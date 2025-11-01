package com.uchamod.user.Model;

import com.uchamod.commonmodules.DTO.UserWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAuthResponse {
    private String token;
    private String type="Bearer";
    private UserWrapper userWrapper;

    public UserAuthResponse(String token,UserWrapper userWrapper){
        this.token=token;
        this.userWrapper=userWrapper;
    }

}
