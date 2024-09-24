package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link MinPQ} interface.
 *
 * @param <E> the type of elements in this priority queue.
 * @see MinPQ
 */
public class OptimizedHeapMinPQ<E> implements MinPQ<E> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the heap of element-priority pairs.
     */
    private final List<PriorityNode<E>> elements;
    /**
     * {@link Map} of each element to its associated index in the {@code elements} heap.
     */
    private final Map<E, Integer> elementsToIndex;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        elements = new ArrayList<>();
        elementsToIndex = new HashMap<>();
    }

    @Override
    public void add(E element, double priority) {
        if (contains(element)) {
            throw new IllegalArgumentException("Already contains " + element);
        }
        // Create a new PriorityNode with the given element and priority
        PriorityNode<E> newNode = new PriorityNode<>(element, priority);
        // Add the new node to the end of the heap
        elements.add(newNode);
        int index = elements.size() - 1;
        // Update the index map with the new element's index in the heap
        elementsToIndex.put(element, index);
        // Move the new node up the heap to maintain the heap property
        swim(index);
    }

    @Override
    public boolean contains(E element) {
        // Check if the element is present in the index map
        return elementsToIndex.containsKey(element);
    }

    @Override
    public E peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // Return the element at the root of the heap (minimum element)
        return elements.get(0).getElement();
    }

    @Override
    public E removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // Remove the minimum element from the heap
        E minElement = elements.get(0).getElement();
        // Swap the minimum element with the last element in the heap
        swap(0, elements.size() - 1);
        // Remove the last element (minimum element) from the heap
        elements.remove(elements.size() - 1);
        // Remove the minimum element from the index map
        elementsToIndex.remove(minElement);
        // Move the element at the root down the heap to maintain the heap property
        sink(0);
        // Return the removed minimum element
        return minElement;
    }

    @Override
    public void changePriority(E element, double priority) {
        if (!contains(element)) {
            throw new NoSuchElementException("PQ does not contain " + element);
        }
        // Get the index of the element in the heap
        int index = elementsToIndex.get(element);
        // Get the old priority of the element
        double oldPriority = elements.get(index).getPriority();
        // Update the priority of the element in the heap
        elements.get(index).setPriority(priority);
        // If the new priority is lower, move the element up the heap
        if (priority < oldPriority) {
            swim(index);
        }
        // If the new priority is higher, move the element down the heap
        else if (priority > oldPriority) {
            sink(index);
        }
    }

    @Override
    public int size() {
        // Return the number of elements in the heap
        return elements.size();
    }

    // Helper method to move a node up the heap to maintain the heap property
    private void swim(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            // If the node is smaller than its parent, swap them
            if (less(index, parentIndex)) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    // Helper method to move a node down the heap to maintain the heap property
    private void sink(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int smallest = index;

        // Find the smallest child of the current node
        if (leftChild < elements.size() && less(leftChild, smallest)) {
            smallest = leftChild;
        }

        if (rightChild < elements.size() && less(rightChild, smallest)) {
            smallest = rightChild;
        }

        // If the smallest child is smaller than the current node, swap them and continue sinking
        if (smallest != index) {
            swap(index, smallest);
            sink(smallest);
        }
    }

    // Helper method to compare nodes based on their priorities
    private boolean less(int i, int j) {
        return elements.get(i).getPriority() < elements.get(j).getPriority();
    }

    // Helper method to swap two nodes in the heap and update the index map
    private void swap(int i, int j) {
        PriorityNode<E> temp = elements.get(i);
        elements.set(i, elements.get(j));
        elements.set(j, temp);

        // Update the index map with the new indices of the swapped elements
        elementsToIndex.put(elements.get(i).getElement(), i);
        elementsToIndex.put(elements.get(j).getElement(), j);
    }
}
