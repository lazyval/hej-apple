import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class CookieSelection {
    private final static String GIMME_COOKIE = "#";
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        MedianHeap holdingArea = new MedianHeap();

        while (io.hasMoreTokens()) {
            String word = io.getWord();
            try {
                int cookie = Integer.parseInt(word);
                holdingArea.push(cookie);
            } catch (NumberFormatException ex) {
                if (!word.equals(GIMME_COOKIE))
                    throw new IllegalArgumentException("Expected gimme cookie command (#), but got :" + word);
                int medianCookie = holdingArea.pop();
                io.println(medianCookie);
            }
        }

        io.close();
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


class Kattio extends PrintWriter {
    public Kattio(InputStream i, OutputStream o) {
        super(new BufferedOutputStream(o));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public boolean hasMoreTokens() {
        return peekToken() != null;
    }

    public String getWord() {
        return nextToken();
    }

    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    private String peekToken() {
        if (token == null)
            try {
                while (st == null || !st.hasMoreTokens()) {
                    line = r.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                }
                token = st.nextToken();
            } catch (IOException e) { }
        return token;
    }

    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }
}
