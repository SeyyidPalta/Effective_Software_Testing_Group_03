package zest;

import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@PropertyDefaults(tries = 100)
class ConvertSortedListToBinarySearchTreePropertyTest {
    private final ConvertSortedListToBinarySearchTree bst = new ConvertSortedListToBinarySearchTree();

    @Property
    void sortedListToBST_nullInputThrowsException() {
        Assume.that(true);
        try {
            bst.sortedListToBST(null);
            Assume.that(false);
        } catch (IllegalArgumentException e) {
            Assume.that(true);
        }
    }

    @Property
    void sortedListToBST_inorderTraversalEqualsInput(
            @ForAll @IntRange(min = -100_000, max = 100_000) int val1,
            @ForAll @IntRange(min = -100_000, max = 100_000) int val2) {
        int min = Math.min(val1, val2);
        int max = Math.max(val1, val2);

        ListNode head = new ListNode(min, new ListNode(max));
        TreeNode actual = bst.sortedListToBST(head);

        Assume.that(actual.val == min);
        Assume.that(actual.right.val == max);
    }

    @Property
    void sortedListToBST_validBST(
            @ForAll List<@IntRange(min = -100_000, max = 100_000) Integer> val) {
        if (val.isEmpty()) return;
        val.sort(Integer::compareTo);

        Assume.that(isBST(bst.sortedListToBST(createList(val)), Long.MIN_VALUE, Long.MAX_VALUE));
    }

    @Property
    void sortedListToBST_heightBalanced(
            @ForAll List<@IntRange(min = -100_000, max = 100_000) Integer> val) {
        if (val.isEmpty()) return;
        val.sort(Integer::compareTo);

        Assume.that(getHeight(bst.sortedListToBST(createList(val))) >= 0);
    }

    @Property
    void sortedListToBST_sameNodeAmount(
            @ForAll List<@IntRange(min = -100_000, max = 100_000) Integer> val) {
        if (val.isEmpty()) return;
        val.sort(Integer::compareTo);

        int actual = countNodes(bst.sortedListToBST(createList(val)));
        Assume.that(actual == val.size());
    }

    @Property
    void sortedListToBST_allValExistsInBST(
            @ForAll List<@IntRange(min = -100_000, max = 100_000) Integer> val) {
        if (val.isEmpty()) return;
        val.sort(Integer::compareTo);

        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(bst.sortedListToBST(createList(val)), inorder);

        for (int v : val) {
            Assume.that(inorder.contains(v));
        }
    }

    @Property
    void sortedListToBST_duplicateValExists(
            @ForAll @IntRange(min = 2, max = 10_000) Integer dupAmount
    ) {
        final int dupVal = 42;

        ListNode head = new ListNode(dupVal);
        ListNode current = head;
        for (int i : IntStream.range(1, dupAmount).toArray()) {
            current.next = new ListNode(dupVal);
            current = current.next;
        }
        List<Integer> inorder = new ArrayList<>();
        inorderTraversal(bst.sortedListToBST(head), inorder);

        inorder.forEach(in -> Assume.that(in == dupVal));
    }

    @Property
    void property_singleVal(
            @ForAll @IntRange(min = -100_000, max = 100_000) int value) {
        ListNode head = new ListNode(value);
        TreeNode actual = bst.sortedListToBST(head);

        Assume.that(actual.val == value);
        Assume.that(actual.left == null);
        Assume.that(actual.right == null);
    }

    private ListNode createList(List<Integer> values) {
        if (values.isEmpty()) return null;
        ListNode head = new ListNode(values.get(0));
        ListNode current = head;
        for (int i = 1; i < values.size(); i++) {
            current.next = new ListNode(values.get(i));
            current = current.next;
        }
        return head;
    }

    private void inorderTraversal(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderTraversal(node.left, result);
        result.add(node.val);
        inorderTraversal(node.right, result);
    }

    private boolean isBST(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val < min || node.val > max) return false;
        return isBST(node.left, min, node.val) && isBST(node.right, node.val, max);
    }

    private int getHeight(TreeNode node) {
        if (node == null) return 0;
        int leftHeight = getHeight(node.left);
        if (leftHeight < 0) return -1;
        int rightHeight = getHeight(node.right);
        if (rightHeight < 0) return -1;
        if (Math.abs(leftHeight - rightHeight) > 1) return -1;
        return Math.max(leftHeight, rightHeight) + 1;
    }

    private int countNodes(TreeNode node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }
}


