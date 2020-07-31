import java.util.ArrayList;
import java.util.Collections;
// 无向无权图
public class HamiltonLoop {

    private Graph G;
    private boolean[] visited;
    private int []pre;
    private int end; // 回路的最后顶点, 初始为 -1，如果大于0意味着存在哈密顿回路

    public HamiltonLoop(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        pre = new int[G.V()];
        end = -1;
        dfs(0, 0);
    }

    private boolean dfs(int v, int parent){
        // 是否有哈密顿回路
        visited[v] = true;
        pre[v] = parent;

        for(int w: G.adj(v)) {
            if (!visited[w]) {
                if(dfs(w, v)) return true;
            } else if (w == 0 && allVisited()) {// 回到初始点并且所有顶点都被访问过
                end = v;
                return true;
            }
        }
        visited[v] = false;// 从v出发寻找哈密顿回路失败,回溯
        return false;
    }

    private boolean allVisited(){
        // 所有顶点是否都被访问过
        for(int v = 0; v < G.V(); v ++)
            if(!visited[v]) return false;
        return true;
    }

    public ArrayList<Integer> result(){
        ArrayList<Integer> res = new ArrayList<>();
        if(end == -1) // 没有哈密顿回路
            return res;
        int cur = end;
        while(cur != 0){
            res.add(cur);
            cur = pre[cur];
        }
        res.add(0);
        Collections.reverse(res);
        return res;
    }

    public static void main(String args[]){
        Graph g = new Graph("g.txt");
        HamiltonLoop hp = new HamiltonLoop(g);
        System.out.println(hp.result());
    }

}
