import controllers.UserController;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import views.Processor;
import views.ProfileMenu;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class ProfileMenuTest {
    private final PrintStream standard = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final ProfileMenu profileMenu = new ProfileMenu();

    @BeforeEach
    public void setUp(){
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown(){
        System.setOut(standard);
        UserController.getInstance().setUsers(new ArrayList<>());
        UserController.getInstance().setLoggedInUser(null);
    }


    @Test
    public void checkChangeNickname() throws Exception {
        Processor processor = Mockito.mock(Processor.class);

        when(processor.get("nickname")).thenReturn("defg");
        when(processor.getNumberOfFields()).thenReturn(1);

        User user = new User("abc", "ghi4JKL", "def");
        UserController.getInstance().getUsers().add(user);
        UserController.getInstance().setLoggedInUser(user);

        Whitebox.invokeMethod(profileMenu, "changeNickname", processor);

        Assertions.assertEquals(processor.get("nickname"), UserController.getInstance().getLoggedInUser().getNickname());
        Assertions.assertEquals(outputStream.toString().trim(), "Nickname changed successfully!");
    }


    @Test
    public void checkChangePassword(){
        
    }
}
