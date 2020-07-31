import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BellmanFord {
    private WeightedGraph G;
    private int s;
    private int dis[];
    int pre[];
    private boolean hasNegativeCycle;

    public BellmanFord(WeightedGraph G, int s){
        this.G = G;
        G.validateVertex(s);
        this.s = s;
        dis = new int[G.V()];
        Arrays.fill(dis, 0x3f3f3f3f);
        dis[s] = 0;

        pre = new int[G.V()];
        Arrays.fill(pre, -1);
        pre[s] = s;

        for(int i = 1; i < G.V(); i ++){// V - 1 轮松弛操作

            for(int v = 0; v < G.V(); v ++)
                for(int w: G.adj(v))// 避免无穷加无穷溢出
                    if(dis[v] != 0x3f3f3f3f && dis[v] + G.getWeight(v, w) < dis[w]) {
                        dis[w] = dis[v] + G.getWeight(v, w);
                        pre[w] = v;
                    }

        }
        // 再进行一次松弛操作，如果dis发生更新说明存在负权环
        for(int v = 0; v < G.V(); v ++)
            for(int w: G.adj(v))// 避免无穷加无穷溢出
                if(dis[v] != 0x3f3f3f3f && dis[v] + G.getWeight(v, w) < dis[w])
                    hasNegativeCycle = true;
    }
    public boolean hasNegCycle(){
        // 是否有负权环
        return hasNegativeCycle;
    }

    public boolean isConnectedTo(int v){
        G.validateVertex(v);
        return dis[v] != 0x3f3f3f3f;
    }

    public int disTo(int v){
        // 源点到 v 的距离
        G.validateVertex(v);
        if(hasNegativeCycle)
            throw new RuntimeException("exist negative cycle!");
        return dis[v];
    }

    public Iterable<Integer>path(int t){
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
        BellmanFord bf = new BellmanFord(g, 0);
        if(!bf.hasNegCycle())
            for(int v = 0; v < g.V(); v ++)
                System.out.print(bf.disTo(v) + " ");
        System.out.println();
        System.out.println(bf.path(3));
    }
}
