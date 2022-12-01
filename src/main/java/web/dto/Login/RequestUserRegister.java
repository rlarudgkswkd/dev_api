package web.dto.Login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserRegister {
    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    @Size(min = 5, max = 100)
    private String pw;

    @NotNull
    @Size(min = 5, max = 100)
    private String nickname;
}