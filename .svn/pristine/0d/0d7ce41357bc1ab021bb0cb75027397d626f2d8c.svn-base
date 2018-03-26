package com.sf.social.signinmvc.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sf.social.signinmvc.user.model.User;

/**
 * @author Petri Kainulainen
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    
    User findById(String id);
 
}
