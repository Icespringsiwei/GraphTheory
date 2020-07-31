import java.util.ArrayList;
import java.util.Collections;
// 无向无权图
public class HLpath {

    private Graph G;
    private int s; // 源点
    private boolean[] visited;
    private int []pre;
    private int end; // 回路的最后顶点, 初始为 -1，如果大于0意味着存在哈密顿路径

    public HLpath(Graph G, int s){
        this.G = G;
        this.s = s;
        visited = new boolean[G.V()];
        pre = new int[G.V()];
        end = -1;
        dfs(s, 0, G.V());
    }

    private boolean dfs(int v, int parent, int left){// left表示剩下几个顶点未访问
        // 是否有哈密顿路径
        visited[v] = true;
        pre[v] = parent;
        left --;

        if(left == 0) {// 所有顶点都被访问过
            end = v;
            return true;
        }
        for(int w: G.adj(v))
            if (!visited[w])
                if(dfs(w, v, left)) return true;


        visited[v] = false;// 从v出发寻找哈密顿路径失败,回溯
        left ++; // 可以不写,写了也没意义，因为left是函数中传入的参数，返回上层函数后使用的仍然是上层的left
        // 但是如果left是成员变量，这里为了回溯就必须写 left ++;
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
        if(end == -1) // 没有哈密顿路径
            return res;
        int cur = end;
        while(cur != s){
            res.add(cur);
            cur = pre[cur];
        }
        res.add(cur);
        Collections.reverse(res);
        return res;
    }

    public static void main(String args[]){
        Graph g = new Graph("g.txt");
        HLpath hp = new HLpath(g,3);
        System.out.println(hp.result());
    }

}
