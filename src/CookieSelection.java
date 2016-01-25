import java.util.Collections;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class CookieSelection {
    private final static String GIMME_COOKIE = "#";
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        MedianHeap holdingArea = new MedianHeap();

        while (input.hasNextLine()) {
            if (input.hasNextInt()) {
                int cookie = Integer.parseInt(input.nextLine());
                holdingArea.push(cookie);
            } else {
                String token = input.nextLine();
                if (!token.equals(GIMME_COOKIE))
                    throw new IllegalArgumentException("Expected gimme cookie command (#), but got :" + token);
                int medianCookie = holdingArea.pop();
                System.out.println(medianCookie);
            }
        }
    }
}

class MedianHeap {
    private final static int DEFAULT_CAPACITY = 11;
    private PriorityQueue<Integer> leftHalf = mkMaxHeap();
    private PriorityQueue<Integer> rightHalf = mkMinHeap();

    public int pop() {
        if (isEmpty()) throw new NoSuchElementException();

        if (leftHalf.size() == rightHalf.size()) {
            return rightHalf.poll();
        } else {
            PriorityQueue<Integer> biggest = leftHalf.size() > rightHalf.size() ? leftHalf : rightHalf;
            return biggest.poll();
        }
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
