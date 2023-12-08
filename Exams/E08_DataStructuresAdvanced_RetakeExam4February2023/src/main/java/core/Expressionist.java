package core;

import models.Expression;

import java.time.LocalDateTime;

public interface Expressionist {
    void addExpression(Expression expression);

    void addExpression(Expression expression, String parentId);

    boolean contains(Expression expression);

    int size();

    Expression getExpression(String expressionId);

    void removeExpression(String expressionId);

    String evaluate();
}
