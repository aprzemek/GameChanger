package pl.sdacademy.gamechanger.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDTO {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
}
