// 프로그래머스 섬 연결하기
import java.util.*;

public class PGS_섬연결하기 {
    int[] parent;
    
    public int solution(int n, int[][] costs) {
        int answer = 0;
    
        Arrays.sort(costs, Comparator.comparingInt((int[] c) -> c[2])); // costs[i][2]를 기준으로 오름차순 정렬
        
        // 각 노드의 부모 노드
        parent = new int[n];
        
        // 처음에는 자기 자신을 초기화
        for(int i = 0; i < n; i++) {
            parent[i] = i;
        }
        
        int selectedEdgeCnt = 0; // 선택된 간선의 개수
        
        for(int [] c : costs) {
            int a = c[0]; // 노드
            int b = c[1]; // 노드
            int cost = c[2]; // 비용
            
            int aParent = findParent(a);
            int bParent = findParent(b);
            
            if(aParent == bParent) continue; // 부모가 같다면 간선을 못 이음
            
            answer += cost; // 두 노드끼리 연결
            parent[bParent] = aParent; // 두 노드의 부모를 동일하게 설정
            
            // 최소비용을 이루는 간선을 순회한 이후에도 끝까지 순회하는 비효율 방지
            selectedEdgeCnt++;
            if(selectedEdgeCnt == n - 1) break;
        }
        
        return answer;
    }
    
    // 각 노드들에 대한 부모 찾기
    private int findParent(int node) {
        if(parent[node] == node) return node; // 자기 자신이 부모(0)
        
        return parent[node] = findParent(parent[node]); // 재귀호출을 통해 노드를 계속 올라감
    }
}
