package com.dilyara.graduatework.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Data
@Entity(name = "usr")
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private String avatar;

  @Column
  private String username;

  @Column
  private String password;

  @Column
  private String fullName;

  @Column
  private String email;

  @Column
  private String phone;

  @Column
  private String instagramId;

  @Column
  private String instagramUsername;

  @Column
  private LocalDate businessDate;

  @Column
  private Float startCapital;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "token_id")
  private FacebookToken token;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities () {
    return null;
  }

  @Override
  public boolean isAccountNonExpired () {
    return true;
  }

  @Override
  public boolean isAccountNonLocked () {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired () {
    return true;
  }

  @Override
  public boolean isEnabled () {
    return true;
  }
}
