import java.util.Scanner;

public class CookieSelection {
    private final static String GIMME_COOKIE = "#";
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        MedianHeap holdingArea = new MedianHeap();

        while(input.hasNextLine()) {
            if (input.hasNextInt()) {
                int cookie = Integer.parseInt(input.nextLine());
                holdingArea.push(cookie);
            } else {
                String token = input.nextLine();
                if (! token.equals(GIMME_COOKIE)) throw new IllegalArgumentException("Expected gimme cookie command (#), but got :" + token);
                int medianCookie = holdingArea.pop();
                System.out.println(medianCookie);
            }
        }
    }
}
