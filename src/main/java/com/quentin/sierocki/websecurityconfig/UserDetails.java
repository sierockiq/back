package com.quentin.sierocki.websecurityconfig;

public interface UserDetails extends org.springframework.security.core.userdetails.UserDetails {

    int getId();
}
