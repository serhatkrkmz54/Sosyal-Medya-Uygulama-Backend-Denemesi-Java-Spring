package org.socialmedia.sonsocialapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyUserDTO {
    private String email;
    private String verificationCode;

}
