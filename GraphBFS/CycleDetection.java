import java.util.LinkedList;
import java.util.Queue;

public class CycleDetection {
    private Graph G;
    private boolean[] visited;
    private int[] pre;
    private boolean hasCycle = false;

    public CycleDetection(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        pre = new int[G.V()];
        for(int v = 0; v < G.V(); v ++)
            pre[v] = -1;

        for(int v = 0; v < G.V(); v ++)
            if(!visited[v])
                if(bfs(v)){ // 如果通过BFS检测出环
                    hasCycle = true;
                    break;
                }
    }
    private boolean bfs(int s){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        pre[s] = s;
        while(!queue.isEmpty()){
            int v = queue.remove();
            for(int w: G.adj(v)){
                if(!visited[w]){
                    queue.add(w);
                    visited[w] = true;
                    pre[w] = v;
                }
                // 这里是逻辑的关键
                // 如果 w 已经被访问过了，我们还必须判断，w 不是 v 的上一个节点
                // 如果 w 不是 v 的上一个节点，说明我们找到了一个环
                else if(pre[v] != w)
                    return true;
            }
        }
        return false;
    }
    public boolean hasCycle(){
        return hasCycle;
    }
    public static void main(String[] args) {

        Graph g = new Graph("g.txt");
        CycleDetection cycleDetection = new CycleDetection(g);
        System.out.println(cycleDetection.hasCycle());
    }

}
