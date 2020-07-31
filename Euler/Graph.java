/*Ice_spring 2020/3/26*/
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

// 用于欧拉图的无向无权图
// 有删除边的功能, 另外为了在寻找欧拉路径删除边的过程中不破坏原图，用Cloneable,重写clone函数为生拷贝
public class Graph implements Cloneable{
    private int V; // 顶点数
    private int E; // 边数
    private TreeSet<Integer>[] adj; // 邻接矩阵

    public Graph(String filename){
        File file = new File(filename);
        try(Scanner scanner = new Scanner(file)){

            V = scanner.nextInt();
            if(V < 0) throw new IllegalArgumentException("V must be a non-neg number!");
            adj = new TreeSet[V];

            for(int i = 0; i < V; i ++)
                adj[i] = new TreeSet<>();
            E = scanner.nextInt();
            if(E < 0) throw new IllegalArgumentException("E must be a non-neg number!");
            for(int i=0; i < E; i ++){
                int a = scanner.nextInt();
                validateVertex(a);
                int b = scanner.nextInt();
                validateVertex(b);
                // 本代码只处理简单图
                if(a == b) throw new IllegalArgumentException("检测到self-loop边！");
                if(adj[a].contains(b)) throw new IllegalArgumentException("Parallel Edges are detected!");
                adj[a].add(b);
                adj[b].add(a);
            }
        }
        catch(IOException e){
            e.printStackTrace();//打印异常信息
        }
    }
    public void validateVertex(int v){
        // 判断顶点v是否合法
        if(v < 0 ||v >= V)
            throw new IllegalArgumentException("vertex " + v + "is invalid!");
    }
    public int V(){ // 返回顶点数
        return V;
    }
    public int E(){
        return E;
    }
    public boolean hasEdge(int v, int w){
        // 顶点 v 到 w 是存在边
        validateVertex(v);
        validateVertex(w);
        return adj[v].contains(w);
    }
    public Iterable<Integer> adj(int v){
        // 返回值可以是TreeSet，不过用 Iterable 接口更能体现面向对象
        // 返回和顶点 v 相邻的所有顶点
        validateVertex(v);
        return adj[v];
    }
    public int degree(int v){
        // 返回节点 v 的度，即与该顶点相邻的顶点个数
        validateVertex(v);
        return adj[v].size(); //
    }
    public void removeEdge(int v, int w){
        // 删除 v-w 边
        validateVertex(v);
        validateVertex(w);
        if(adj[v].contains(w)) E --;
        adj[v].remove(w);
        adj[w].remove(v);
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("V = %d, E = %d\n",V, E));
        for(int v = 0; v < V; v ++){
            // 编程好习惯 i,j,k 作索引, v,w 作顶点
            sb.append(String.format("%d : ", v));

            for(int w: adj[v])
                sb.append(String.format("%d ", w));

            sb.append('\n');
        }
        return sb.toString();
    }
    @Override
    public Object clone() {
        try {
            Graph cloned = (Graph) super.clone();
            cloned.adj = new TreeSet[V];
            for(int v = 0; v < V; v ++){
                cloned.adj[v] = new TreeSet<>();
                for(int w: this.adj[v])
                    cloned.adj[v].add(w);
            }
            return cloned;
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String args[]){
        Graph g = new Graph("g.txt");
        System.out.println(g);
    }
}
