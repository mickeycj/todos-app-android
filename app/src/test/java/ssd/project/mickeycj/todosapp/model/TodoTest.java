package ssd.project.mickeycj.todosapp.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by user on 4/6/60.
 */

public class TodoTest {

    private Todo todo;

    @Before
    public void setUp() { todo = new Todo(); }

    @Test
    public void shouldGetTitle() {
        todo.setTitle("title");
        assertEquals("title", todo.getTitle());
    }

    @Test
    public void shouldGetImportance() {
        todo.setImportance(true);
        assertTrue(todo.isImportant());
    }

    @Test
    public void shoudlGetCreatedDate() {
        Date now = new Date();
        todo.setCreatedAt(now);
        assertEquals(0, now.compareTo(todo.getCreatedAt()));
    }
}
