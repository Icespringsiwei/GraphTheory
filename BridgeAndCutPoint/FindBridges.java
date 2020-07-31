import java.util.ArrayList;
// 无向无权图
public class FindBridges {

    private Graph G;
    private boolean[] visited;
    private int ord[];
    private int low[];
    private ArrayList<Edge> res; // 存储桥边
    private int cnt; // 记录顶点的访问顺序值，其实就是当前已经遍历的顶点数(0开始)

    public FindBridges(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        res = new ArrayList<>();
        ord = new int[G.V()];
        low = new int[G.V()];
        cnt = 0;

        for(int v = 0; v < G.V(); v ++)
            if(!visited[v])// 在各个连通分量中寻找桥
                dfs(v, v);
    }

    private void dfs(int v, int parent){
        visited[v] = true;
        ord[v] = cnt ++;// ord[v] = cnt; cnt ++;
        low[v] = ord[v];

        for(int w: G.adj(v))
            if(!visited[w]) {
                dfs(w, v);
                low[v] = Math.min(low[v], low[w]);
                // w 没有被访问过，v-w是 条新边，判定它是否是桥
                if(low[w] > ord[v])
                    // v-w 是桥
                    res.add(new Edge(v, w));
            }else if (w != parent){// w 被访问过但不是它的parent节点
                // 如果 w == parent,意味着出现了环
                low[v] = Math.min(low[v], low[w]);// 由于v和w相邻，故w能到达的最小ord，v也能到达
            }
    }
    public ArrayList<Edge> result(){
        return res;
    }
    public static void main(String args[]){
        Graph g = new Graph("g.txt");
        FindBridges fb = new FindBridges(g);
        System.out.println(fb.result());
    }

}
