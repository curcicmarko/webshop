package com.example.webshop.model.auth;

import com.example.webshop.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthToken {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "access_token", nullable = false)
    @Lob
    private String accessToken;

    @Column(name = "refresh_token", nullable = false)
    @Lob
    private String refreshToken;


}
