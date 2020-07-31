import java.util.ArrayList;
import java.util.Collections;
// 无向无权图
public class HL_SC_MemorySearch {
    // 基于状态压缩的哈密顿回路求解

    private Graph G;
    private int []pre;
    private int end; // 回路的最后顶点, 初始为 -1，如果大于0意味着存在哈密顿回路

    public HL_SC_MemorySearch(Graph G){
        this.G = G;
        pre = new int[G.V()];
        end = -1;

        int visited = 0;
        dfs(visited, 0, 0, G.V());
    }

    private boolean dfs(int visited, int v, int parent, int left){// left表示剩下几个顶点未访问
        // 在当前访问状态下，从v开始是否有哈密顿回路
        visited += (1 << v);
        pre[v] = parent;
        left --;

        if(left == 0 && G.hasEdge(v, 0)) {// 所有顶点都被访问过
            end = v;
            return true;
        }
        for(int w: G.adj(v))
            if ((visited & (1 << w)) == 0)// 是否访问过w
                if(dfs(visited, w, v, left)) return true;


        visited -= (1 << v);// visited 不是全局变量了,由于值传递，可删除这个语句
        left ++; // 可以不写,写了也没意义，因为left是函数中传入的参数，返回上层函数后使用的仍然是上层的left
        // 但是如果left是全局成员变量，这里为了回溯就必须写 left ++;

        return false;
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
        HL_SC_MemorySearch hp = new HL_SC_MemorySearch(g);
        System.out.println(hp.result());
    }

}
