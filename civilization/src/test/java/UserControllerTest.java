import controllers.UserController;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class UserControllerTest {
    User user1 = Mockito.mock(User.class);
    User user2 = Mockito.mock(User.class);
    User user3 = Mockito.mock(User.class);

    @BeforeEach
    public void setUp() {
        UserController.getInstance().setUsers(new ArrayList<>(List.of(user1, user2, user3)));
    }

    @AfterEach
    public void clear(){
        UserController.getInstance().setUsers(new ArrayList<>());
    }

    @Test
    public void checkIfPasswordIsStrong() {
        String password1 = "abcde";
        String password2 = "Abcdef";
        String password3 = "A12345";
        String password4 = "a12345";
        String password5 = "Aa1234";

        Assertions.assertFalse(UserController.getInstance().isPasswordStrong(password1));
        Assertions.assertFalse(UserController.getInstance().isPasswordStrong(password2));
        Assertions.assertFalse(UserController.getInstance().isPasswordStrong(password3));
        Assertions.assertFalse(UserController.getInstance().isPasswordStrong(password4));
        Assertions.assertTrue(UserController.getInstance().isPasswordStrong(password5));
    }


    @Test
    public void checkGetUserByUsername() {
        when(user1.getUsername()).thenReturn("abc");
        when(user2.getUsername()).thenReturn("def");
        when(user3.getUsername()).thenReturn("ghi");

        Assertions.assertNotNull(UserController.getInstance().getUserByUsername("abc"));

        when(user1.getUsername()).thenReturn("abcd");

        Assertions.assertNull(UserController.getInstance().getUserByUsername("abc"));
    }


    @Test
    public void checkGetUserByNickname() {
        when(user1.getNickname()).thenReturn("abc");
        when(user2.getNickname()).thenReturn("def");
        when(user3.getNickname()).thenReturn("ghi");

        Assertions.assertNotNull(UserController.getInstance().getUserByNickname("abc"));

        when(user1.getNickname()).thenReturn("abcd");

        Assertions.assertNull(UserController.getInstance().getUserByNickname("abc"));
    }
}
