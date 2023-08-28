package com.zipsoft.commons.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zipsoft.auth.dto.UserDto;
import com.zipsoft.model.entity.User;

import lombok.Getter;
import lombok.Setter;

public class UserPrincipal implements UserDetails {
	
	private UserDto user;
	
	public UserPrincipal(UserDto user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collections = new ArrayList<>();
        collections.add(() -> {
            return user.getUserName();
        });

        return collections;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
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
		return true;
	}
	
	public long getUserId() {
		return this.user.getId();
	}
	
	public UserDto getUser() {
		return this.user;
	}

}
