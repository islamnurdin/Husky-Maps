package minpq;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * {@link PriorityQueue} implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class HeapMinPQ<E> implements MinPQ<E> {
    /**
     * {@link PriorityQueue} storing {@link PriorityNode} objects representing each element-priority pair.
     */
    private final PriorityQueue<PriorityNode<E>> pq;

    /**
     * Constructs an empty instance.
     */
    public HeapMinPQ() {
        pq = new PriorityQueue<>(Comparator.comparingDouble(PriorityNode::getPriority));
    }

    @Override
    public void add(E element, double priority) {
        pq.add(new PriorityNode<>(element, priority)); // Add a new element with its priority to the priority queue
    }

    @Override
    public boolean contains(E element) {
        for (PriorityNode<E> node : pq) {
            if (node.getElement().equals(element)) {
                return true; // If the element is found in the priority queue, return true
            }
        }
        return false; // If the element is not found in the priority queue, return false
    }

    @Override
    public E peekMin() {
        PriorityNode<E> node = pq.peek(); // Get the element with the minimum priority from the priority queue
        if (node == null) {
            return null; // If the priority queue is empty, return null
        }
        return node.getElement(); // Return the element with the minimum priority
    }

    @Override
    public E removeMin() {
        PriorityNode<E> node = pq.poll(); // Remove and return the element with the minimum priority from the priority queue
        if (node == null) {
            return null; // If the priority queue is empty, return null
        }
        return node.getElement(); // Return the removed element
    }

    @Override
    public void changePriority(E element, double priority) {
        PriorityQueue<PriorityNode<E>> updatedPQ = new PriorityQueue<>(Comparator.comparingDouble(PriorityNode::getPriority)); // Create a new priority queue to store the updated elements
        boolean found = false;
        while (!pq.isEmpty()) {
            PriorityNode<E> node = pq.poll(); // Remove an element from the original priority queue
            if (node.getElement().equals(element)) {
                updatedPQ.add(new PriorityNode<>(element, priority)); // If the element is found, add the updated element to the new priority queue
                found = true;
            } else {
                updatedPQ.add(node); // If the element is not the one being updated, add it to the new priority queue
            }
        }
        if (!found) {
            throw new NoSuchElementException("PQ does not contain " + element); // If the element is not found, throw an exception
        }
        pq.addAll(updatedPQ); // Add all the updated elements back to the original priority queue
    }

    @Override
    public int size() {
        return pq.size(); // Return the size of the priority queue
    }
}
