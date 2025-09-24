package com.avinashee0012.crio_video_rental_basic.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {
    @NonNull
    @Email(message = "Email should be valid")
    private String email;

    @NonNull
    private String password;

    private String firstName;
    private String lastName;
    private String role = "CUSTOMER";
}
