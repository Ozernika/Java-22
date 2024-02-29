import java.util.Scanner;
import java.util.Stack;

public class RPNCalculator {
    public static double evaluateRPN(String[] expression) {
        Stack<Double> stack = new Stack<>();

        for (String token : expression) {
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(token));
            } else if (token.matches("[+\\-*/]")) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Недостаточно операндов для оператора " + token);
                }

                double operand2 = stack.pop();
                double operand1 = stack.pop();

                switch (token) {
                    case "+":
                        stack.push(operand1 + operand2);
                        break;
                    case "-":
                        stack.push(operand1 - operand2);
                        break;
                    case "*":
                        stack.push(operand1 * operand2);
                        break;
                    case "/":
                        if (operand2 == 0) {
                            throw new ArithmeticException("Деление на ноль");
                        }
                        stack.push(operand1 / operand2);
                        break;
                }
            } else {
                throw new IllegalArgumentException("Недопустимый токен: " + token);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Неверное выражение");
        }

        return stack.pop();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите выражение в обратной польской нотации: ");
        String expression = scanner.nextLine();
        String[] tokens = expression.split("\\s+");

        try {
            double result = evaluateRPN(tokens);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
	/*вычисления завершены*/
    }
}
