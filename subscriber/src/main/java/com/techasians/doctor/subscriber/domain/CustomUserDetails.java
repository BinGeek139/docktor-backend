package com.techasians.doctor.subscriber.domain;

import com.techasians.doctor.subscriber.utils.Constants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class CustomUserDetails implements UserDetails {
    private Subscriber subscriber;

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public CustomUserDetails(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
        grantedAuthorities.add( new SimpleGrantedAuthority(subscriber.getRole()));
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.subscriber.getPassword();
    }

    @Override
    public String getUsername() {
        return this.subscriber.getPhoneNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        if(Objects.isNull(subscriber.getExpireDatePassword())){
            return true;
        }
       long expireDatePassword= this.subscriber.getExpireDatePassword().getTime();
       long current=System.currentTimeMillis();
       return expireDatePassword >= current ;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        return this.subscriber.getStatus().equals(Constants.ACTIVE);
    }
}
