package comp3506.assn1.adts;

/**
 * The node used in the linked list
 */
class ListNode {
    private Object element;
    private ListNode pre;
    private ListNode next;
    private int x;
    private int y;
    private int z;

    /**
     * Constructor for the nodes used in TraversableQueue().
     *
     * @param element The element to store
     * @param pre The previous node
     * @param next The next node
     */
    ListNode(Object element, ListNode pre, ListNode next) {
        this.element = element;
        this.pre = pre;
        this.next = next;
    }

    /**
     * Constructor for the nodes used in BoundedCube().
     *
     * @param element The element to store
     * @param pre The previous node
     * @param next The next node
     * @param x X Coordinate of the position
     * @param y Y Coordinate of the position
     * @param z Z Coordinate of the position
     */
    ListNode(Object element, ListNode pre, ListNode next, int x, int y, int z) {
        this.element = element;
        this.pre = pre;
        this.next = next;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * @param node Set the next node to be this node
     */
    void setNext(ListNode node) {
        next = node;
    }

    /**
     * @return The next node
     */
    ListNode getNext() {
        return next;
    }

    /**
     * @param node Set the previous node to be this node
     */
    void setPre(ListNode node) {
        pre = node;
    }

    /**
     * @return The previous node
     */
    ListNode getPre() {
        return pre;
    }

    /**
     * @return The element that the node contains
     */
    Object getElement() {
        return element;
    }

    /**
     * @return Whether it has the next node
     */
    boolean hasNext() {
        return (next != null);
    }

    /**
     * Remove the node from the linked list
     */
    void remove() {
        pre = getPre();
        next = getNext();
        if (pre != null) {
            pre.setNext(next);
        }
        if (next != null) {
            next.setPre(pre);
        }
        setNext(null);
        setPre(null);
    }

    /**
     * @return The X coordinate
     */
    int getX() {
        return x;
    }

    /**
     * @return The Y coordinate
     */
    int getY() {
        return y;
    }

    /**
     * @return The Z coordinate
     */
    int getZ() {
        return z;
    }

}
