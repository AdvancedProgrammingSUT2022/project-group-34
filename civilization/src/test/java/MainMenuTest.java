import controllers.UserController;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import views.MainMenu;
import views.Processor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class MainMenuTest {
    private final PrintStream standard = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final Processor processor = Mockito.mock(Processor.class);
    private final MainMenu mainMenu = new MainMenu();

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
    public void checkLogout() throws Exception {
        when(processor.getSection()).thenReturn("logout");

        User user = new User("abc", "ghi4JKL", "def");
        UserController.getInstance().getUsers().add(user);
        UserController.getInstance().setLoggedInUser(user);

        Whitebox.invokeMethod(mainMenu, "logout", processor);

        Assertions.assertNull(UserController.getInstance().getLoggedInUser());
        Assertions.assertEquals(outputStream.toString().trim(), "User logged out successfully!");
    }


    @Test
    public void checkStartGame(){
        when(processor.getNumberOfFields()).thenReturn(1);


    }
}
