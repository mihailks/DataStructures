package core;

import model.Message;
import shared.DataTransferSystem;

import java.util.List;

public class MessagingSystem implements DataTransferSystem {

    @Override
    public void add(Message message) {

    }

    @Override
    public Message getByWeight(int weight) {
        return null;
    }

    @Override
    public Message getLightest() {
        return null;
    }

    @Override
    public Message getHeaviest() {
        return null;
    }

    @Override
    public Message deleteLightest() {
        return null;
    }

    @Override
    public Message deleteHeaviest() {
        return null;
    }

    @Override
    public Boolean contains(Message message) {
        return null;
    }

    @Override
    public List<Message> getOrderedByWeight() {
        return null;
    }

    @Override
    public List<Message> getPostOrder() {
        return null;
    }

    @Override
    public List<Message> getPreOrder() {
        return null;
    }

    @Override
    public List<Message> getInOrder() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
