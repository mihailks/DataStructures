package core;

import interfaces.Entity;
import interfaces.Repository;

import java.util.*;

public class Data implements Repository {
    private final Queue<Entity> data;

    public Data() {
        this.data = new PriorityQueue<>();
    }

    public Data(Data other) {
        this.data = new PriorityQueue<>(other.data); //copy all element and create new priority queue
    }

    @Override
    public void add(Entity entity) {
//        if (entity.getId() > 0) {
//            Entity parent = this.getById(entity.getParentId());
//            parent.addChild(entity);
//        }
        this.data.offer(entity);
    }

    @Override
    public Entity getById(int id) {
        for (Entity entity : data) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public List<Entity> getByParentId(int id) {
        List<Entity> result = new ArrayList<>();
//        Entity parent = this.getById(id);
//
//        if (parent != null) {
//            result.addAll(parent.getChildren());
//        }

        for (Entity entity : data) {
            if (entity.getParentId() == id) {
                result.add(entity);
            }
        }

        return result;
    }

    @Override
    public List<Entity> getAll() {
        return new ArrayList<>(this.data);
    }

    @Override
    public Repository copy() {
        return new Data(this);
    }

    @Override
    public List<Entity> getAllByType(String type) {
        List<Entity> result = new ArrayList<>();
        for (Entity entity : data) {
            if (entity.getClass().getSimpleName().equals(type)) {
                result.add(entity);
            }
        }
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Illegal type: " + type);
        }
        return result;
    }

    @Override
    public Entity peekMostRecent() {
        if (this.data.isEmpty()){
            throw new IllegalStateException("Operation on empty Data");
        }
        return this.data.peek();
    }

    @Override
    public Entity pollMostRecent() {
        if (this.data.isEmpty()){
            throw new IllegalStateException("Operation on empty Data");
        }
        return this.data.poll();
    }

    @Override
    public int size() {
        return this.data.size();
    }
}
