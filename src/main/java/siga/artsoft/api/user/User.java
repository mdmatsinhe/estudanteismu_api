package siga.artsoft.api.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import siga.artsoft.api.utils.IdEntity;

import java.security.Permission;
import java.util.*;

@Table(name="users")
@Entity(name="User")
public class User extends IdEntity implements UserDetails {


    private static final long serialVersionUID = 6311364761937265306L;

    @NotNull(message = "Utilizador não pode ser vazio")
    @NotEmpty(message = "Utilizador não pode conter apenas espaços em branco")
    @Size(max = 50, message = "Utilizador não deve ter mais de 50 caracteres")
    @Column(name = "username", length = 50, unique = true)
    private String username;

    @NotNull(message = "Senha não pode ser vazia")
    @NotEmpty(message = "Senha não pode conter apenas espaços em branco")
    @Column(columnDefinition = "LONGTEXT")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Transient
    private String planPass = "";

    private String type;

    @ColumnDefault("0")
    @Column(name = "login_count")
    private int loginCount;

    public int getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public User() {
        this.enabled = true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPlanPass() {
        return planPass;
    }

    public void setPlanPass(String planPass) {
        this.planPass = planPass;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String toString() {
        return String.format("%s(id=%d, username=%s, password=%s, enabled=%b)",
                this.getClass().getSimpleName(), this.getId(),
                this.getUsername(), this.getPassword(), this.getEnabled());
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean isAccountNonExpired() {
        // return true = account is valid / not expired
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // return true = account is not locked
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        // return true = password is valid / not expired
        return true;
    }

    public boolean isEnabled() {
        return this.getEnabled();
    }

    public void SetPasswordEncripted(String senha) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(senha);

    }


    public String gerarSenhaAleatoria() {
        int max = 8;
        String[] caracteres = { "a", "1", "b", "2", "4", "5", "6", "7", "8",
                "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
                "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
                "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
                "V", "W", "X", "Y", "Z" };

        StringBuilder senha = new StringBuilder();

        for (int i = 0; i < max; i++) {
            int posicao = (int) (Math.random() * caracteres.length);
            senha.append(caracteres[posicao]);
        }

        return senha.toString();
    }
}
