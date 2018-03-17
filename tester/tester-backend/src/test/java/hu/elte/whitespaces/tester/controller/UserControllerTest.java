package hu.elte.whitespaces.tester.controller;

import static hu.elte.whitespaces.tester.model.User.Role.STUDENT;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import hu.elte.whitespaces.tester.model.User;
import hu.elte.whitespaces.tester.model.User.Role;

public class UserControllerTest extends AbstractControllerTest {

    private final static String EMAIL = "user@mail.com";
    private final static String EMAIL_2 = "2use2r@mail2.com2";
    private final static String PASSWORD = "supers3cret";
    private final static Role ROLE = STUDENT;
    private final static String FIRST_NAME = "FIrSrT";
    private final static String LAST_NAME = "Name";
    private final static String SCHOOL = "school";

    @Autowired
    private UserController controller;

    @Test
    public void getAllTest() {
        User user1 = controller.create(buildUserWithGivenEmail(EMAIL))
                               .getBody();
        User user2 = controller.create(buildUserWithGivenEmail(EMAIL_2))
                               .getBody();

        List<User> users = controller.getAll().getBody();

        assertThat(users).hasSize(2);
        assertThat(users.get(0)).isEqualToComparingFieldByField(user1);
        assertThat(users.get(1)).isEqualToComparingFieldByField(user2);
    }

    @Test
    public void getUserByUsernameTest() {
        User user1 = controller.create(buildUserWithGivenEmail(EMAIL))
                               .getBody();
        User user2 = controller.create(buildUserWithGivenEmail(EMAIL_2))
                               .getBody();

        assertThat(controller.getUserByEmail(EMAIL).getBody()).isEqualToComparingFieldByField(user1);
        assertThat(controller.getUserByEmail(EMAIL_2).getBody()).isEqualToComparingFieldByField(user2);
    }

    @Test
    public void getUserByIdTest() {
        User user1 = controller.create(buildUserWithGivenEmail(EMAIL))
                               .getBody();

        assertThat(controller.getUserById(user1.getId()).getBody()).isEqualToComparingFieldByField(user1);
    }


    @Test
    public void createTest() {
        User created = controller.create(buildUserWithGivenEmail(EMAIL))
                                 .getBody();

        assertThat(created.getPassword()).isEqualTo(PASSWORD);
        assertThat(created.getEmail()).isEqualTo(EMAIL);
        assertThat(created.getRole()).isEqualTo(STUDENT);
        assertThat(created.getFirstname()).isEqualTo(FIRST_NAME);
        assertThat(created.getLastname()).isEqualTo(LAST_NAME);
        assertThat(created.getSchool()).isEqualTo(SCHOOL);
    }

    @Test
    public void deleteTest() {
        User user1 = controller.create(buildUserWithGivenEmail(EMAIL))
                               .getBody();

        ResponseEntity<User> deleteResponse = controller.delete(user1.getId());
        assertThat(deleteResponse.getStatusCode()).isEqualTo(OK);
    }

    private User buildUserWithGivenEmail(String email) {

        User user = new User();
        user.setSchool(SCHOOL);
        user.setEmail(email);
        user.setPassword(PASSWORD);
        user.setRole(ROLE);
        user.setFirstname(FIRST_NAME);
        user.setLastname(LAST_NAME);

        return user;
    }
}
