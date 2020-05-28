import java.util.LinkedList;
        import java.util.Queue;

public class BiPartitionDetection {
    private Graph G;
    private boolean[] visited;
    private int[] colors; //  两种颜色,实际 colors 可以兼有 visited 的功能
    private boolean isBipartite = true;

    public BiPartitionDetection(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        colors = new int[G.V()];

        for(int v = 0; v < G.V(); v ++)
            colors[v] = -1;

        for(int v = 0; v < G.V(); v ++)
            if(!visited[v])
                if(!bfs(v)){
                    isBipartite = false;
                    break;
                }
    }

    private boolean bfs(int s){
        // 从s 点开始看整张图是否是二分图
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        colors[s] = 0;
        while (!queue.isEmpty()){
            int v = queue.remove();
            for(int w: G.adj(v)) {
                if (!visited[w]) {
                    queue.add(w);
                    visited[w] = true;
                    colors[w] = 1 - colors[v];
                }
                else if(colors[v] == colors[w]){
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {

        Graph g = new Graph("g.txt");
        BiPartitionDetection bipartitionDetection = new BiPartitionDetection(g);
        System.out.println(bipartitionDetection.isBipartite);
    }
}
