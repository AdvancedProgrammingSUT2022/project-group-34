import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;
import views.Processor;
import views.RegisterMenu;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

public class RegisterMenuTest {
    private final PrintStream standard = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standard);
    }

    @Test
    public void checkCreatingUser() throws Exception {
        RegisterMenu registerMenu = new RegisterMenu();

        Processor processor = Mockito.mock(Processor.class);

        when(processor.get("username")).thenReturn("abc");
        when(processor.get("nickname")).thenReturn("def");
        when(processor.get("password")).thenReturn("ghi4JKL");
        when(processor.getNumberOfFields()).thenReturn(3);

        Whitebox.invokeMethod(registerMenu, "register", processor);

        Assertions.assertEquals(outputStream.toString().trim(), "User Created successfully!");
    }
}
