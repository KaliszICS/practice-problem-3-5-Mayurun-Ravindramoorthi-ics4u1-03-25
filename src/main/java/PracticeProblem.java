import java.util.LinkedList;
import java.util.Queue;

public class PracticeProblem {

    // For minimum moves: explore in all directions
    private static final int[][] DIRS_ALL = {
        {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    // For counting paths: restrict to up and right only
    private static final int[][] DIRS_UP_RIGHT = {
        {-1, 0}, {0, 1}
    };

    // Method 1: Count number of valid paths (up/right only)
    public static int noOfPaths(String[][] maze) {
        int rows = maze.length;
        int cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];
        return dfsCountPaths(maze, rows - 1, 0, visited); // Start at "S" (bottom-left)
    }

    private static int dfsCountPaths(String[][] maze, int r, int c, boolean[][] visited) {
        if (!isValid(maze, r, c) || visited[r][c] || maze[r][c].equals("X")) return 0;
        if (maze[r][c].equals("F")) return 1;

        visited[r][c] = true;
        int total = 0;

        for (int[] dir : DIRS_UP_RIGHT) {
            total += dfsCountPaths(maze, r + dir[0], c + dir[1], visited);
        }

        visited[r][c] = false; // backtrack
        return total;
    }

    // Method 2: Find minimum number of moves using BFS
    public static int searchMazeMoves(String[][] maze) {
        int rows = maze.length;
        int cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();

        queue.add(new int[]{rows - 1, 0, 0}); // {row, col, steps}
        visited[rows - 1][0] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1], steps = cur[2];

            if (maze[r][c].equals("F")) {
                return steps;
            }

            for (int[] dir : DIRS_ALL) {
                int nr = r + dir[0], nc = c + dir[1];
                if (isValid(maze, nr, nc) && !visited[nr][nc] && !maze[nr][nc].equals("X")) {
                    visited[nr][nc] = true;
                    queue.add(new int[]{nr, nc, steps + 1});
                }
            }
        }

        return -1; // No path found
    }

    // Helper method to check bounds
    private static boolean isValid(String[][] maze, int r, int c) {
        return r >= 0 && r < maze.length && c >= 0 && c < maze[0].length;
    }
}