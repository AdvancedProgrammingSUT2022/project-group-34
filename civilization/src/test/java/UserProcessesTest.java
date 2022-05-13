import controllers.UserController;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import views.Processor;
import views.RegisterMenu;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;


public class UserProcessesTest {

    @Mock
    private Processor processor;

    @Mock
    HashMap<String, String> fields;

    @Mock
    UserController controller;

    @Mock
    ArrayList<User> users;

    @Mock
    User user;

    @Mock
    RegisterMenu registerMenu;

    @Test
    public void checkIfUserWithUsernameExists() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Processor processor = Mockito.mock(Processor.class);

        HashMap<String, String> fields = Mockito.mock(HashMap.class);

        UserController controller = Mockito.mock(UserController.class);

//        User user = Mockito.mock(User.class);
        User user = new User("iuhaf", "iuhaf", "liuhaf");

        RegisterMenu registerMenu = new RegisterMenu();

        when(fields.get("username")).thenReturn("abc");
        when(fields.get("nickname")).thenReturn("def");
        when(fields.get("password")).thenReturn("ghi");


        when(processor.get("username")).thenReturn("abc");
        when(processor.get("nickname")).thenReturn("def");
        when(processor.get("password")).thenReturn("ghi");
        when(processor.getNumberOfFields()).thenReturn(3);

        when(controller.getUserByUsername("abc")).thenReturn(user);


        Method method = RegisterMenu.class.getDeclaredMethod("register", Processor.class);
        method.setAccessible(true);

//        method.invoke(registerMenu, processor);

        verify(method.invoke(registerMenu, processor), times(1));
//        verify(System.out.format("A user with username %s already exists\n", processor.get("username")));
    }

}
