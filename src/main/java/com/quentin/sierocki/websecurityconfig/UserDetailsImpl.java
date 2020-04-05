package com.quentin.sierocki.websecurityconfig;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDetailsImpl extends org.springframework.security.core.userdetails.User implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int id;

    public UserDetailsImpl(int id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        //this(username, password, true, true, true, true, authorities);
        super(username, password, true, true, true, true, authorities);
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
