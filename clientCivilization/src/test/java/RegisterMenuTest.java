import app.controllers.UserController;
import app.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import app.models.connection.Processor;
import app.views.commandLineMenu.RegisterMenu;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class RegisterMenuTest {
    private final PrintStream standard = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final Processor processor = Mockito.mock(Processor.class);
    private final RegisterMenu registerMenu = new RegisterMenu();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
        when(processor.get("username")).thenReturn("abc");
        when(processor.get("password")).thenReturn("ghi4JKL");
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standard);
        UserController.getInstance().setUsers(new ArrayList<>());
    }

    @Test
    public void checkCreatingUser() throws Exception {
        when(processor.get("nickname")).thenReturn("def");
        when(processor.getNumberOfFields()).thenReturn(3);

        Whitebox.invokeMethod(registerMenu, "register", processor);

        Assertions.assertEquals(outputStream.toString().trim(), "User Created successfully!");
    }


    @Test
    public void checkLogin() throws Exception {
        when(processor.getNumberOfFields()).thenReturn(2);

        UserController.getInstance().getUsers().add(new User("abc", "ghi4JKL", "def"));

        Whitebox.invokeMethod(registerMenu, "login", processor);

        Assertions.assertEquals(outputStream.toString().trim(), "User logged in successfully!");
    }
}
