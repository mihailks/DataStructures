package core;

import models.Expression;
import models.ExpressionType;

import java.util.LinkedHashMap;
import java.util.Map;

public class ExpressionistImpl implements Expressionist {
    //TODO: not at 100%.  84 / 100
    Map<String, Expression> expressionsById = new LinkedHashMap<>();

    @Override
    public void addExpression(Expression expression) {
        if (expressionsById.isEmpty()) {
            expressionsById.put(expression.getId(), expression);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void addExpression(Expression expression, String parentId) {
        Expression parent = expressionsById.get(parentId);
        if (parent == null) {
            throw new IllegalArgumentException();
        }
        if (parent.getLeftChild() == null) {
            parent.setLeftChild(expression);
        } else if (parent.getRightChild() == null) {
            parent.setRightChild(expression);
        } else {
            throw new IllegalArgumentException();
        }
        expressionsById.put(expression.getId(), expression);
    }

    @Override
    public boolean contains(Expression expression) {
        return expressionsById.containsKey(expression.getId());
    }

    @Override
    public int size() {
        return expressionsById.size();
    }

    @Override
    public Expression getExpression(String expressionId) {
        Expression expression = expressionsById.get(expressionId);
        if (expression == null) {
            throw new IllegalArgumentException();
        }
        return expression;
    }

    @Override
    public void removeExpression(String expressionId) {
        Expression expression = expressionsById.get(expressionId);
        if (expression == null) {
            throw new IllegalArgumentException();
        }
        removeExpressionFromParent(expression);
        expressionsById.remove(expressionId);
    }

    private void removeExpressionFromParent(Expression expression) {
        Expression parent = getParentExpression(expression);
        if (parent != null) {
            if (parent.getLeftChild() == expression) {
                parent.setLeftChild(expression.getRightChild());
            } else if (parent.getRightChild() == expression) {
                parent.setRightChild(null);
            }
        }
    }

    private Expression getParentExpression(Expression expression) {
        for (Expression exp : expressionsById.values()) {
            if (exp.getLeftChild() == expression || exp.getRightChild() == expression) {
                return exp;
            }
        }
        return null;
    }

    @Override
    public String evaluate() {
        Expression root = expressionsById.values().iterator().next();
        if (root == null) {
            return "";
        }
        return evaluateExpression(root);
    }

    private String evaluateExpression(Expression expression) {

        StringBuilder sb = new StringBuilder();
        if (expression.getType().equals(ExpressionType.VALUE)) {
            sb.append(expression.getValue());
        } else if (expression.getType().equals(ExpressionType.OPERATOR)) {
            sb.append("(");
            sb.append(evaluateExpression(expression.getLeftChild()));
            sb.append(" ");
            sb.append(expression.getValue());
            sb.append(" ");
            sb.append(evaluateExpression(expression.getRightChild()));
            sb.append(")");
        }
        return sb.toString();
    }
}
