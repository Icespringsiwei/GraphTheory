import java.util.Arrays;

public class Dijkstra {
    private WeightedGraph G;
    private int s; // 源点s
    private int dis[]; // 源点到各点的最短距离
    private boolean visited[];

    public Dijkstra(WeightedGraph G, int s){
        this.G = G;
        G.validateVertex(s);
        this.s = s;
        dis = new int[G.V()];
        Arrays.fill(dis, 0x3f3f3f3f);
        dis[s] = 0; // 初始状态

        visited = new boolean[G.V()];
        while(true){
            int curdis = 0x3f3f3f3f, curv = -1;
            for(int v = 0; v < G.V(); v ++)
                if(!visited[v] && dis[v] < curdis){
                    curdis = dis[v];
                    curv = v;
                }
            if(curv == -1) break;

            visited[curv] = true;
            for(int w: G.adj(curv))
                if(!visited[w])
                    if(dis[curv] + G.getWeight(curv, w) < dis[w])
                        dis[w] = dis[curv] + G.getWeight(curv, w);
        }
    }

    public boolean isConnectedTo(int v){
        G.validateVertex(v);
        return visited[v];
    }

    public int distTo(int v){
        // 返回源点 s 到 v 的最短路径
        G.validateVertex(v);
        return dis[v];
    }
    public static void main(String args[]){
        WeightedGraph g = new WeightedGraph("g.txt");
        Dijkstra d = new Dijkstra(g, 0);
        for(int v = 0; v < g.V(); v ++)
            System.out.print(d.distTo(v) + " ");
    }
}
