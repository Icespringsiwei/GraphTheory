import java.util.ArrayList;
import java.util.Stack;

public class EulerPath {
    private Graph G;
    ArrayList<Integer> startAndEnd; // start end,欧拉路径的起点和终点
    boolean isEuler = false, isHalfEuler = false;
    public EulerPath(Graph G){
        this.G = G;
        startAndEnd = new ArrayList<>();
    }
    public void hasEulerPath(){
        // 是否存在欧拉路径
        CC cc = new CC(G);
        if(cc.count() > 1) {
            isEuler = false;// 首先判断连通性
            isHalfEuler = false;
        }
        for(int v = 0; v < G.V(); v ++)
            if(G.degree(v) % 2 == 1)
                startAndEnd.add(v);
        if(startAndEnd.size() == 0){
            isEuler = true; isHalfEuler = true;
        }else if(startAndEnd.size() == 2){
            isHalfEuler = true;
        }
    }
    public ArrayList<Integer> result(){
        // 返回欧拉路径结果
        ArrayList<Integer> res = new ArrayList<>();// 充当Path栈
        hasEulerPath();
        if(!isHalfEuler) return res;
        Graph g = (Graph) G.clone();
        // 删除 g 的边不会影响 G
        Stack<Integer> stack = new Stack<>(); // curPath 栈
        int curv = 0;
        if(startAndEnd.size() == 2)
            curv = startAndEnd.get(0);
        stack.push(curv);
        while (!stack.isEmpty()){
            if(g.degree(curv) != 0){
                // 度不为0说明当前顶点连的还有边，也就是还有路可走
                stack.push(curv);
                int w = g.adj(curv).iterator().next(); // 可迭代列表的第一个元素,即取g的任意邻点
                g.removeEdge(curv, w);
                curv = w;
            }else {
                // curv 到不了其它顶点，则已经找到一个环
                res.add(curv);
                curv = stack.pop();
            }
        }
        return res;
    }
    public static void main(String args[]){
        Graph g = new Graph("g.txt");
        EulerPath ep = new EulerPath(g);
        System.out.println(ep.result());
    }
}
