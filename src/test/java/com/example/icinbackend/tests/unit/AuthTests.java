package com.example.icinbackend.tests.unit;

import com.example.icinbackend.DAO.UserDAO;
import com.example.icinbackend.models.User;
import com.example.icinbackend.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

@ExtendWith(MockitoExtension.class)
public class AuthTests {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @Test
    public void addedUserHasPassword() {
        when(userDAO.addOne(ArgumentMatchers.any(User.class))).then(returnsFirstArg());
        User user = userService.addUser("eric", "jojobar");
        assertThat(user.getPassword()).isNotNull();
    }

    @Test
    public void newUsersPasswordIsHashed() {
        when(userDAO.addOne(ArgumentMatchers.any(User.class))).then(returnsFirstArg());
        String password = "jojobar";
        User user = userService.addUser("eric", "jojobar");
        assertThat(user.getPassword().equals("jojobar")).isFalse();
    }

}
