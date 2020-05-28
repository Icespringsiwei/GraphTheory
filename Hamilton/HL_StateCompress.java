import java.util.ArrayList;
import java.util.Collections;
// 无向无权图
public class HL_StateCompress {
    // 基于状态压缩的哈密顿回路求解

    private Graph G;
    private int visited;
    private int []pre;
    private int end; // 回路的最后顶点, 初始为 -1，如果大于0意味着存在哈密顿回路

    public HL_StateCompress(Graph G){
        this.G = G;
        visited = 0;
        pre = new int[G.V()];
        end = -1;
        dfs(0, 0, G.V());
    }

    private boolean dfs(int v, int parent, int left){// left表示剩下几个顶点未访问
        // 是否有哈密顿回路
        visited += (1 << v);
        pre[v] = parent;
        left --;

        if(left == 0 && G.hasEdge(v, 0)) {// 所有顶点都被访问过
            end = v;
            return true;
        }
        for(int w: G.adj(v))
            if ((visited & (1 << w)) == 0)// 是否访问过w
                if(dfs(w, v, left)) return true;

        visited -= (1 << v);// 从v出发寻找哈密顿回路失败,回溯
        left ++; // 可以不写,写了也没意义，因为left是函数中传入的参数，返回上层函数后使用的仍然是上层的left
        // 但是如果left是成员变量，这里为了回溯就必须写 left ++;
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
        HL_StateCompress hp = new HL_StateCompress(g);
        System.out.println(hp.result());
    }

}
