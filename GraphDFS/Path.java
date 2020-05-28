import java.util.ArrayList;
import java.util.Collections;

public class Path {
    private Graph G;
    private int s;
    private int t;
    private boolean[] visited;
    private int pre[]; // 其实只用 pre 一个数组即可标记访问与否及路径信息
    public Path(Graph G, int s, int t){
        // s是源顶点
        G.validateVertex(s);
        G.validateVertex(t);
        this.G = G;
        this.s = s;
        this.t = t;

        visited = new boolean[G.V()];
        pre = new int[G.V()];

        for(int i = 0; i < pre.length; i ++)
            pre[i] = -1;
        dfs(s, s);
    }

    private boolean dfs(int v, int parent){
        // 单源路径的优化，提前终止递归
        // 看 v 是否能到 t
        visited[v] = true;
        pre[v] = parent;
        if(v == t) return true;
        for(int w: G.adj(v))
            if(!visited[w])
                if(dfs(w, v)) return true;
        return false;
    }

    public boolean isConnected(){
        // 源点是否与 t 连通
        return visited[t];
    }

    public Iterable<Integer> path(){
        // 返回记录的路径 s -> t
        ArrayList<Integer> res = new ArrayList<>();
        if(!isConnected()) return res; // 没有路径

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
        Path path = new Path(g, 0, 5);
        System.out.println("0 -> 5: " + path.path());

    }

}
