// 无向无权图
public class BipartitionDetection {

    private Graph G;
    private boolean isBipartite = true;
    private boolean[] visited;
    private int[] colors; // 取值 0, 1 表示两种颜色

    public BipartitionDetection(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        colors = new int[G.V()];
        for(int i = 0; i < G.V(); i ++)
            colors[i] = -1; // 表示还没有被染色

        for(int v = 0; v < G.V(); v ++)
            if(!visited[v])
                if(!dfs(v, 0))
                    isBipartite = false;
    }

    private boolean dfs(int v, int color){
        // v 会被染色为 color
        // 通过 v 检测是否是二分图
        visited[v] = true;
        colors[v] = color;
        for(int w: G.adj(v)) {
            if (!visited[w]) {
                if (!dfs(w, 1 - color))// 邻点颜色相反
                    return false;
            }
            else if (colors[w] == colors[v])
                return false;
        }
        return true;

    }


    public boolean isBipartite(){
        return isBipartite;
    }

    public static void main(String args[]){
        Graph g = new Graph("g.txt");
        BipartitionDetection bd = new BipartitionDetection(g);
        System.out.println(bd.isBipartite());
    }

}
