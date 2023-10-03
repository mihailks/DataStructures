package implementations;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class TreeFactory {
    private Map<Integer, Tree<Integer>> nodesByKeys;

    public TreeFactory() {
        this.nodesByKeys = new LinkedHashMap<>();
    }

    public Tree<Integer> createTreeFromStrings(String[] input) {
        for (String params : input) {
            int[] keys = Arrays.stream(params.split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int parentKey = keys[0];
            int childKey = keys[1];

            this.addEdge(parentKey,childKey);
        }
        return this.getRoot();
    }

    private Tree<Integer> getRoot() {   //минаваме през всички и намираме, този който няма родители -> връщаме него като руут
        for (Tree<Integer> value : nodesByKeys.values()) {
            if (value.getParent()==null){
                return value;
            }
        }
        return null;
    }

    public Tree<Integer> createNodeByKey(int key) {
        this.nodesByKeys.putIfAbsent(key, new Tree<>(key));// проверяваме дали вече даденият ключ не е бил добавен в мапа
        return this.nodesByKeys.get(key);
    }

    public void addEdge(int parent, int child) {
        Tree<Integer> parentByKey = this.createNodeByKey(parent);   //взимаме си създадените ноудове
        Tree<Integer> childByKey = this.createNodeByKey(child);

        childByKey.setParent(parentByKey);  //сетваме парент
        parentByKey.addChild(childByKey);   // сетваме дете
    }
}



