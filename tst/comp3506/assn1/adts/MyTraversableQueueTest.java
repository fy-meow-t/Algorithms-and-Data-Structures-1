package comp3506.assn1.adts;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class MyTraversableQueueTest {

    private TraversableQueue<Object> testQueue;

    @Before
    public void setUp() {
        testQueue = new TraversableQueue<>();
    }

    @After
    public void tearDown() {
        testQueue = null;
    }

    @Test
    public void testIllegalEnqueue() {
        int max = 20000;
        for (int i = 0; i < max; i++) {
            testQueue.enqueue(new Object());
        }
        assertEquals(max, testQueue.size());
        try {
            testQueue.enqueue(new Object());
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void testDequeue() {
        try {
            testQueue.dequeue();
            fail();
        } catch (IndexOutOfBoundsException e) {
        }

        Object first = new Object();
        testQueue.enqueue(first);
        Object second = new Object();
        testQueue.enqueue(second);

        assertEquals(first, testQueue.dequeue());

        Object third = new Object();
        testQueue.enqueue(third);
        assertEquals(second, testQueue.dequeue());
        assertEquals(third, testQueue.dequeue());

        Object fourth = new Object();
        testQueue.enqueue(fourth);
        assertEquals(fourth, testQueue.dequeue());

        try {
            testQueue.dequeue();
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void testSingle() {
        Object element = new Object();
        testQueue.enqueue(element);
        assertEquals(1, testQueue.size());
        assertEquals(element, testQueue.dequeue());
        assertEquals(0, testQueue.size());
    }

    @Test
    public void testEmptyIterator() {
        Iterator<Object> iterator = testQueue.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIterator() {
        Object first = new Object();
        testQueue.enqueue(first);
        Object second = new Object();
        testQueue.enqueue(second);

        Iterator<Object> iterator = testQueue.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(first, iterator.next());
        assertEquals(second, iterator.next());

        testQueue.dequeue();
        Object third = new Object();
        testQueue.enqueue(third);
        assertEquals(third, iterator.next());

        try {
            iterator.next();
            fail();
        } catch (NoSuchElementException e) {
        }
    }

    @Test
    public void testDequeueWhileIterating() {
        Object first = new Object();
        testQueue.enqueue(first);
        Object second = new Object();
        testQueue.enqueue(second);

        Iterator<Object> iterator = testQueue.iterator();
        assertEquals(first, iterator.next());
        testQueue.dequeue();
        try {
            iterator.next();
            fail();
        } catch (NoSuchElementException e) {
        }
    }
}