package implementations;

import interfaces.AbstractTree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class Tree<E> implements AbstractTree<E> {

    private E key; // value
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E key/*, Tree<E>... children*/) {

        this.key = key;
        this.children = new ArrayList<>();

//        this.children.addAll(Arrays.asList(children));
//        for (int i = 0; i < children.length; i++) {
//            children[i].setParent(this);
//        }

    }


    @Override
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public String getAsString() {
        StringBuilder stringBuilder = new StringBuilder();

        traverseTreeRecursive(stringBuilder, 0, this);

        return stringBuilder.toString().trim();
    }

    public String traverseWithBFS() {
        StringBuilder stringBuilder = new StringBuilder();

        Deque<Tree<E>> queue = new ArrayDeque<>(); //не използваме само стек или опашка, а циклична опашка(Deque), защото е по-бърза, защото е с Array отдолу и по малко кеш мисове ще има

        queue.offer(this);
        int indent = 0;

        while (!queue.isEmpty()) {
            Tree<E> currentTree = queue.poll();

            if (currentTree.parent != null && currentTree.getParent().getKey().equals(this.getKey())) { // така ще работи само за нашият случай, тоест с 3 нива
                indent = 2;
            }

            if (currentTree.children.isEmpty()) {
                indent = 4;
            }

            stringBuilder
                    .append(getPadding(indent))
                    .append(currentTree.getKey())
                    .append(System.lineSeparator());

            for (Tree<E> child : currentTree.children) {
                queue.offer(child);
            }
        }
        return stringBuilder.toString().trim();
    }

    public List<Tree<E>> traverseWithBFSAllNodes() {
        Deque<Tree<E>> queue = new ArrayDeque<>();
        List<Tree<E>> allNodes = new ArrayList<>();

        queue.offer(this);

        while (!queue.isEmpty()) {
            Tree<E> currentTree = queue.poll();
            allNodes.add(currentTree);
            for (Tree<E> child : currentTree.children) {
                queue.offer(child);
            }
        }
        return allNodes;
    }

    private void traverseTreeRecursive(StringBuilder stringBuilder, int indent, Tree<E> tree) {

        stringBuilder
                .append(this.getPadding(indent))
                .append(tree.getKey())
                .append(System.lineSeparator());

        for (Tree<E> child : tree.children) {
            traverseTreeRecursive(stringBuilder, indent + 2, child); //дъното, е че като мина през всички деца, повече няма да влизам във ForEach-а и реално това се явява дъно
        }
    }

//    private void traverseTreeRecursive(Tree<E> tree) { //най-простото DFS
//        for (Tree<E> child : tree.children) {
//            traverseTreeRecursive(child);
//        }
//    }

    private void traverseTreeRecursive(List<Tree<E>> collection, Tree<E> tree) {
        collection.add(tree);
        for (Tree<E> child : tree.children) {
            traverseTreeRecursive(collection, child);
        }
    }

    private String getPadding(int size) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    @Override
    public List<E> getLeafKeys() {
        List<E> leafs = new ArrayList<>();
        getLeafsRecursive(this, leafs);
        return leafs;
    }

    private void getLeafsRecursive(Tree<E> tree, List<E> leafs) {
        for (Tree<E> child : tree.children) {
            if (child.children.isEmpty()) {
                leafs.add(child.getKey());
            } else {
                getLeafsRecursive(child, leafs);
            }
        }
    }

    @Override
    public List<E> getMiddleKeys() {
        List<Tree<E>> allNotes = new ArrayList<>();
        this.traverseTreeRecursive(allNotes, this);
        return allNotes
                .stream()   //взимаме всички
                .filter(tree -> tree.parent != null && !tree.children.isEmpty())// филтър само тези, които имат родители и деца едновременно
                .map(Tree::getKey)  // взимаме им само стойностите
                .collect(Collectors.toList());
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() { //пробваме да тръгнем от децата нагоре bottom up approach
        List<Tree<E>> trees = this.traverseWithBFSAllNodes();
        // ще бройм, колко стъпки имаме от всяко листо до руут
        int maxPath = 0;

        Tree<E> deepestLeftMostNode = null;

        for (Tree<E> tree : trees) {
            if (tree.isLeaf()) {
                int currentPath = getStepsFromLeafToRoot(tree);
                if (currentPath > maxPath) {
                    maxPath = currentPath;
                    deepestLeftMostNode = tree;
                }
            }
        }

        return deepestLeftMostNode;
    }

    private int getStepsFromLeafToRoot(Tree<E> tree) {

        int counter = 0;
        Tree<E> current = tree;

        while (current.parent != null) {
            counter++;
            current = current.parent; //това е реално местенето нагоре
        }
        return counter;
    }

    private boolean isLeaf() {
        return this.parent != null && this.children.isEmpty();
    }

    @Override
    public List<E> getLongestPath() {
        return null;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        return null;
    }

    @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {
        return null;
    }
}



