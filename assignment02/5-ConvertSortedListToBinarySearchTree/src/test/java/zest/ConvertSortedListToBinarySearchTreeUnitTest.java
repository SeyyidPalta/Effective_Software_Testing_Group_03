package zest;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ConvertSortedListToBinarySearchTreeUnitTest {
    private final ConvertSortedListToBinarySearchTree bst = new ConvertSortedListToBinarySearchTree();

    @Test
    void sortedListToBST_example1_ok() {
        ListNode head = new ListNode(-10,
                new ListNode(-3,
                        new ListNode(0,
                                new ListNode(5,
                                        new ListNode(9)))));
        TreeNode expected = new TreeNode(0,
                new TreeNode(-10,
                        null,
                        new TreeNode(-3)),
                new TreeNode(5,
                        null,
                        new TreeNode(9)));

        assertEquals(expected.val, bst.sortedListToBST(head).val);
        assertEquals(expected.left.val, bst.sortedListToBST(head).left.val);
        assertEquals(expected.right.val, bst.sortedListToBST(head).right.val);
        assertEquals(expected.left.right.val, bst.sortedListToBST(head).left.right.val);
        assertEquals(expected.right.right.val, bst.sortedListToBST(head).right.right.val);
    }

    @Test
    void sortedListToBST_example2_ok() {
        assertEquals(new TreeNode().val, bst.sortedListToBST(new ListNode()).val);
    }

    @Test
    void sortedListToBST_example3_ok() {
        ListNode head = new ListNode(0);
        TreeNode expected = new TreeNode(0);

        assertEquals(expected.val, bst.sortedListToBST(head).val);
    }

    @Test
    void sortedListToBST_duplicateRoot_ok() {
        ListNode head = new ListNode(0, new ListNode(0));
        TreeNode expected = new TreeNode(0);

        assertEquals(expected.val, bst.sortedListToBST(head).val);
    }

    @Test
    void sortedListToBST_duplicateLeaf_ok() {
        ListNode head = new ListNode(0, new ListNode(1, new ListNode(1)));
        TreeNode expected = new TreeNode(1, new TreeNode(0), new TreeNode(1));

        TreeNode actual = bst.sortedListToBST(head);

        assertEquals(expected.val, actual.val);
        assertEquals(expected.left.val, actual.left.val);
        assertEquals(expected.right.val, actual.right.val);
    }

    @Test
    void sortedListToBST_allNegative_ok() {
        ListNode head = new ListNode(-5, new ListNode(-3, new ListNode(-1)));
        TreeNode expected = new TreeNode(-3, new TreeNode(-5), new TreeNode(-1));

        TreeNode actual = bst.sortedListToBST(head);

        assertEquals(expected.val, actual.val);
        assertEquals(expected.left.val, actual.left.val);
        assertEquals(expected.right.val, actual.right.val);
    }

    @Test
    void sortedListToBST_largeList_ok() {
        ListNode head = new ListNode(0);
        ListNode current = head;
        for (int i : IntStream.range(1, 19).toArray()) {
            current.next = new ListNode(i);
            current = current.next;
        }
        TreeNode expected = new TreeNode(9,
                new TreeNode(4,
                        new TreeNode(1,
                                new TreeNode(0),
                                new TreeNode(2,
                                        null,
                                        new TreeNode(3))),
                        new TreeNode(6,
                                new TreeNode(5),
                                new TreeNode(7,
                                        null,
                                        new TreeNode(8)))),
                new TreeNode(14,
                        new TreeNode(11,
                                new TreeNode(10),
                                new TreeNode(12,
                                        null,
                                        new TreeNode(13))),
                        new TreeNode(16,
                                new TreeNode(15),
                                new TreeNode(17,
                                        null,
                                        new TreeNode(18)))));

        TreeNode actual = bst.sortedListToBST(head);

        assertEquals(expected.val, actual.val);

        assertEquals(expected.left.val, actual.left.val);
        assertEquals(expected.right.val, actual.right.val);

        assertEquals(expected.left.left.val, actual.left.left.val);
        assertEquals(expected.left.right.val, actual.left.right.val);
        assertEquals(expected.right.left.val, actual.right.left.val);
        assertEquals(expected.right.right.val, actual.right.right.val);

        assertEquals(expected.left.left.left.val, actual.left.left.left.val);
        assertEquals(expected.left.left.right.val, actual.left.left.right.val);
        assertEquals(expected.left.right.left.val, actual.left.right.left.val);
        assertEquals(expected.left.right.right.val, actual.left.right.right.val);

        assertEquals(expected.right.left.left.val, actual.right.left.left.val);
        assertEquals(expected.right.left.right.val, actual.right.left.right.val);
        assertEquals(expected.right.right.left.val, actual.right.right.left.val);
        assertEquals(expected.right.right.right.val, actual.right.right.right.val);

        assertEquals(expected.left.left.right.right.val, actual.left.left.right.right.val);
        assertEquals(expected.left.right.right.right.val, actual.left.right.right.right.val);
        assertEquals(expected.right.left.right.right.val, actual.right.left.right.right.val);
        assertEquals(expected.right.right.right.right.val, actual.right.right.right.right.val);
    }
}
