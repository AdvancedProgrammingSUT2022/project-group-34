import controllers.UserController;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import views.Processor;
import views.RegisterMenu;

import javax.annotation.processing.Processor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.*;

@PrepareForTest(UserController.class)
public class RegisterMenuTest {
//    UserController controller;


    @BeforeEach
    public void setUp() throws Exception {
//        controller = Whitebox.invokeConstructor(UserController.class);
//        PowerMockito.mockStatic(UserController.class);
    }

    @Test
    public void checkCreatingUser() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Processor processor = Mockito.mock(Processor.class);

        User user = new User("abc", "def", "ghi4JKL");

        RegisterMenu registerMenu = new RegisterMenu();

        when(processor.get("username")).thenReturn("abc");
        when(processor.get("nickname")).thenReturn("def");
        when(processor.get("password")).thenReturn("ghi4JKL");
        when(processor.getNumberOfFields()).thenReturn(3);


        ArrayList<User> users = Mockito.mock(ArrayList.class);


        PowerMockito.mockStatic(UserController.class);
        when(UserController.getInstance().getUsers()).thenReturn(users);


        verify(UserController.getInstance()).getUsers().add(user);



//        Method method = RegisterMenu.class.getDeclaredMethod("register", Processor.class);
//        method.setAccessible(true);

//        method.invoke(registerMenu, processor);

//        verify(method.invoke(registerMenu, processor), times(1));
//        verify(System.out.format("A user with username %s already exists\n", processor.get("username")));
    }

}
