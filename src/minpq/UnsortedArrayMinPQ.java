package minpq;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class UnsortedArrayMinPQ<E> implements MinPQ<E> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the element-priority pairs in no specific order.
     */
    private final List<PriorityNode<E>> elements;

    /**
     * Constructs an empty instance.
     */
    public UnsortedArrayMinPQ() {
        elements = new ArrayList<>();
    }

    @Override
    public void add(E element, double priority) {
        elements.add(new PriorityNode<>(element, priority)); // Add a new element with its priority to the list
    }

    @Override
    public boolean contains(E element) {
        for (PriorityNode<E> node : elements) {
            if (node.getElement().equals(element)) {
                return true; // If the element is found in the list, return true
            }
        }
        return false; // If the element is not found in the list, return false
    }

    @Override
    public E peekMin() {
        PriorityNode<E> minNode = elements.get(0); // Assume the first element is the minimum
        for (PriorityNode<E> node : elements) {
            if (node.getPriority() < minNode.getPriority()) {
                minNode = node; // If a lower priority element is found, update the minimum node
            }
        }
        return minNode.getElement(); // Return the element with the minimum priority
    }

    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty"); // If the list is empty, throw an exception
        }
        PriorityNode<E> minNode = elements.get(0); // Assume the first element is the minimum
        int minIndex = 0;
        for (int i = 1; i < elements.size(); i++) {
            if (elements.get(i).getPriority() < minNode.getPriority()) {
                minNode = elements.get(i); // If a lower priority element is found, update the minimum node and index
                minIndex = i;
            }
        }
        elements.remove(minIndex); // Remove the element with the minimum priority from the list
        return minNode.getElement(); // Return the removed element
    }

    @Override
    public void changePriority(E element, double priority) {
        boolean found = false;
        for (PriorityNode<E> node : elements) {
            if (node.getElement().equals(element)) {
                node.setPriority(priority); // If the element is found, update its priority
                found = true;
                break;
            }
        }
        if (!found) {
            throw new NoSuchElementException("PQ does not contain " + element); // If the element is not found, throw an exception
        }
    }

    @Override
    public int size() {
        return elements.size(); // Return the size of the list
    }
}
