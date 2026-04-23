package zest;

import java.util.ArrayList;
import java.util.List;

public class ConvertSortedListToBinarySearchTree {
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {
            throw new IllegalArgumentException("linked list has to be not null");
        }

        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }

        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) {
                throw new IllegalArgumentException("linked list has to be sorted in ascending order");
            }
        }

        return sortedArrayToBST(list, 0, list.size() - 1);
    }

    private TreeNode sortedArrayToBST(List<Integer> list, int start, int end) {
        // The number of nodes in the linked list is in the range [0, 2 * 10^4].
        if (list.size() > 20_000) {
            throw new IllegalArgumentException("linked list has to be in the range [0, 2 * 10^4]");
        }
        // Each node's value will be in the range [-10^5, 10^5].
        if (list.parallelStream().anyMatch(val -> val < -100_000 || val > 100_000)) {
            throw new IllegalArgumentException("linked list values have to be in the range [-10^5, 10^5]");
        }

        if (start > end) {
            return null;
        }
        int mid = start + (end - start) / 2;
        TreeNode root = new TreeNode(list.get(mid));
        root.left = sortedArrayToBST(list, start, mid - 1);
        root.right = sortedArrayToBST(list, mid + 1, end);
        return root;
    }
}
