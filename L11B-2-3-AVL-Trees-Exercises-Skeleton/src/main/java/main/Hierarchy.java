package main;

import java.util.*;
import java.util.stream.Collectors;

public class Hierarchy<T> implements IHierarchy<T> {
    private Map<T, HierarchyNode<T>> data; // so we can access the nodes by their key easily and keep the uniqueness of the tree
    //we use a map, so we don't have to iterate through the whole tree to find a node. We can access it directly by its key
    private HierarchyNode<T> root; // we keep the root element, so we can access it easily


    public Hierarchy(T element) {
        this.data = new HashMap<>();
        HierarchyNode<T> root = new HierarchyNode<>(element); // on the first call we create the root element
        this.root = root; // we set the root element
        this.data.put(element, root); // we add the root element to the map
    }


    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public void add(T element, T child) {
        HierarchyNode<T> parent = ensureElementExistsAndGet(element); // we get the parent node

        if (this.data.containsKey(child)) {
            throw new IllegalArgumentException();
        }

        HierarchyNode<T> toBeAdded = new HierarchyNode<>(child); // we create the node, which we have to add
        toBeAdded.setParent(parent); // we set the parent of the node
        parent.getChildren().add(toBeAdded); // we add the node to the children of the parent
        this.data.put(child, toBeAdded); // we add the node to the map
    }

    @Override
    public void remove(T element) {
        HierarchyNode<T> toRemove = ensureElementExistsAndGet(element);
        if (toRemove.getParent() == null) { //check if the element is the root
            throw new IllegalStateException();
        }
//        toRemove.getParent().getChildren().addAll(toRemove.getChildren()); // we add all the children of the node to be removed to the parent of the nodes
        HierarchyNode<T> parent = toRemove.getParent();

        List<HierarchyNode<T>> children = toRemove.getChildren(); // we get the children of the node to be removed
        for (HierarchyNode<T> child : children) {   // and we set their parent to be the parent of the node to be removed
            child.setParent(parent);
            parent.getChildren().add(child);
        }
        parent.getChildren().remove(toRemove); // we remove the node from the children of the parent
        this.data.remove(toRemove.getValue()); // we remove the node from the map
    }

    @Override
    public Iterable<T> getChildren(T element) {
        HierarchyNode<T> parent = ensureElementExistsAndGet(element);
        return parent.getChildren()
                .stream()
                .map(HierarchyNode::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public T getParent(T element) {
        HierarchyNode<T> node = ensureElementExistsAndGet(element);
        return node.getParent() == null ? null : node.getParent().getValue();
    }

    @Override
    public boolean contains(T element) {
        return this.data.containsKey(element);
    }

    @Override
    public Iterable<T> getCommonElements(IHierarchy<T> other) {
        List<T> commonElements = new ArrayList<>();
        for (T element : data.keySet()) {
            if (other.contains(element)){
                commonElements.add(element);
            }
        }
        return commonElements;
    }

    @Override
    public Iterator<T> iterator() {
    //breadth-first search!!!
        Deque<HierarchyNode<T>> deque = new ArrayDeque<>(
                Arrays.asList(root)
        );

        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return deque.size()>0;
            }

            @Override
            public T next() {
                HierarchyNode<T> nextElement = deque.poll();
                deque.addAll(nextElement.getChildren());
                return nextElement.getValue();
            }
        };
    }

    private HierarchyNode<T> ensureElementExistsAndGet(T key) {
        HierarchyNode<T> element = this.data.get(key);
        if (element == null) {
            throw new IllegalArgumentException();
        }
        return element;
    }

}
