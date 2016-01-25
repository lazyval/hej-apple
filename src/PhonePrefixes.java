import java.util.Arrays;

public class PhonePrefixes {
    public static void main(String[] args) {

    }

    public static class Problem {
        public static boolean solve(String[] lines) {
            Arrays.sort(lines);

            for (int i = 0; i < lines.length - 1; i++) {
                String possiblePrefix = lines[0];
                String word = lines[1];
                if (word.startsWith(possiblePrefix)) return false;
            }
            return true;
        }
    }
}
