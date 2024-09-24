package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> elements;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        elements = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // TODO: Replace with your code
        //elements.sort((a, b) -> a.toString().compareTo(b.toString()));
        //throw new UnsupportedOperationException("Not implemented yet");
        elements.addAll(terms); // Add all terms to the elements list before sorting
        // Sort the elements list lexicographically
        elements.sort((a, b) -> a.toString().compareTo(b.toString()));
    }

    @Override
    // TODO: Replace with your code
    // throw new UnsupportedOperationException("Not implemented yet");
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> matches = new ArrayList<>();
        int left = findLeftMostIndex(prefix.toString()); // Convert CharSequence to String
        if (left != -1) {
            int right = findRightMostIndex(prefix.toString()); // Convert CharSequence to String
            matches = new ArrayList<>(elements.subList(left, right + 1));
        }
        return matches;
    }
    private int findLeftMostIndex(String prefix) {
        int low = 0;
        int high = elements.size() - 1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            CharSequence current = elements.get(mid);
            if (current.toString().startsWith(prefix)) {
                result = mid;
                high = mid - 1;
            } else if (current.toString().compareTo(prefix) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }

    private int findRightMostIndex(String prefix) {
        int low = 0;
        int high = elements.size() - 1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            CharSequence current = elements.get(mid);
            if (current.toString().startsWith(prefix)) {
                result = mid;
                low = mid + 1;
            } else if (current.toString().compareTo(prefix) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }
}