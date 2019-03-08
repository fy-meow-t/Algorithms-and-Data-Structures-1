package comp3506.assn1.adts;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A LinkedList-based queue with ability to iterate over all the elements in the queue.
 *
 * Memory usage: O(n) where n is the number of the elements in the queue
 *
 * @author Feiyue Tao 44734046
 *
 * @param <T> The type of element held in the data structure.
 */
public class TraversableQueue<T> implements IterableQueue<T> {

    private int size;
    private int maxSize;
    private ListNode head;
    private ListNode tail;

    /**
     * Constructor for TraversableQueue.
     *
     * Run-time: O(1)
     */
    public TraversableQueue() {
        size = 0;
        // Australian airspace will contain a maximum of 20,000 aircraft at any one time
        maxSize = 20000;
        head = tail = null;
    }

    /**
     * Add a new element to the end of the queue.
     *
     * Run-time: O(1)
     *
     * @param element The element to be added to the queue.
     * @throws IllegalStateException Queue cannot accept a new element (e.g. queue space is full).
     */
    @Override
    public void enqueue(T element) throws IllegalStateException {
        if (size() >= maxSize) {
            throw new IllegalStateException();
        }
        ListNode node = new ListNode(element, null, null);
        if (size() == 0) {
            head = node;
        } else {
            tail.setNext(node);
            node.setPre(tail);
        }
        tail = node;
        size++;
    }

    /**
     * Remove and return the element at the head of the queue.
     *
     * Run-time: O(1)
     *
     * @return Element at that was at the head of the queue.
     * @throws IndexOutOfBoundsException Queue is empty and nothing can be dequeued.
     */
    @Override
    public T dequeue() throws IndexOutOfBoundsException {
        if (size() == 0) {
            throw new IndexOutOfBoundsException();
        }
        @SuppressWarnings("unchecked")
		T result = (T)head.getElement();
        ListNode temp = head.getNext();
        head.setNext(null);
        if (temp != null) {
            temp.setPre(null);
        } else {
            tail = null;
        }
        head = temp;
        size--;
        return result;
    }

    /**
     * Run-time: O(1)
     *
     * @return Number of elements in the queue.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Remove a node from the linked queue
     *
     * @param node
     */
    void remove(ListNode node) {
        if (node.getPre() == null) {
            head = node.getNext();
        }
        if (node.getNext() == null) {
            tail = node.getPre();
        }
        node.remove();
        size--;
    }

    /**
     * @return The head of the queue.
     */
    ListNode getHead() {
        return head;
    }

    /**
     * Empty the queue.
     */
    void reset() {
        head = tail = null;
    }

    /**
     * Run-time: O(1)
     *
     * @return An iterator that can iterate over and access
     *      all the elements in the queue from the front to the end
     */
    @Override
    public Iterator<T> iterator() {
        return new QueueIterator(head);
    }

    /**
     * The iterator
     */
    private class QueueIterator implements Iterator<T> {

        private ListNode start;

        /**
         * Constructor
         *
         * @param head The head of the queue
         */
        QueueIterator(ListNode head) {
            start = new ListNode(null, null, head);
        }

        /**
         * Run-time: O(1)
         *
         * @return True if there is more elements to iterate over; false if it reaches the end.
         */
        public boolean hasNext() {
            return start.hasNext();
        }

        /**
         * Run-time: O(1)
         *
         * @return The next element
         * @throws NoSuchElementException The iterator reaches the end
         */
        @SuppressWarnings("unchecked")
		public T next() throws NoSuchElementException {
            if (!start.hasNext()) {
                throw new NoSuchElementException();
            }
            start = start.getNext();
            return (T)start.getElement();
        }
    }
}

/**
 * Design justification:
 *
 * Memory usage: O(n)
 *      In the context of the simulation, the linked list in the queue contains at least 0 and at most 20,000 nodes.
 *      To minimize memory usage, the number of the nodes is equal to the number of elements (aircrafts).
 *      The dynamic linked list could grow when having more elements and reduce in size when having less.
 *
 *      An alternative method is to use arrays. The array needs to be in size of 20,000 to be able to carry all elements.
 *      However, aircrafts are meant to be separated by 1 km and they would fly normally in most cases,
 *          which means the queue would usually only have a small number of elements.
 *      Therefore, using a large fixed-sized array may waste a lot of memory.
 *
 * Run-time:
 *      The efficiency of all methods in this class is O(1).
 *      The queue only deals with one node (usually the head or the end) and its adjacent nodes each time.
 *
 *      The run-time efficiency of accessing to an element would be O(n) where n is the number of elements.
 *      For an array with direct indexes, it would be O(1) which is more efficient.
 *      As mentioned above, in most of the time, the queue would only have a few elements and thus a short iterating time.
 *      Hence saving memory seems to be more important.
 */
