package com.prototype.restaurantsreview.service;

import com.prototype.restaurantsreview.dto.UserRegistrationRequest;
import com.prototype.restaurantsreview.model.Article;
import com.prototype.restaurantsreview.model.ConfirmationToken;
import com.prototype.restaurantsreview.model.ERole;
import com.prototype.restaurantsreview.model.User;
import com.prototype.restaurantsreview.repository.ConfirmationTokenRepository;
import com.prototype.restaurantsreview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ConfirmationTokenService confirmationTokenService;
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private final static String USER_NOT_FOUND="User %s not found!";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByUsername(username).
                orElseThrow(()->new UsernameNotFoundException(String.format(USER_NOT_FOUND,username)));
    }

    public void fromDbToRepo() throws SQLException, ClassNotFoundException {
        String myDriver = "com.mysql.cj.jdbc.Driver";
        String myUrl ="jdbc:mysql://localhost:3306/reviewdb";
        Class.forName(myDriver);
        String query = "SELECT * FROM users";
        Connection conn = DriverManager.getConnection(myUrl, "root", "admin");
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next())
        {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setUserEmail(rs.getString("userEmail"));
            user.setUserPassword(rs.getString("userPassword"));
            userRepository.save(user);
        }
        st.close();
    }

    public String createTokenForUser(User user) {
        boolean userExists = userRepository
                .findByUserEmail(user.getUserEmail()).isPresent();
        if (userExists) {throw new IllegalStateException("email already taken");}

        String encodedPassword = bCryptPasswordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodedPassword);


        userRepository.save(user);

        String token= UUID.randomUUID().toString();
        ConfirmationToken confirmationToken=new ConfirmationToken(token,
                                                                    LocalDateTime.now(),
                                                                    LocalDateTime.now().plusMinutes(15),
                                                                    user);



        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public String registerAndValidateUser(UserRegistrationRequest request) {
        String token = createTokenForUser(
                new User(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getUsername(),
                        request.getPassword(),
                        ERole.USER
                )
        );

        String link = "http://localhost:8080/confirm?token=" + token;
        emailService.send(
                request.getEmail(),
                emailService.buildEmail(request.getUsername(), link));
        return token;
    }

    public List<User> getUsers()
    {
        return userRepository.findAll();
    }

    public User updateUser(User user, int userId)
    {
        User temp = userRepository.findById(userId).get();

        if (Objects.nonNull(user.getUserFirstName()) && !"".equalsIgnoreCase(user.getUserFirstName()))
        {temp.setUserFirstName(user.getUserFirstName());}

        if (Objects.nonNull(user.getUserLastName()) && !"".equalsIgnoreCase(user.getUserLastName()))
        {temp.setUserLastName(user.getUserLastName());}


        return userRepository.save(temp);
    }

    public void deleteUserById(Integer userId)
    {
        User temp=userRepository.findById(userId).get();
        confirmationTokenRepository.deleteConfirmationTokenByUser(temp);
        userRepository.deleteById(userId);
    }

    public void setUserAdmin(int id){
        User user=userRepository.findById(id).get();
        user.setUserRole(ERole.ADMIN);
        userRepository.save(user);
    }
    public void setUserUser(int id){
        User user=userRepository.findById(id).get();
        user.setUserRole(ERole.USER);
        userRepository.save(user);
    }
    public void lockUser(int id){
        User user=userRepository.findById(id).get();
        user.setLocked(true);
        userRepository.save(user);
    }
    public void unlockUser(int id){
        User user=userRepository.findById(id).get();
        user.setLocked(false);
        userRepository.save(user);
    }
    public void enableUser(int id){
        User user=userRepository.findById(id).get();
        user.setEnabled(true);
        userRepository.save(user);
    }
}
