import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Collections;

public class MedianHeap {
    private final static int DEFAULT_CAPACITY = 11;
    private PriorityQueue<Integer> leftHalf = mkMaxHeap();
    private PriorityQueue<Integer> rightHalf = mkMinHeap();

    public int pop() {
        if (isEmpty()) throw new NoSuchElementException();

        int median = size() % 2 == 0
            ? rightHalf.poll()
            : leftHalf.poll();

        return median;
    }

    public void push(int x) {
        if (isEmpty()) {
            leftHalf.add(x);
        } else {
            if (leftHalf.peek() >= x) {
                leftHalf.add(x);
            } else {
                rightHalf.add(x);
            }
        }

        rebalance();
    }

    private boolean isEmpty() {
        return size() == 0;
    }

    private int size() {
        return leftHalf.size() + rightHalf.size();
    }

    private void rebalance() {
        boolean isRequired = Math.abs(leftHalf.size() - rightHalf.size()) > 1;
        if (isRequired) {
            if (leftHalf.size() > rightHalf.size()) {
                rightHalf.add(leftHalf.poll());
            } else {
                leftHalf.add(rightHalf.poll());
            }
        }
    }

    private static PriorityQueue<Integer> mkMinHeap() {
        return new PriorityQueue<Integer>(DEFAULT_CAPACITY, NATURAL_ORDER);
    }

    private static PriorityQueue<Integer> mkMaxHeap() {
        return new PriorityQueue<Integer>(DEFAULT_CAPACITY, Collections.reverseOrder(NATURAL_ORDER));
    }

    private final static Comparator<Integer> NATURAL_ORDER = new Comparator<Integer>() {
        @Override
        public int compare(Integer a, Integer b) {
            return a.compareTo(b);
        }
    };

    public String toString() {
        return leftHalf.toString() + rightHalf.toString();
    }
}
