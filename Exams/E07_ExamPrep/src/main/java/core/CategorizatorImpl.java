package core;

import models.Category;

import java.util.*;

public class CategorizatorImpl implements Categorizator {
    //TODO: there are some bugs in the code below.

        private Map<String, Category> categories = new HashMap<>();
        private Map<String, Set<String>> parentChildMapping = new HashMap<>();

        @Override
        public void addCategory(Category category) {
            if (categories.containsKey(category.getId())) {
                throw new IllegalArgumentException();
            }
            categories.put(category.getId(), category);
        }

        @Override
        public void assignParent(String childCategoryId, String parentCategoryId) {
            if (!categories.containsKey(childCategoryId) || !categories.containsKey(parentCategoryId)) {
                throw new IllegalArgumentException();
            }

            if (parentChildMapping.containsKey(parentCategoryId) &&
                    parentChildMapping.get(parentCategoryId).contains(childCategoryId)) {
                throw new IllegalArgumentException();
            }

            parentChildMapping.computeIfAbsent(parentCategoryId, k -> new HashSet<>()).add(childCategoryId);
        }

        @Override
        public void removeCategory(String categoryId) {
            if (!categories.containsKey(categoryId)) {
                throw new IllegalArgumentException();
            }
            categories.remove(categoryId);
            for (Set<String> children : parentChildMapping.values()) {
                children.remove(categoryId);
            }
        }

        @Override
        public boolean contains(Category category) {
            return categories.containsKey(category.getId());
        }

        @Override
        public int size() {
            return categories.size();
        }

        @Override
        public Iterable<Category> getChildren(String categoryId) {
            if (!categories.containsKey(categoryId)) {
                throw new IllegalArgumentException();
            }

            Set<Category> childrenCategories = new HashSet<>();
            Set<String> childrenIds = parentChildMapping.getOrDefault(categoryId, Collections.emptySet());

            for (String childId : childrenIds) {
                childrenCategories.add(categories.get(childId));
                List<Category> children = (List<Category>) getChildren(childId);
                childrenCategories.addAll(children);
            }

            return childrenCategories;
        }

        @Override
        public Iterable<Category> getHierarchy(String categoryId) {
            if (!categories.containsKey(categoryId)) {
                throw new IllegalArgumentException();
            }

            LinkedList<Category> hierarchy = new LinkedList<>();
            Category category = categories.get(categoryId);

            hierarchy.addFirst(category);

            while (parentChildMapping.containsKey(category.getId())) {
                String parentId = parentChildMapping.get(category.getId()).iterator().next();
                category = categories.get(parentId);
                hierarchy.addFirst(category);
            }

            return hierarchy;
        }

        @Override
        public Iterable<Category> getTop3CategoriesOrderedByDepthOfChildrenThenByName() {
            List<Category> sortedCategories = new ArrayList<>(categories.values());

            sortedCategories.sort((c1, c2) -> {
                int depthCompare = Integer.compare(getDepthOfChildren(c2.getId()), getDepthOfChildren(c1.getId()));
                if (depthCompare != 0) {
                    return depthCompare;
                } else {
                    int nameCompare = c1.getName().compareTo(c2.getName());
                    if (nameCompare != 0) {
                        return nameCompare;
                    } else {
                        return Integer.compare(getIndexOfInputOrder(c1.getId()), getIndexOfInputOrder(c2.getId()));
                    }
                }
            });

            return sortedCategories.subList(0, Math.min(3, sortedCategories.size()));
        }

        private int getDepthOfChildren(String categoryId) {
            int depth = 0;
            Set<String> childrenIds = parentChildMapping.getOrDefault(categoryId, Collections.emptySet());

            for (String childId : childrenIds) {
                depth = Math.max(depth, 1 + getDepthOfChildren(childId));
            }

            return depth;
        }

        private int getIndexOfInputOrder(String categoryId) {
            int index = 0;
            for (Category category : categories.values()) {
                if (category.getId().equals(categoryId)) {
                    return index;
                }
                index++;
            }
            return index;
        }
    }
