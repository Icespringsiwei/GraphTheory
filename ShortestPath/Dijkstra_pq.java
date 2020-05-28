import java.util.*;

public class Dijkstra_pq {
    private WeightedGraph G;
    private int s; // 源点s
    private int dis[]; // 源点到各点的最短距离
    private boolean visited[];
    private int pre[];
    private class Node implements Comparable<Node>{
        public int v, dis;
        public Node(int v, int dis){
            this.v = v;
            this.dis = dis;
        }
        @Override
        public int compareTo(Node another){
            return this.dis - another.dis;
        }
    }
    public Dijkstra_pq(WeightedGraph G, int s){
        this.G = G;
        G.validateVertex(s);
        this.s = s;
        dis = new int[G.V()];
        Arrays.fill(dis, 0x3f3f3f3f);
        pre = new int[G.V()];
        Arrays.fill(pre, -1);

        dis[s] = 0; // 初始状态
        pre[s] = s;

        visited = new boolean[G.V()];
        Queue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(s, 0));

        while(!pq.isEmpty()){

            int curv = pq.remove().v;
            if(visited[curv]) continue;
            visited[curv] = true;
            for(int w: G.adj(curv))
                if(!visited[w])
                    if(dis[curv] + G.getWeight(curv, w) < dis[w]) {
                        dis[w] = dis[curv] + G.getWeight(curv, w);
                        pre[w] = curv;
                        pq.add(new Node(w, dis[w]));

                    }
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

    public Iterable<Integer> path(int t){
        ArrayList<Integer>res = new ArrayList<>();
        if(!isConnectedTo(t)) return res;
        int cur = t;
        while(cur !=s){
            res.add(cur);
            cur = pre[cur];
        }
        res.add(s);
        Collections.reverse(res);
        return res;
    }
    public static void main(String args[]){
        WeightedGraph g = new WeightedGraph("g.txt");
        Dijkstra_pq d = new Dijkstra_pq(g, 0);
        for(int v = 0; v < g.V(); v ++)
            System.out.print(d.distTo(v) + " ");
        System.out.println();
        System.out.println(d.path(4));
    }
}
