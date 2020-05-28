import java.util.ArrayList;
import java.util.HashSet;

// 无向无权图
public class FindCutPoints {

    private Graph G;
    private boolean[] visited;
    private int ord[];
    private int low[];
    private HashSet<Integer> res; // 存储桥边
    private int cnt; // 记录顶点的访问顺序值，其实就是当前已经遍历的顶点数(0开始)

    public FindCutPoints(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        res = new HashSet<>();
        ord = new int[G.V()];
        low = new int[G.V()];
        cnt = 0;

        for(int v = 0; v < G.V(); v ++)
            if(!visited[v])// 在各个连通分量中寻找桥
                dfs(v, v); // 根节点的parent是自己
    }

    private void dfs(int v, int parent){
        visited[v] = true;
        ord[v] = cnt ++;// ord[v] = cnt; cnt ++;
        low[v] = ord[v];

        int rootchild = 0;
        for(int w: G.adj(v))
            if(!visited[w]) {
                dfs(w, v);
                low[v] = Math.min(low[v], low[w]);

                rootchild ++;
                if(v == parent && rootchild > 1) // 根节点的单独处理
                    res.add(v);

                if(v != parent && low[w] >= ord[v])// v 不是根节点
                    res.add(v);// v是割点

            }else if (w != parent){// w 被访问过但不是它的parent节点
                low[v] = Math.min(low[v], low[w]);// 由于v和w相邻，故w能到达的最小ord，v也能到达
            }
    }
    public HashSet<Integer> result(){
        return res;
    }
    public static void main(String args[]){
        Graph g = new Graph("g.txt");
        FindCutPoints fc = new FindCutPoints(g);
        System.out.println(fc.result());
    }

}
