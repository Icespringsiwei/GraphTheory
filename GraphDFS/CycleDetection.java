
// 无向无权图
public class CycleDetection {

    private Graph G;
    private boolean hasCycle;
    private boolean[] visited;

    public CycleDetection(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        for(int v = 0; v < G.V(); v ++)
            if(!visited[v])
                if(dfs(v, v)) {
                    hasCycle = true;
                    break;
                }
    }

    private boolean dfs(int v, int parent){
        visited[v] = true;

        for(int w: G.adj(v))
            if(!visited[w]) {
                if (dfs(w, v))
                    return true;
            }
            else if(w != parent) // w 的邻点访问过且不是 w 的上一个顶点，则找到环
                return true;
        return false;
    }

    public boolean hasCycle(){
        return hasCycle;
    }

    public static void main(String args[]){
        Graph g = new Graph("g.txt");
        CycleDetection cd = new CycleDetection(g);
        System.out.println(cd.hasCycle());
    }

}
