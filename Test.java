import java.util.Locale;
import java.util.Scanner;

public class Test {
    public static void main(String[] args){
        String str = "ääö --";
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        System.out.println("input: " + input);
        input = input.toLowerCase(Locale.GERMAN);
        System.out.println("input2: " + input);
        System.out.println("boolean: " + input.contains(str));
    }
}
