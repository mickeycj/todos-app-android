package ssd.project.mickeycj.todosapp.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by user on 4/6/60.
 */

public class ItemTest {

    private Item item;

    @Before
    public void setUp() { item = new Item(); }

    @Test
    public void shouldGetTitle() {
        item.setTitle("title");
        assertEquals("title", item.getTitle());
    }

    @Test
    public void shouldMarkAsDone() {
        item.markAsDone();
        assertTrue(item.isDone());
    }

    @Test
    public void shouldMarkAsNotDone() {
        item.markAsDone();
        item.markAsNotDone();
        assertFalse(item.isDone());
    }

    @Test
    public void shouldGetCreatedDate() {
        Date now = new Date();
        item.setCreatedAt(now);
        assertEquals(0, now.compareTo(item.getCreatedAt()));
    }
}
