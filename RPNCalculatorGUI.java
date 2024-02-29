import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class RPNCalculatorGUI {
    private JTextField inputField;
    private JTextField resultField;

    public RPNCalculatorGUI() {
        JFrame frame = new JFrame("RPN Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLayout(new BorderLayout());

        inputField = new JTextField();
        resultField = new JTextField();
        resultField.setEditable(false);

        JPanel buttonPanel = createButtonPanel();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(inputField);
        panel.add(buttonPanel);
        panel.add(resultField);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "+", "=",
        };

        for (int i = 0; i < 16; ++i) {
            String label = buttonLabels[i];
            JButton button = new JButton(label);
            button.addActionListener(new ButtonActionListener());
            buttonPanel.add(button);
        }

        return buttonPanel;
    }

    private class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton) e.getSource();
            String buttonText = sourceButton.getText();

            if (buttonText.equals("=")) {
                calculate();
            } else {
                inputField.setText(inputField.getText() + buttonText);
            }
        }
    }

    private void calculate() {
        String expression = inputField.getText();
        String[] tokens = expression.split("\\s+");

        try {
            double result = evaluateRPN(tokens);
            resultField.setText("Результат: " + result);
        } catch (Exception ex) {
            resultField.setText("Ошибка: " + ex.getMessage());
        }
    }

    private double evaluateRPN(String[] expression) {
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RPNCalculatorGUI();
/* новый комментарий */
            }
        });
    }
}
