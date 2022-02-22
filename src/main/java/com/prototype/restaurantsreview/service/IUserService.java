package com.prototype.restaurantsreview.service;

import com.prototype.restaurantsreview.dto.UserRegistrationRequest;
import com.prototype.restaurantsreview.model.Article;
import com.prototype.restaurantsreview.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public interface IUserService extends UserDetailsService{
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    void fromDbToRepo() throws SQLException, ClassNotFoundException;
    String createTokenForUser(User user);
    String registerAndValidateUser(UserRegistrationRequest request);
    List<User> getUsers();
    User updateUser(User user, int userId);
    void deleteUserById(Integer userId);
    void lockUser(int id);
    void setUserAdmin(int id);
    void setUserUser(int id);
    void unlockUser(int id);
    void enableUser(int id);
}
