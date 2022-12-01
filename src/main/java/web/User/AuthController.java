package web.User;

import domain.jwt.JwtFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.dto.Login.ResponseLogin;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    // 생성자주입
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/authenticate") // Account 인증 API
    public ResponseEntity<ResponseLogin> authorize(@Valid @RequestBody RequestAuth.Login loginDto) {

        ResponseLogin token = authService.authenticate(loginDto.getUsername(), loginDto.getPassword());

        // response header 에도 넣고 응답 객체에도 넣는다.
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token.getAccessToken());

        return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);
    }
}