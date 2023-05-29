package com.vstavaystrana.vstavaystrana_site.services;

//import com.vstavaystrana.vstavaystrana_site.models.Role;
import com.vstavaystrana.vstavaystrana_site.models.Businessman;
import com.vstavaystrana.vstavaystrana_site.models.Role;
import com.vstavaystrana.vstavaystrana_site.models.User;
import com.vstavaystrana.vstavaystrana_site.repositories.BusinessmanRepository;
import com.vstavaystrana.vstavaystrana_site.repositories.RoleRepository;
import com.vstavaystrana.vstavaystrana_site.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepo;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    BusinessmanRepository businessmanRepository;

    public BCryptPasswordEncoder getbCryptPasswordEncoder(){
        return bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> userGetList(Long startId) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", startId).getResultList();
    }


//    public void assignRole(User user, Role.Names roleName){
//        Role role = roleRepo.findByName(roleName.name());
//        if(role == null || user == null) return;
//        assignRole(user, role);
//    }
//
//    private void assignRole(User user, Role role){
//        user.addRole(role);
//        userRepository.save(user);
//    }
}