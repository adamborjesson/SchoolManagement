import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {

static Scanner sc = new Scanner(System.in);
    public String stringInput() {

        String s = sc.nextLine();
        return s;

    }

    public int intInput() {

                int i = sc.nextInt();
                sc.nextLine();

                return i;

        }


    }

