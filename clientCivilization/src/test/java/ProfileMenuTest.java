import app.controllers.UserController;
import app.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import app.models.connection.Processor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.when;

public class ProfileMenuTest {
    private final PrintStream standard = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final Processor processor = Mockito.mock(Processor.class);

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
        User user = new User("abc", "ghi4JKL", "def");
        //UserController.getInstance().getUsers().add(user);
        //UserController.getInstance().setLoggedInUser(user);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standard);
        //UserController.getInstance().setUsers(new ArrayList<>());
        //UserController.getInstance().setLoggedInUser(null);
    }

    @Test
    public void checkChangeNickname() throws Exception {
        when(processor.get("nickname")).thenReturn("defg");
        when(processor.getNumberOfFields()).thenReturn(1);

        Assertions.assertEquals(processor.get("nickname"), UserController.getInstance().getLoggedInUser().getNickname());
        Assertions.assertEquals(outputStream.toString().trim(), "Nickname changed successfully!");
    }


    @Test
    public void checkChangePassword() throws Exception {
        when(processor.get("current")).thenReturn("ghi4JKL");
        when(processor.get("new")).thenReturn("GHI4jkl");
        when(processor.get("password")).thenReturn(null);
        when(processor.getNumberOfFields()).thenReturn(3);

        Assertions.assertTrue(UserController.getInstance().getLoggedInUser().isPasswordCorrect(processor.get("new")));
        Assertions.assertEquals(outputStream.toString().trim(), "Password changed successfully!");
    }
}
