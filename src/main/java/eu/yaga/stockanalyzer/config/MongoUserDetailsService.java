package eu.yaga.stockanalyzer.config;

import eu.yaga.stockanalyzer.exception.UsernameExistsException;
import eu.yaga.stockanalyzer.model.MongoUser;
import eu.yaga.stockanalyzer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Implementation of Springs {@link UserDetailsService} using MongoDB
 */
public class MongoUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MongoUser user = userRepository.findByUsername(username);

        return user;
    }

    public MongoUser registerNewAccount(MongoUser mongoUser) throws UsernameExistsException {
        MongoUser usernameExists = userRepository.findByUsername(mongoUser.getUsername());
        if (usernameExists != null) {
            throw new UsernameExistsException("There is already a user with the username " + usernameExists.getUsername());
        }

        String encodedPassword = passwordEncoder.encode(mongoUser.getPassword());
        mongoUser.setPassword(encodedPassword);

        return userRepository.save(mongoUser);
    }

}
