package models;

import java.time.LocalDateTime;

public class Expression {
    private String id;
    private String value;
    private ExpressionType type;
    private Expression leftChild;
    private Expression rightChild;

    public Expression() {
    }

    public Expression(String id, String value, ExpressionType type, Expression leftChild, Expression rightChild) {
        this.id = id;
        this.value = value;
        this.type = type;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ExpressionType getType() {
        return type;
    }

    public void setType(ExpressionType type) {
        this.type = type;
    }

    public Expression getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Expression leftChild) {
        this.leftChild = leftChild;
    }

    public Expression getRightChild() {
        return rightChild;
    }

    public void setRightChild(Expression rightChild) {
        this.rightChild = rightChild;
    }
}
