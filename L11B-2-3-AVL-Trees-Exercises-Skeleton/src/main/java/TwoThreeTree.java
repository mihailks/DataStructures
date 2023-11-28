public class TwoThreeTree<K extends Comparable<K>> {
    private TreeNode<K> root;

    public TwoThreeTree() {
        root = null;
    }

    public static class TreeNode<K> {
        private K leftKey;
        private K rightKey;

        private TreeNode<K> leftChild;
        private TreeNode<K> middleChild;
        private TreeNode<K> rightChild;

        private TreeNode(K key) {
            this.leftKey = key;
        }

        public TreeNode(K root, K leftValue, K rightValue) {
            // create new parent node with one key and two children, also with one key
            this(root); // we already have a constructor to create new note with one key
            this.leftChild = new TreeNode<>(leftValue);
            this.rightChild = new TreeNode<>(rightValue);
        }

        public TreeNode(K root, TreeNode<K> left, TreeNode<K> right) {
            this.leftKey = root;
            this.leftChild = left;
            this.rightChild = right;
        }

        boolean isThreeNode() {
            return rightKey != null;
        }

        boolean isTwoNode() {
            return rightKey == null;
        }

        boolean isLeaf() {
            return this.leftChild == null && this.middleChild == null && this.rightChild == null;
        }
    }

    public void insert(K key) {
        // if the tree is empty, we add the key to the root
        if (root == null) {
            this.root = new TreeNode<>(key); //we add the key to the left node, and then we check if it is smaller or bigger than the root key and if it is smaller we swap them
            return;
        }
        // we have to check if the root have any children. First we have to go to a leaf and then check up again if there are no space in the leaf

        TreeNode<K> newRoot = this.insert(this.root, key);

        if (newRoot != null) { // when it is not null, we have made changes
            this.root = newRoot;
        }


    }

    private TreeNode<K> insert(TreeNode<K> node, K key) {
        // at the first step we know that we have only one key in the root
        if (node.isLeaf()) {
            if (node.isTwoNode()) {
                if (node.leftKey.compareTo(key) < 0) { //here we try fo find the place of the new key, and we compare it to the one already in
                    node.rightKey = key;
                } else {
                    node.rightKey = node.leftKey; // save the key that`s already in to the new place, because it is bigger than the new key
                    node.leftKey = key;
                }
                return null; // we won`t have a result. Return null because everything have it`s place. We won`t have to go up and change the others;
            }
            // if the node is three node
            K left = node.leftKey;
            K middle = key; // we have to find the place of the new key. We have to compare it to the left and right key
            K right = node.rightKey;
            if (key.compareTo(left) < 0) {
                left = key;
                middle = node.leftKey;
            } else if (key.compareTo(right) > 0) {
                right = key;
                middle = node.rightKey;
            }

            return new TreeNode<>(middle, left, right);


        }
        //navigate to the bottom of the tree
        TreeNode<K> toFix = null;
        if (node.leftKey.compareTo(key) > 0) { // it does`n matter if we have a two or a three note, we always have left key. If the key is smaller than the left key, we go to the left child
            toFix = insert(node.leftChild, key);
        } else if (node.isTwoNode() && node.leftKey.compareTo(key) < 0) { // if the key is smaller than the left key, we go to the left child
            toFix = insert(node.rightChild, key);
        } else if (node.isThreeNode() && node.rightKey.compareTo(key) < 0) { // if the key is smaller than the left key, we go to the left child
            toFix = insert(node.rightChild, key);
        } else { // if the key is between the left and the right key, we go to the middle child
            toFix = insert(node.middleChild, key);
        }
        if (toFix == null) { // if we don`t have to fix/rearrange anything, we return null.
            return null;
        }

        if (node.isTwoNode()) { // for sure this is a two node, so when we put new middle key, we are sure, that we won`t replace some other key
            // we have to put it as a middle child, because we go left. All the keys on the left are smaller than parent key.
            // So we can`t put the child on the right, because it will be smaller than the parent key, and we will break the binary search tree rule
            if (toFix.leftKey.compareTo(node.leftKey) < 0) {
                // fix the keys
                node.rightKey = node.leftKey;
                node.leftKey = toFix.leftKey;
                // fix the references/children
                node.leftChild = toFix.leftChild;
                node.middleChild = toFix.rightChild;
            } else {
                // fix the keys
                node.rightKey = toFix.leftKey;
                // fix the references/children
                node.middleChild = toFix.leftChild;
                node.rightChild = toFix.rightChild;
            }
            return null;
        }

        K promoteValue = null;
        TreeNode<K> left = null;
        TreeNode<K> right = null;
        // if we have a three node
        if (toFix.leftKey.compareTo(node.leftKey) < 0) {
            // fix the keys
            // this is the new key, that have to go up
            promoteValue = node.leftKey;
            left = toFix;
            right = new TreeNode<>(node.rightKey, node.middleChild, node.rightChild);
        }
        if (toFix.leftKey.compareTo(node.rightKey) > 0) {
            // fix the keys
            // this is the new key, that have to go up
            promoteValue = node.rightKey;
            left = new TreeNode<>(node.leftKey, node.leftChild, node.middleChild);
            right = toFix;
        } else {
            // middle key is the new key, that have to go up
            // this is the new key, that have to go up
            promoteValue = toFix.leftKey;
            left = new TreeNode<>(node.leftKey, node.leftChild, toFix.leftChild);
            right = new TreeNode<>(node.rightKey, toFix.rightChild, node.rightChild);
        }

        return new TreeNode<>(promoteValue, left, right);
    }


    public String getAsString() {
        StringBuilder out = new StringBuilder();
        recursivePrint(this.root, out);
        return out.toString().trim();
    }

    private void recursivePrint(TreeNode<K> node, StringBuilder out) {
        if (node == null) {
            return;
        }
        if (node.leftKey != null) {
            out.append(node.leftKey)
                    .append(" ");
        }
        if (node.rightKey != null) {
            out.append(node.rightKey).append(System.lineSeparator());
        } else {
            out.append(System.lineSeparator());
        }
        if (node.isTwoNode()) {
            recursivePrint(node.leftChild, out);
            recursivePrint(node.rightChild, out);
        } else if (node.isThreeNode()) {
            recursivePrint(node.leftChild, out);
            recursivePrint(node.middleChild, out);
            recursivePrint(node.rightChild, out);
        }
    }
}
