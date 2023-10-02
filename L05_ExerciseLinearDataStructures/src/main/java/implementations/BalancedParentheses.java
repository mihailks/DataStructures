package implementations;

import interfaces.Deque;
import interfaces.Solvable;

public class BalancedParentheses implements Solvable {
    private String parentheses;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
    }

    @Override
    public Boolean solve() {

        String input = parentheses;

        Deque<Character> stack = new ArrayDeque<>();

        boolean isBalanced = false;

        for (char bracket : input.toCharArray()) {
            if (bracket == '(' || bracket == '[' || bracket == '{') {
                stack.push(bracket);
            } else if (bracket == ')' || bracket == ']' || bracket == '}') {
                if (stack.isEmpty()) {
                    isBalanced = false;
                    break;
                }

                char lastOpenBracket = stack.pop();

                if (lastOpenBracket == '(' && bracket == ')') {
                    isBalanced = true;
                } else if (lastOpenBracket == '[' && bracket == ']') {
                    isBalanced = true;
                } else if (lastOpenBracket == '{' && bracket == '}') {
                    isBalanced = true;
                } else {
                    isBalanced = false;
                    break;
                }
            }
        }
        return isBalanced;
    }
}
