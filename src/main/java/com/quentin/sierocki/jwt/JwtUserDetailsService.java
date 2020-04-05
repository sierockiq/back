// https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world

package com.quentin.sierocki.jwt;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.quentin.sierocki.controller.model.UserDTO;
import com.quentin.sierocki.domain.entity.UserDAO;
import com.quentin.sierocki.domain.repository.UserRepository;
import com.quentin.sierocki.exception.ResourceNotFoundException;
import com.quentin.sierocki.websecurityconfig.UserDetails;
import com.quentin.sierocki.websecurityconfig.UserDetailsImpl;

//import org.springframework.security.core.userdetails.UserDetails;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws ResourceNotFoundException {

        UserDAO user = userRepository.findByUsername(username);

        if(user == null) {
            throw new ResourceNotFoundException("User", "username", username);
        }
                //.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));



        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getPassword(),
                new ArrayList<>());

    }

    /*public UserDetails loadUserById(int id) {
        // Todo: create a new ResourceNotFound Exception class and throw it here

        DAOUser user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }*/

    public UserDetails loadUser(UserDTO user) {
        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

}
