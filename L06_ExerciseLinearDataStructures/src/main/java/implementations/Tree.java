package implementations;

import interfaces.AbstractTree;

import java.util.*;

public class Tree<E> implements AbstractTree<E> {
    private E value;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E value, Tree<E>... subtrees) {

        this.value = value;
        this.parent = null;
        this.children = new ArrayList<>();

        for (Tree<E> subtree : subtrees) {
            this.children.add(subtree);
            subtree.parent = this; //текущият обект ще се явява родител
        }
    }

    @Override
    public List<E> orderBfs() {

        List<E> result = new ArrayList<>();
        Deque<Tree<E>> childrenQueue = new ArrayDeque<>();

        if (this.value == null) { //като останем, само с роот елемента, няма как да го изтрием и да запазим дървото едновременно, за това му сетваме стойността равна на нъл и
            // тук проверяваме дали е върнатата стойност е нъл и ако е направо връщаме резултатната опашка
            return result;
        }

        childrenQueue.offer(this);

        while (!childrenQueue.isEmpty()) {

            Tree<E> current = childrenQueue.poll();

            result.add(current.value);

            for (Tree<E> child : current.children) {
                childrenQueue.offer(child);
            }

        }
        return result;
    }

    @Override
    public List<E> orderDfs() {
        List<E> result = new ArrayList<>();

        this.doDfs(this, result); // имплементация чрез рекурсия, тук първо слизаме най-отдолу и от там започваме

        //имплементация със стек, но самото спринтиране става по различен начин, тък каквото видим по пътя го принтираме
//        Deque<Tree<E>> toTraverse = new ArrayDeque<>();
//
//        toTraverse.push(this);
//
//        while (!toTraverse.isEmpty()) {
//            Tree<E> current = toTraverse.pop();
//
//            for (Tree<E> note : current.children) {
//                toTraverse.push(note);
//            }
//            result.add(current.value);
//        }


        return result;
    }

    private void doDfs(Tree<E> node, List<E> result) {
        for (Tree<E> child : node.children) {
            this.doDfs(child, result);
        }
        result.add(node.value);
    }

    @Override
    public void addChild(E parentKey, Tree<E> child) {
        Tree<E> search = findBfs(parentKey);  //търсене на определеният родител с BFS

//        Tree<E> search = findRecursive(this, parentKey);  //търсене на определеният родител с DFS

        if (search == null) {
            throw new IllegalStateException();
        }

        search.children.add(child);
        child.parent = search;
    }


    //    търсене на определеният родител с BFS
    private Tree<E> findBfs(E parentKey) {
        Deque<Tree<E>> childrenQueue = new ArrayDeque<>();

        childrenQueue.offer(this);

        while (!childrenQueue.isEmpty()) {

            Tree<E> current = childrenQueue.poll();

            if (current.value.equals(parentKey)) {
                return current;
            }

            for (Tree<E> child : current.children) {
                childrenQueue.offer(child);
            }

        }
        return null;
    }


    //търсене на определеният родител с DFS
    private Tree<E> findRecursive(Tree<E> current, E parentKey) {

        if (current.value.equals(parentKey)) {
            return current;
        }

        for (Tree<E> child : current.children) {

            Tree<E> found = this.findRecursive(child, parentKey);

            if (found != null) {
                return found;
            }
        }

        return null;
    }


    @Override
    public void removeNode(E nodeKey) {

        Tree<E> toRemove = findBfs(nodeKey);

        if (toRemove == null) {
            throw new IllegalArgumentException("no such element");
        }

        for (Tree<E> child : toRemove.children) {
            child.parent = null;
        }

        toRemove.children.clear();
        Tree<E> parent = toRemove.parent; //трябва да махнем референцията, към родителя на тази който махаме

        if (parent != null) { //по принцип само корена трябва да има родител нъл, а и предпазваме от nullPointerEx, ако искаме да махнем корена
            parent.children.remove(toRemove);
        }
        toRemove.value = null;
    }


    @Override
    public void swap(E firstKey, E secondKey) {
        Tree<E> firstNode = findBfs(firstKey);
        Tree<E> secondNode = findBfs(secondKey);

        if (firstNode == null || secondNode == null) {      //проверяваме дали съществуват
            throw new IllegalArgumentException();
        }

        Tree<E> firstParent = firstNode.parent;     //взимаме им родителите
        Tree<E> secondParent = secondNode.parent;

        //правим слапа на двата ноуда в този случай, като просто запазваме всичко под този, който не е руут. Защото иначе, ако тръгнем да ги сменяме няма ще счупим дървото

        if (firstParent == null) {
            this.value = secondNode.value;  //прехвърляме value в новия ноут;
            this.parent = null; //махаме родителя на новия руут
            this.children = secondNode.children; //прехвърляме си децата (правим, този на който родителите не са нъл, на нов руут)
            secondNode.parent = null; //ако има някакви останали указатели, да ги махнем
            return;
        } else if (secondParent == null) {
            this.value = firstNode.value;  //прехвърляме value в новия ноут;
            this.parent = null; //махаме родителя на новия руут
            this.children = firstNode.children; //прехвърляме си децата (правим, този на който родителите не са нъл, на нов руут)
            firstParent.parent = null;//ако има някакви останали указатели, да ги махнем
            return;
        }

        firstNode.parent = secondParent;    // сменяме им родителите
        secondNode.parent = firstParent;

        //запазваме им индексите за да знам после, като ги сменяме, къде да ги сложим пак
        int firstIndex = firstParent.children.indexOf(firstNode);
        int secondIndex = secondParent.children.indexOf(secondNode);

        firstParent.children.set(firstIndex, secondNode);
        secondParent.children.set(secondIndex, firstNode);

    }
}



