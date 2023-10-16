package br.com.wswork.bestcommerceapi.dto;

import br.com.wswork.bestcommerceapi.model.User;
import br.com.wswork.bestcommerceapi.utils.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterDTO {
    private String login;
    private String password;
    private UserRole role;

    public static RegisterDTO convertUserToDTO(User user) {

        return RegisterDTO.builder()
                          .login(user.getLogin())
                          .role(user.getRole())
                          .build();
    }
}
