package kz.iitu.swd.group34.FirstSpringBootIITU.services;

import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Users;
import kz.iitu.swd.group34.FirstSpringBootIITU.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(s);
        User secUser = new User(user.getEmail(), user.getPassword(), user.getRoles());
        return secUser;
    }
}
