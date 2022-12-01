package web.dto.Login;

import domain.user.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserRegister {
    private String username;
    private String pw;
    private String nickname;
    private Set<String> authoritySet;

    public static ResponseUserRegister of(Account account) {
        if(account == null) return null;

        return ResponseUser.Info.builder()
                .username(account.getUsername())
                .pw(account.getPw())
                .nickname(account.getNickname())
                .authoritySet(account.getAuthorities().stream()
                        .map(authority -> authority.getAuthorityName())
                        .collect(Collectors.toSet()))
                .build();
    }
}