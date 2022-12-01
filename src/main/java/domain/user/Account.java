package domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name="account")
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @Column(name = "userid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(name = "username", length = 50, unique = true)
    private String username;

    @Column(name = "pw ", length = 100)
    private String pw ;

    @Column(name = "points")
    private Long nickname;

    @Column(name = "activated")
    private boolean activated;

    @ManyToMany
    @JoinTable( // JoinTable은 테이블과 테이블 사이에 별도의 조인 테이블을 만들어 양 테이블간의 연관관계를 설정 하는 방법
            name = "account_authority",
            joinColumns = {@JoinColumn(name = "userid", referencedColumnName = "userid")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    @Builder
    public Account(String username, String pw, String nickname, Set<Authority> authorities, boolean activated) {
        this.username = username;
        this.pw = pw;
        this.authorities = authorities;
        this.activated = activated;
//        this.tokenWeight = 1L; // 초기 가중치는 1
    }

}
