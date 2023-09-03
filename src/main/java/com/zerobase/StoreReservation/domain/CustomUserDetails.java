package com.zerobase.StoreReservation.domain;
import com.zerobase.StoreReservation.type.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
    public static CustomUserDetails build(User user) {
        List<GrantedAuthority> authorities = user.getUserType().equals(UserType.MANAGER)
                ? List.of(new SimpleGrantedAuthority("ROLE_MANAGER"))
                : List.of(new SimpleGrantedAuthority("ROLE_GENERAL"));

        return new CustomUserDetails(
                user.getUserId(),
                user.getUserPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부를 반환
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부를 반환
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 여부를 반환
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성 여부를 반환
    }
}