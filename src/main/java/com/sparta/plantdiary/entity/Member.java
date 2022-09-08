package com.sparta.plantdiary.entity;

import com.sparta.plantdiary.dto.MypageRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE member SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
public class Member extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String nickname;

    @Column(nullable = false, length = 128)
    private String email;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String password;

    public Member(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    };

    public void updateNickname(MypageRequestDto requestDto){
        if(!(""==requestDto.getNickname())){
            this.nickname = requestDto.getNickname();
        }
    }
    public void updatePassword(String pw){
        if(!(""==pw)) {
            this.password = pw;
        }
    }

    public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }
}
