import java.util.Arrays;
import java.util.Scanner;

public class PhonePrefixes {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int testCases = Integer.parseInt(input.nextLine());
        for (int testCase = 0; testCase < testCases; testCase++) {
            String[] numbers = readNumbers(input);

            String answer = Problem.solve(numbers) ? "YES" : "NO";
            System.out.println(answer);
        }
    }

    private static String[] readNumbers(Scanner input) {
        int numbersCount = Integer.parseInt(input.nextLine());
        String[] numbers = new String[numbersCount];
        for (int n = 0; n < numbersCount; n++) {
            numbers[n] = input.nextLine();
        }
        return numbers;
    }

    public static class Problem {
        public static boolean solve(String[] lines) {
            Arrays.sort(lines);

            for (int i = 0; i < lines.length - 1; i++) {
                String possiblePrefix = lines[i];
                String word = lines[i + 1];
                if (word.startsWith(possiblePrefix)) return false;
            }
            return true;
        }
    }
}
