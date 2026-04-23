package zest;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ConvertSortedListToBinarySearchTreeConditionTest {
    private final ConvertSortedListToBinarySearchTree bst = new ConvertSortedListToBinarySearchTree();

    @Test
    void sortedListToBST_valueBelowMinBound_nok() {
        assertThrows(IllegalArgumentException.class,
                () -> bst.sortedListToBST(new ListNode(-200001)));
    }

    @Test
    void sortedListToBST_valueAboveMaxBound_nok() {
        assertThrows(IllegalArgumentException.class,
                () -> bst.sortedListToBST(new ListNode(200001)));
    }

    @Test
    void sortedListToBST_null_nok() {
        assertThrows(IllegalArgumentException.class,
                () -> bst.sortedListToBST(null));
    }

    @Test
    void sortedListToBST_tooManyNodes_nok() {
        ListNode head = new ListNode(0);
        ListNode current = head;
        for (int i : IntStream.range(1, 30001).toArray()) {
            current.next = new ListNode(i);
            current = current.next;
        }

        assertThrows(IllegalArgumentException.class,
                () -> bst.sortedListToBST(head));
    }

    @Test
    void sortedListToBST_unsorted_nok() {
        assertThrows(IllegalArgumentException.class,
                () -> bst.sortedListToBST(new ListNode(1, new ListNode(0))));
    }
}
