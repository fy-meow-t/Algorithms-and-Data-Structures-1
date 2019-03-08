package comp3506.assn1.adts;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class MyBoundedCubeTest {

    @Test
    public void testIllegalArg() {
        // Dimensions should be positive
        try {
            new BoundedCube<>(0, 3, 5);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            new BoundedCube<>(6, -1, 7);
            fail();
        } catch (IllegalArgumentException e) {
        }
        try {
            new BoundedCube<>(10, 4, 0);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testIllegalAdd() {
        Cube<Object> testCube = new BoundedCube<>(3, 4, 5);
        Object element = new Object();
        try {
            testCube.add(2, 0, 4, element);
        } catch (IndexOutOfBoundsException e) {
            fail();
        }
        try {
            testCube.add(3, 3, 3, element);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.add(2, 4, 3, element);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.add(2, 3, 5, element);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.add(6, 9, 10, element);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.add(-1, 3, 3, element);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.add(2, -1, 3, element);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.add(1, 2, -3, element);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void testAddSingle() {
        Cube<Object> testCube = new BoundedCube<>(6, 7, 8);
        Object first = new Object();
        testCube.add(1, 2, 3, first);
        assertEquals(first, testCube.get(1, 2, 3));

        Object second = new Object();
        testCube.add(1, 2, 7, second);
        Object third = new Object();
        testCube.add(1, 1, 3, third);
        testCube.add(5, 2, 3, third);

        assertEquals(second, testCube.get(1, 2, 7));
        assertEquals(third, testCube.get(1, 1, 3));
        assertEquals(third, testCube.get(5, 2, 3));
        assertEquals(1, testCube.getAll(5, 2, 3).size());
    }

    @Test
    public void testMultiple() {
        Cube<Object> testCube = new BoundedCube<>(6, 7, 8);
        Object first = new Object();
        testCube.add(3, 3, 5, first);
        Object second = new Object();
        testCube.add(1, 3, 5, second);
        Object third = new Object();
        testCube.add(1, 1, 5, third);
        Object fourth = new Object();
        testCube.add(1, 3, 5, fourth);
        testCube.add(1, 3, 5, second);

        assertFalse(testCube.isMultipleElementsAt(1, 1, 5));
        assertFalse(testCube.isMultipleElementsAt(3, 3, 5));
        assertTrue(testCube.isMultipleElementsAt(1, 3, 5));
        assertEquals(3, testCube.getAll(1, 3, 5).size());
        assertEquals(second, testCube.get(1, 3, 5));

        IterableQueue<Object> queue = testCube.getAll(1, 3, 5);
        Iterator<Object> iterator = queue.iterator();
        assertEquals(second, iterator.next());
        assertEquals(fourth, iterator.next());
        assertEquals(second, iterator.next());
        assertEquals(3, queue.size());
    }

    @Test
    public void testIllegalGet() {
        Cube<Object> testCube = new BoundedCube<>(3, 4, 5);
        try {
            testCube.get(2, 0, 4);
        } catch (IndexOutOfBoundsException e) {
            fail();
        }
        try {
            testCube.get(3, 3, 3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.get(2, 4, 3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.get(2, 3, 5);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.get(6, 9, 10);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.get(-1, 3, 3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.get(2, -1, 3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.get(1, 2, -3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void testEmpty() {
        Cube<Object> testCube = new BoundedCube<>(6, 7, 8);
        assertNull(testCube.getAll(1, 2, 3));
        assertNull(testCube.get(1, 2, 3));
        assertFalse(testCube.isMultipleElementsAt(1, 2, 3));

        Object first = new Object();
        testCube.add(3, 1, 2, first);

        assertNull(testCube.get(1, 1, 2));
        assertNull(testCube.getAll(1, 1, 2));
        assertFalse(testCube.isMultipleElementsAt(1, 1, 2));

        assertNull(testCube.get(3, 2, 2));
        assertNull(testCube.getAll(3, 2, 2));
        assertFalse(testCube.isMultipleElementsAt(3, 2, 2));

        assertNull(testCube.get(3, 1, 5));
        assertNull(testCube.getAll(3, 1, 5));
        assertFalse(testCube.isMultipleElementsAt(3, 1, 5));
    }

    @Test
    public void testIllegalGetAll() {
        Cube<Object> testCube = new BoundedCube<>(3, 4, 5);
        try {
            testCube.getAll(2, 0, 4);
        } catch (IndexOutOfBoundsException e) {
            fail();
        }
        try {
            testCube.getAll(3, 3, 3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.getAll(2, 4, 3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.getAll(2, 3, 5);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.getAll(6, 9, 10);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.getAll(-1, 3, 3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.getAll(2, -1, 3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.getAll(1, 2, -3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void testIllegalIsMultipleElementsAt() {
        Cube<Object> testCube = new BoundedCube<>(3, 4, 5);
        try {
            testCube.isMultipleElementsAt(2, 0, 4);
        } catch (IndexOutOfBoundsException e) {
            fail();
        }
        try {
            testCube.isMultipleElementsAt(3, 3, 3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.isMultipleElementsAt(2, 4, 3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.isMultipleElementsAt(2, 3, 5);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.isMultipleElementsAt(6, 9, 10);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.isMultipleElementsAt(-1, 3, 3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.isMultipleElementsAt(2, -1, 3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.isMultipleElementsAt(1, 2, -3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void testRemove() {
        Cube<Object> testCube = new BoundedCube<>(6, 7, 8);
        Object first = new Object();
        assertFalse(testCube.remove(1, 2, 3, first));

        testCube.add(1, 2, 3, first);
        assertFalse(testCube.remove(1, 2, 4, first));
        assertFalse(testCube.remove(1, 5, 3, first));
        assertFalse(testCube.remove(2, 2, 3, first));

        testCube.add(3, 2, 1, first);
        assertTrue(testCube.remove(3, 2, 1, first));

        Object second = new Object();
        assertFalse(testCube.remove(1, 2, 3, second));
        testCube.add(1, 2, 3, second);
        assertTrue(testCube.isMultipleElementsAt(1, 2, 3));
        testCube.add(1, 2, 3, second);
        assertEquals(3, testCube.getAll(1, 2, 3).size());

        assertTrue(testCube.remove(1, 2, 3, second));
        assertEquals(2, testCube.getAll(1, 2, 3).size());
        assertEquals(first, testCube.get(1, 2, 3));
        assertTrue(testCube.remove(1, 2, 3, first));
        assertFalse(testCube.remove(1, 2, 3, first));
        assertFalse(testCube.isMultipleElementsAt(1, 2, 3));

        assertTrue(testCube.remove(1 ,2, 3, second));
        assertFalse(testCube.remove(1, 2, 3, second));
        assertNull(testCube.get(1, 2, 3));
        assertNull(testCube.getAll(1, 2, 3));
    }

    @Test
    public void testIllegalRemove() {
        Cube<Object> testCube = new BoundedCube<>(3, 4, 5);
        Object element = new Object();
        try {
            testCube.remove(2, 0, 4, element);
        } catch (IndexOutOfBoundsException e) {
            fail();
        }
        try {
            testCube.remove(3, 3, 3, element);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.remove(2, 4, 3, element);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.remove(2, 3, 5, element);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.remove(6, 9, 10, element);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.remove(-1, 3, 3, element);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.remove(2, -1, 3, element);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.remove(1, 2, -3, element);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void removeAll() {
        Cube<Object> testCube = new BoundedCube<>(6, 7, 8);
        Object first = new Object();
        testCube.removeAll(3, 1, 2); // Nothing happens
        testCube.add(3, 1, 2, first);
        testCube.add(3, 1, 2, first);
        Object second = new Object();
        testCube.add(3, 1, 2, second);
        testCube.add(5, 6, 7, second);

        // Nothing happens
        testCube.removeAll(3, 1, 3);
        testCube.removeAll(3, 5, 2);
        testCube.removeAll(1, 1, 2);

        assertEquals(3, testCube.getAll(3, 1, 2).size());
        testCube.removeAll(3, 1, 2);
        assertNull(testCube.get(3, 1, 2));
        assertNull(testCube.getAll(3, 1, 2));
        testCube.removeAll(5, 6, 7);
        assertNull(testCube.get(5, 6, 7));
    }

    @Test
    public void testIllegalRemoveAll() {
        Cube<Object> testCube = new BoundedCube<>(3, 4, 5);
        try {
            testCube.removeAll(2, 0, 4);
        } catch (IndexOutOfBoundsException e) {
            fail();
        }
        try {
            testCube.removeAll(3, 3, 3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.removeAll(2, 4, 3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.removeAll(2, 3, 5);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.removeAll(6, 9, 10);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.removeAll(-1, 3, 3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.removeAll(2, -1, 3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
        try {
            testCube.removeAll(1, 2, -3);
            fail();
        } catch (IndexOutOfBoundsException e) {
        }
    }

    @Test
    public void clear() {
        Cube<Object> testCube = new BoundedCube<>(6, 7, 8);
        Object first = new Object();
        Object second = new Object();
        testCube.clear(); // Nothing happens

        testCube.add(1, 2, 3, first);
        testCube.add(1, 2, 3, second);
        testCube.add(1, 2, 3, first);
        testCube.add(3, 2, 1, second);
        testCube.add(3, 2, 1, first);

        assertEquals(first, testCube.get(1, 2, 3));
        assertEquals(second, testCube.get(3, 2, 1));

        testCube.clear();
        assertNull(testCube.get(1, 2, 3));
        assertNull(testCube.get(3, 2, 1));
        assertNull(testCube.getAll(1, 2, 3));
        assertNull(testCube.getAll(3, 2, 1));

        testCube.add(1, 2, 3, first);
        assertEquals(first, testCube.get(1, 2, 3));
    }
}