import java.util.ArrayList;
import java.util.Collections;

// 无向无权图
public class SingleSourcePath{

    private Graph G;
    private int s;
    private boolean[] visited;
    private int pre[]; // 其实只用 pre 一个数组即可标记访问与否及路径信息
    public SingleSourcePath(Graph G, int s){
        // s是源顶点
        G.validateVertex(s);
        this.G = G;
        this.s = s;
        visited = new boolean[G.V()];
        pre = new int[G.V()];

        for(int i = 0; i < pre.length; i ++)
            pre[i] = -1;
        dfs(s, s);
    }

    private void dfs(int v, int parent){
        // parent是v的上一个顶点
        visited[v] = true;
        pre[v] = parent;
        for(int w: G.adj(v))
            if(!visited[w])
                dfs(w, v);
    }

    public boolean isConnectedTo(int t){
        // 源点是否与 t 连通
        G.validateVertex(t);
        return visited[t];
    }

    public Iterable<Integer> path(int t){
        // 返回记录的路径 s -> t
        ArrayList<Integer> res = new ArrayList<>();
        if(!isConnectedTo(t)) return res; // 没有路径

        int cur = t;
        while(cur != s){
            res.add(cur);
            cur = pre[cur];
        }
        res.add(cur); // 最后的源顶点
        Collections.reverse(res);
        return res;
    }
    public static void main(String args[]){
        Graph g = new Graph("g.txt");
        SingleSourcePath spath = new SingleSourcePath(g, 0);
        System.out.println("0 -> 6: " + spath.path(6));
        System.out.println("0 -> 5: " + spath.path(5));
    }

}
