// 684. Redundant Connection
// 부모가 같다면 이미 연결됐다는 의미이기 때문에
// 간선 형성하지 말기 (사이클 형성 방지)
class LTC_684 {
    static int[] parent;

    // 부모 노드 찾기
    public static int find(int x) {
        if(parent[x] == x) {
            return x;
        } // 자기 자신이 부모
        else {
            return parent[x] = find(parent[x]);
        } // 재귀를 통해 부모노드를 계속해서 찾아감
    }

    public static boolean union(int x, int y) {
        x = find(x);
        y = find(y);

        if(x == y) { // 부모가 같다면
            return false;
        }
        parent[x] = y;
        
        return true;
    }

    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        int[] answer = {0, 0};

        // 처음에는 자기 자신으로 초기화
        parent = new int[n+1];
        for(int i = 1; i <= n; i++) {
            parent[i] = i;
        }

        // 간선을 생성할 필요가 없는 노드 찾기
        for(int i = 0; i < n; i++) {
            int a = edges[i][0];
            int b = edges[i][1];

            if(!union(a, b)) {
                answer = new int[] {a, b};
            }
        }

        return answer;
    }
}