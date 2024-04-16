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






// package core;

// import models.Category;

// import java.util.*;
// import java.util.stream.Collectors;

// public class CategorizatorImpl implements Categorizator {

//     LinkedHashMap<String, Category> categoriesById = new LinkedHashMap<>();
//     Map<String, Category> parentByCatId = new HashMap<>();
//     Map<String, LinkedHashSet<Category>> childrenByCatId = new HashMap<>();

//     @Override
//     public void addCategory(Category category) {
//         if (contains(category)) {
//             throw new IllegalArgumentException();
//         }

//         categoriesById.put(category.getId(), category);
//         childrenByCatId.put(category.getId(), new LinkedHashSet<>());
//     }

//     private Category tryGetCategory(String id) {
//         return categoriesById.get(id);
//     }

//     @Override
//     public void assignParent(String childCategoryId, String parentCategoryId) {
//         Category child = tryGetCategory(childCategoryId);
//         Category parent = tryGetCategory(parentCategoryId);

//         if (child == null || parent == null) {
//             throw new IllegalArgumentException();
//         }

//         Category previousParent = parentByCatId.put(child.getId(), parent);
//         if (previousParent != null) {
//             throw new IllegalArgumentException();
//         }

//         LinkedHashSet<Category> parentCategoryChildren = childrenByCatId.get(parent.getId());
//         parentCategoryChildren.add(child);
//     }

//     @Override
//     public void removeCategory(String categoryId) {
//         Category categoryToDelete = categoriesById.remove(categoryId);
//         if (categoryToDelete == null) {
//             throw new IllegalArgumentException();
//         }

//         LinkedHashSet<Category> childrenToDelete = new LinkedHashSet<>(childrenByCatId.get(categoryToDelete.getId()));
//         for (Category category : childrenToDelete) {
//             removeCategory(category.getId());
//         }

//         Category parent = parentByCatId.remove(categoryToDelete.getId());
//         if (parent != null) {
//             LinkedHashSet<Category> parentCategoryChildren = childrenByCatId.get(parent.getId());
//             parentCategoryChildren.remove(categoryToDelete);
//         }
//     }

//     @Override
//     public boolean contains(Category category) {
//         return contains(category.getId());
//     }

//     private boolean contains(String categoryId) {
//         return tryGetCategory(categoryId) != null;
//     }

//     @Override
//     public int size() {
//         return categoriesById.size();
//     }

//     private void fillChildren(String categoryId, List<Category> allChildren) {
//         LinkedHashSet<Category> directChildren = childrenByCatId.get(categoryId);
//         for (Category directChild : directChildren) {
//             allChildren.add(directChild);
//             fillChildren(directChild.getId(), allChildren);
//         }
//     }

//     @Override
//     public Iterable<Category> getChildren(String categoryId) {
//         if (!contains(categoryId)) {
//             throw new IllegalArgumentException();
//         }

//         List<Category> allChildren = new ArrayList<>();
//         fillChildren(categoryId, allChildren);

//         return allChildren;
//     }

//     @Override
//     public Iterable<Category> getHierarchy(String categoryId) {
//         Category category = tryGetCategory(categoryId);
//         if (category == null) {
//             throw new IllegalArgumentException();
//         }

//         List<Category> hierarchy = new ArrayList<>();
//         while (category != null) {
//             hierarchy.add(category);
//             category = parentByCatId.get(category.getId());
//         }

//         Collections.reverse(hierarchy);
//         return hierarchy;
//     }

//     Map<String, Long> depthByCategoryId = new HashMap<>();
//     @Override
//     public Iterable<Category> getTop3CategoriesOrderedByDepthOfChildrenThenByName() {
//         depthByCategoryId = new HashMap<>();

//         for (Category category : categoriesById.values()) {
//             if (parentByCatId.get(category.getId()) == null) {
//                 calculateDepth(category);
//             }
//         }

//         return categoriesById.values().stream()
//                 .sorted(
//                         Comparator.comparing((Category c) -> depthByCategoryId.get(c.getId()), Comparator.reverseOrder())
//                                 .thenComparing((Category c) -> c.getName())
//                 )
//                 .limit(3)
//                 .collect(Collectors.toList());
//     }

//     private long calculateDepth(Category category) {
//         long maxChildDepth = 0;

//         for (Category childCategory : childrenByCatId.get(category.getId())) {
//             long childDepth = calculateDepth(childCategory);
//             if (maxChildDepth < childDepth) {
//                 maxChildDepth = childDepth;
//             }
//         }

//         long depth = 1 + maxChildDepth;
//         depthByCategoryId.put(category.getId(), depth);

//         return depth;
//     }
// }
