import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.*;

public class App {
    public static void main(String[] args) {
        try {
            calculator();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("The end");
        }
    }

    public static void calculator() throws IOException, Exception {
        int num_left = 0, num_right = 0;
        String operator = "";

        System.out.println("Enter operation");
        Scanner in = new Scanner(System.in);
        String string = in.nextLine();

        in.close();

        String regex_arab = "(^\\d{1,2})(?: *)?([*+-/]){1}(?: *)?(\\d{1,2}$)";
        String regex_roma = "(^I{1,3}|IV|V|VI|VII|VIII|IX|X)(?: *)?([*+-/]){1}(?: *)?(I{1,3}|IV|V|VI|VII|VIII|IX|X)$";

        Pattern pattern_arab = Pattern.compile(regex_arab);
        Pattern pattern_roma = Pattern.compile(regex_roma);

        Matcher match_arab = pattern_arab.matcher(string);
        Matcher match_roma = pattern_roma.matcher(string);

        boolean check_arab = Pattern.matches(regex_arab, string);
        boolean check_roma = Pattern.matches(regex_roma, string);

        if (!check_arab && !check_roma) {
            throw new IOException("Invalid input format");
        }

        if (match_arab.find()) {
            num_left = Integer.parseInt(match_arab.group(1));
            operator = match_arab.group(2);
            num_right = Integer.parseInt(match_arab.group(3));
            if (num_left >= 1 && num_left <= 10 && num_right >= 1 && num_right <= 10) {
                int answer = operateWithNumbers(num_left, num_right, operator);
                System.out.println(answer);
            } else {
                throw new IOException("Invalid input format");
            }
        } else if (match_roma.find()) {
            num_left = romaToArab(match_roma.group(1));
            operator = match_roma.group(2);
            num_right = romaToArab(match_roma.group(3));
            if (num_left >= 1 && num_left <= 10 && num_right >= 1 && num_right <= 10) {
                int answer = operateWithNumbers(num_left, num_right, operator);
                if (answer < 1) {
                    throw new Exception("Answer less than 1");
                }
                String result_roma_string = arabToRoma(answer);
                System.out.println(result_roma_string);
            } else {
                throw new IOException("Invalid input format");
            }
        } else {
            throw new IOException("Invalid input format");
        }
    }

    static int operateWithNumbers(int num_left, int num_right, String operator) {
        int result = 0;
        switch (operator) {
            case "+":
                result = num_left + num_right;
                break;
            case "-":
                result = num_left - num_right;
                break;
            case "*":
                result = num_left * num_right;
                break;
            case "/":
                result = num_left / num_right;
        }
        return result;
    }

    static int romaToArab(String s) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        int result = 0;
        int i = 1;
        if (s.length() == 1) {
            return map.get(s.charAt(0));
        }
        while (i <= s.length()) {
            if (s.charAt(i - 1) == 'I' && (s.charAt(i) == 'V' || s.charAt(i) == 'X')) {
                result += map.get(s.charAt(i)) - 1;
                i += 2;
            } else if (s.charAt(i - 1) == 'X' && (s.charAt(i) == 'L' || s.charAt(i) == 'C')) {
                result += map.get(s.charAt(i)) - 10;
                i += 2;
            } else {
                result += map.get(s.charAt(i - 1));
                i += 1;
                if (i == s.length()) {
                    result += map.get(s.charAt(i - 1));
                    break;
                }
            }
        }
        return result;
    }

    static String arabToRoma(int num) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "I");
        map.put(5, "V");
        map.put(10, "X");
        map.put(50, "L");
        map.put(100, "C");

        String result_roma_string = "";
        int tens = num / 10;
        int units = num % 10;

        if (tens >= 1 && tens <= 3) {
            result_roma_string += "X".repeat(tens);
        } else if (tens == 4) {
            result_roma_string += map.get(10) + map.get(50);
        } else if (tens == 5) {
            result_roma_string += map.get(5);
        } else if (tens >= 6 && tens <= 8) {
            result_roma_string += "L" + "X".repeat(tens - 5);
        } else if (tens == 9) {
            result_roma_string += map.get(10) + map.get(100);
        } else if (tens == 10) {
            result_roma_string += map.get(100);
        }

        if (units >= 1 && units <= 3) {
            result_roma_string += "I".repeat(units);
        } else if (units == 4) {
            result_roma_string += map.get(1) + map.get(5);
        } else if (units == 5) {
            result_roma_string += map.get(5);
        } else if (units >= 6 && units <= 8) {
            result_roma_string += "V" + "I".repeat(units - 5);
        } else if (units == 9) {
            result_roma_string += map.get(1) + map.get(10);
        }

        return result_roma_string;
    }
}
