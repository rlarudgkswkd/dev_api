package web.User;

import domain.user.Account;
import domain.user.Authority;
import javassist.bytecode.DuplicateMemberException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dto.Login.AccountRepository;
import web.dto.Login.RequestUserRegister;
import web.dto.Login.ResponseUserRegister;

import java.util.Collections;

@Service
public class UserService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입 메서드
    @Transactional
    public ResponseUserRegister signup(RequestUserRegister registerDto) throws DuplicateMemberException {
        if (accountRepository.findOneWithAuthoritiesByUsername(registerDto.getUsername()).orElseGet(()->null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        // 이 유저는 권한이 ROLE_USER
        // 이건 부팅 시 data.sql에서 INSERT로 디비에 반영한다. 즉 디비에 존재하는 값이여야함
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        Account user = Account.builder()
                .username(registerDto.getUsername())
                .pw(passwordEncoder.encode(registerDto.getPw()))
                .nickname(registerDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        // DB에 저장하고 그걸 DTO로 변환해서 반환, 예제라서 비번까지 다 보낸다. 원랜 당연히 보내면 안댐
        return ResponseUserRegister.of(accountRepository.save(user));
    }
}