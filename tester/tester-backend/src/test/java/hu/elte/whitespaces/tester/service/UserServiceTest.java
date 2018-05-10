package hu.elte.whitespaces.tester.service;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import hu.elte.whitespaces.tester.model.User;
import hu.elte.whitespaces.tester.repository.UserRepository;

public class UserServiceTest {

    private final static int USER_ID = 100;
    private final static String EMAIL = "UserName";

    private final UserRepository repository = mock(UserRepository.class);
    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    private UserService service;

    @Before
    public void setUp() {
        service = new UserService(repository, passwordEncoder);
    }

    @Test
    public void getUserTest() {
        User user = new User();
        user.setId(USER_ID);
        when(repository.findById(USER_ID)).thenReturn(Optional.of(user));

        service.getUserById(USER_ID);

        verify(repository).findById(USER_ID);
    }

    @Test
    public void getUserByEmailTest() {
        User user = new User();
        user.setEmail(EMAIL);
        when(repository.findByEmail(EMAIL)).thenReturn(Optional.of(user));

        service.getUserByEmail(EMAIL);

        verify(repository).findByEmail(EMAIL);
    }

    @Test
    public void saveTest() {
        User user = new User();
        user.setPassword("password");
        service.register(user);

        verify(repository).save(user);
    }

    @Test
    public void deleteTest() {
        User user = new User();
        user.setId(USER_ID);
        when(repository.findById(USER_ID)).thenReturn(Optional.of(user));

        service.delete(USER_ID);

        verify(repository).findById(USER_ID);
        verify(repository).delete(user);
    }

    @Test
    public void getAllUserTest() {
        User user = new User();
        user.setId(USER_ID);
        when(repository.findAll()).thenReturn(asList(user));

        service.getAllUser();

        verify(repository).findAll();
    }
}
