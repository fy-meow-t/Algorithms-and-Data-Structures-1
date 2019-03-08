package comp3506.assn1.adts;


import java.util.Iterator;

/**
 * A linked data structure.
 * Each cell in the structure can hold multiple items and represent a position which has at least one element.
 * A bounded cube has a specified maximum size in each dimension.
 * The root of each dimension is indexed from zero.
 *
 * Memory usage: O(n)
 * n is the total number of the elements in the cube (i.e. aircrafts in the airspace)
 *
 * @author Feiyue Tao 44734046
 *
 * @param <T> The type of element held in the data structure.
 */
public class BoundedCube<T> implements Cube<T> {

	private int length;
	private int breadth;
	private int height;
	private ListNode space;

	/**
	 * Constructor for BoundedCube.
     *
     * Run-time: O(1)
     *
	 * @param length  Maximum size in the 'x' dimension.
	 * @param breadth Maximum size in the 'y' dimension.
	 * @param height  Maximum size in the 'z' dimension.
	 * @throws IllegalArgumentException If provided dimension sizes are not positive.
	 */
	public BoundedCube(int length, int breadth, int height) throws IllegalArgumentException {
		if (length <=0 || breadth <=0 || height <=0) {
			throw new IllegalArgumentException();
		}
		this.length = length;
		this.breadth = breadth;
		this.height = height;
		space = null;
	}

    /**
     * Check whether the index is out of the bound.
     *
     * @param x X Coordinate of the position of the element.
     * @param y Y Coordinate of the position of the element.
     * @param z Z Coordinate of the position of the element.
     * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
     */
	private void checkBounds (int x, int y, int z) throws IndexOutOfBoundsException {
		if ( x < 0 || x >= length || y < 0 || y >= breadth || z < 0 || z >= height) {
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * Add an element at a fixed position.
     *
     * Run-time: O(n) - n is the number of the elements (aircrafts) in the cube.
	 *
	 * @param element The element to be added at the indicated position.
	 * @param x X Coordinate of the position of the element.
	 * @param y Y Coordinate of the position of the element.
	 * @param z Z Coordinate of the position of the element.
	 * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
	 */
	@Override
	public void add(int x, int y, int z, T element) throws IndexOutOfBoundsException {
		checkBounds(x, y, z);
		TraversableQueue<T> queue = (TraversableQueue<T>)getAll(x, y, z);
		// There is at least one element at that position. Add the new element at the end of its queue
		if (queue != null) {
		    queue.enqueue(element);
        } else {
		    // No element at that position. Create a new node then add the new element
            queue = new TraversableQueue<>();
            queue.enqueue(element);
			ListNode node = new ListNode(queue, null, space, x, y, z);
            if (space != null) {
            	space.setPre(node);
			}
			space = node;
        }
	}

	/**
	 * Return the 'oldest' element at the indicated position.
     *
     * Run-time: O(n) - n is the number of the elements (aircrafts) in the cube.
	 *
	 * @param x X Coordinate of the position of the element.
	 * @param y Y Coordinate of the position of the element.
	 * @param z Z Coordinate of the position of the element.
	 * @return 'Oldest' element at this position or null if no elements at the indicated position.
	 * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
	 */
	@Override
	public T get(int x, int y, int z) throws IndexOutOfBoundsException {
		checkBounds(x, y, z);
        TraversableQueue<T> queue = (TraversableQueue<T>)getAll(x, y, z);
        if (queue != null) {
            return queue.iterator().next();
        }
        return null;
	}

	/**
	 * Return all the elements at the indicated position.
     *
     * Run-time: O(n) - n is the number of the elements (aircrafts) in the cube
	 *
	 * @param x X Coordinate of the position of the element(s).
	 * @param y Y Coordinate of the position of the element(s).
	 * @param z Z Coordinate of the position of the element(s).
	 * @return An IterableQueue of all elements at this position or null if no elements at the indicated position.
	 * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IterableQueue<T> getAll(int x, int y, int z) throws IndexOutOfBoundsException {
		checkBounds(x, y, z);
        ListNode temp = space;
        while (temp != null) {
            if (temp.getX() == x && temp.getY() == y && temp.getZ() == z) {
               return (TraversableQueue<T>)temp.getElement();
            }
            temp = temp.getNext();
        }
		return null;
	}

	/**
	 * Indicates whether there are more than one elements at the indicated position.
     *
     * Run-time: O(n) - n is the number of the elements (aircrafts) in the cube.
	 *
	 * @param x X Coordinate of the position of the element(s).
	 * @param y Y Coordinate of the position of the element(s).
	 * @param z Z Coordinate of the position of the element(s).
	 * @return true if there are more than one elements at the indicated position, false otherwise.
	 * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
	 */
	@Override
	public boolean isMultipleElementsAt(int x, int y, int z) throws IndexOutOfBoundsException {
		checkBounds(x, y, z);
        TraversableQueue<T> queue = (TraversableQueue<T>)getAll(x, y, z);
        if (queue != null) {
            Iterator<T> iterator = queue.iterator();
            iterator.next();
            return iterator.hasNext();
        }
		return false;
	}

    /**
     * @param cell The cell to remove from the linked list
     */
	private void removeCell(ListNode cell) {
		if (cell.getPre() == null) {
			space = cell.getNext();
		}
		cell.remove();
	}

	/**
	 * Removes the specified element at the indicated position.
     *
     * Run-time: O(n) - n is the number of the elements (aircrafts) in the cube.
	 *
	 * @param element The element to be removed from the indicated position.
	 * @param x X Coordinate of the position.
	 * @param y Y Coordinate of the position.
	 * @param z Z Coordinate of the position.
	 * @return true if the element was removed from the indicated position, false otherwise.
	 * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(int x, int y, int z, T element) throws IndexOutOfBoundsException {
		checkBounds(x, y, z);
        ListNode temp = space;
        while (temp != null) {
            // Check the position
            if (temp.getX() == x && temp.getY() == y && temp.getZ() == z) {
                TraversableQueue<T> queue = (TraversableQueue<T>) temp.getElement();
                ListNode node = queue.getHead();
                while (node != null) {
                    // Check the element
                    if (node.getElement() == element) {
                        queue.remove(node);
                        if (queue.size() == 0) {
                            // The position becomes empty
                            removeCell(temp);
                        }
                        return true;
                    }
                    node = node.getNext();
                }
            }
            temp = temp.getNext();
        }
		return false;
	}

	/**
	 * Removes all elements at the indicated position.
     *
     * Run-time: O(n) - n is the number of the elements (aircrafts) in the cube.
	 *
	 * @param x X Coordinate of the position.
	 * @param y Y Coordinate of the position.
	 * @param z Z Coordinate of the position.
	 * @throws IndexOutOfBoundsException If x, y or z coordinates are out of bounds.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void removeAll(int x, int y, int z) throws IndexOutOfBoundsException {
		checkBounds(x, y, z);
        ListNode temp = space;
        while (temp != null) {
            if (temp.getX() == x && temp.getY() == y && temp.getZ() == z) {
                TraversableQueue<T> queue = (TraversableQueue<T>)temp.getElement();
                queue.reset();
                removeCell(temp);
            }
            temp = temp.getNext();
        }
	}

	/**
	 * Removes all elements stored in the cube.
     *
     * Run-time: O(1)
	 */
	@Override
	public void clear() {
        space = null;
	}
}

/**
 * Design justification:
 *
 * Memory usage:
 *      There would be from 0 to maximum 20,000 nodes in the linked list in the cube.
 *      Each node represents a 1 km^3 cell which has at least one aircraft in it.
 *      Two extreme cases:
 *          20,000 aircrafts in a single cell: the list would have one node whose queue contains 20,000 elements
 *          20,000 aircrafts in all different cells: the list would have 20,000 cells. Each cell's queue has one element
 *      When an occupied cell becomes empty, which means all aircrafts in it are removed,
 *          the cell will be removed from the list to save more space.
 *      The memory usage would be based on the number of elements (aircrafts) in the cube (airspace).
 *
 *      An alternative method is to use a 3D array.
 *      Since the array is fixed-sized and aircrafts may exist in any place within the cube,
 *          the array needs to represent the whole huge cube.
 *      Then the array would occupy much more space than the dynamic list which would not be longer than 20,000.
 *
 * Run-time:
 *      The efficiency of all public methods except the constructor and clear() is O(n)
 *      n is the number of aircrafts in the airspace.
 *      In the worst case, the coordinates of all cells (maximum 20,000) have to be checked when looking for an indicated position.
 *      A fixed-sized array may only cost O(1).
 *      However, there would be a very small number of occupied cells in the cube in this simulation.
 *      Therefore, the list would only have a few elements and a short iterating time.
 *      While in a 3D array, a large number of cells would be empty.
 *      Hence saving memory seems to be more important.
 *
 *      Another possible solution is to group the nodes based on the range of one dimension.
 *      For example, set a linked list where the first node represents cells whose z coordinates are between 0 and max/5
 *      The second node represents max/5 to 2*max/5, etc.
 *      When accessing to one cell, the program checks the range of z coordinate first instead of checking every single cell.
 *      So it can improve the run-time efficiency, but the data structure would be a bit more complicated and occupy more memory.
 */
