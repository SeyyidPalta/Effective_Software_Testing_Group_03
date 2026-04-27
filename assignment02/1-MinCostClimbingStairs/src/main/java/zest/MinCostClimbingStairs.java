package zest;

import java.util.stream.IntStream;

public class MinCostClimbingStairs {
    public int minCostClimbingStairs(int[] cost) {

        if (cost == null || cost.length < 2) {
            throw new IllegalArgumentException("cost array is null or length < 2");
        }

        if (IntStream.of(cost).anyMatch(i -> i <= 0) || IntStream.of(cost).anyMatch(i -> i > 999)) {
            throw new IllegalArgumentException("cost array contains values lower than 0 or higher than 999");
        }

        int n = cost.length;
        int[] dp = new int[n];
        dp[0] = 0;
        dp[1] = cost[1];

        for (int i = 2; i < n; i++) {
            dp[i] = cost[i] + Math.min(dp[i - 1], dp[i - 2]);
        }

        return Math.min(dp[n - 1], dp[n - 2]);
    }
}
