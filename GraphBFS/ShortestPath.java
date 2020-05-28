import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class ShortestPath {
    private Graph G;
    private int s;
    private boolean[] visited;
    private int[] pre;
    private int[] dis;

    public ShortestPath(Graph G, int s){
        this.G = G;
        this.s = s;
        visited = new boolean[G.V()];
        dis = new int[G.V()];
        pre = new int[G.V()];

        for(int i = 0; i < G.V(); i ++) {
            pre[i] = -1;
            dis[i] = -1;
        }

        bfs(s);
    }

    private void bfs(int s){
        // 从 s 开始进行BFS
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        pre[s] = s;
        dis[s] = 0;

        while(!queue.isEmpty()) {
            int v = queue.remove(); // 取队头

            for (int w : G.adj(v))
                if (!visited[w]) {
                    queue.add(w);
                    visited[w] = true;
                    pre[w] = v;
                    dis[w] = dis[v] + 1;// 距离加一
                }
        }
    }

    public boolean isConnectedTo(int t){
        G.validateVertex(t);
        return visited[t];// 意味着 s 与 t 在同一连通分量;
    }

    public Iterable<Integer> path(int t){
        // s -> t 的路径

        ArrayList<Integer> res = new ArrayList<>();
        if(!isConnectedTo(t)) return res;
        int cur = t;
        while(cur != s){
            res.add(cur);
            cur = pre[cur];
        }
        res.add(s);
        Collections.reverse(res);
        return res;
    }
    public int dis(int t){
        // s -> t 的距离，不关注路径，只关注最短距离
        G.validateVertex(t);
        return dis[t];

    }
    public static void main(String[] args){
        Graph g = new Graph("g.txt");
        ShortestPath sp = new ShortestPath(g,0);
        System.out.println("0 -> 3: " + sp.dis(3));
    }
}
