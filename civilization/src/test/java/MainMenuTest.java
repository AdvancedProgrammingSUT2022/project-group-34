import app.controllers.gameServer.GameController;
import app.controllers.singletonController.UserController;
import app.models.User;
import app.models.connection.Processor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class MainMenuTest {
    private final PrintStream standard = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final Processor processor = Mockito.mock(Processor.class);
    //private final MainMenu mainMenu = new MainMenu();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
        User user = new User("abc", "ghi4JKL", "def");
        UserController.getInstance().getUsers().add(user);
        //UserController.getInstance().addLoggedInUser(user);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standard);
        UserController.getInstance().setUsers(new ArrayList<>());
        //UserController.getInstance().LogoutUser(user);
    }

    @Test
    public void checkLogout() throws Exception {
        when(processor.getSection()).thenReturn("logout");

        //Whitebox.invokeMethod(mainMenu, "logout", processor);

        //Assertions.assertNull(UserController.getInstance().getLoggedInUsers(mySocketHandler.getSocketToken()));
        Assertions.assertEquals(outputStream.toString().trim(), "User logged out successfully!");
    }


    @Test
    public void checkStartGame() throws Exception {
        when(processor.getNumberOfFields()).thenReturn(1);

        User user2 = new User("ABC", "GHI4jkl", "DEF");
        UserController.getInstance().getUsers().add(user2);

        when(processor.get("player1")).thenReturn(user2.getUsername());

        //Whitebox.invokeMethod(mainMenu, "startNewGame", processor);

        Assertions.assertNotNull(GameController.getInstance().getGame());
        Assertions.assertEquals(outputStream.toString().trim(), "Game started!");

        GameController.getInstance().setGame(null);
    }
}
