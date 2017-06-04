package ssd.project.mickeycj.todosapp.model;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by user on 4/6/60.
 */

public class UserTest {

    private User user;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field currentUser = User.class.getDeclaredField("currentUser");
        currentUser.setAccessible(true);
        currentUser.set(null, null);
        user = User.getCurrentUser();
    }

    @Test
    public void shouldGetUsername() {
        user.setUsername("username");
        assertEquals("username", user.getUsername());
    }

    @Test
    public void shouldGetEmail() {
        user.setEmail("user@email.com");
        assertEquals("user@email.com", user.getEmail());
    }

    @Test
    public void shouldGetJoinedDate() {
        Date now = new Date();
        user.setJoinedAt(now);
        assertEquals(0, now.compareTo(user.getJoinedAt()));
    }
}
