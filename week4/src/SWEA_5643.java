import java.util.*;
import java.io.*;

class SWEA_5643 {
    static ArrayList<Integer>[] adj; // 정방향 노드 (작은 것 -> 큰 것)
    static ArrayList<Integer>[] revAdj; // 역방향 노드 (큰 것 -> 작은 것)
    static int N, M;
    
	public static void main(String args[]) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
		int T = Integer.parseInt(br.readLine());

		for(int tc = 1; tc <= T; tc++) {
            N = Integer.parseInt(br.readLine()); // 학생들의 수
            M = Integer.parseInt(br.readLine()); // 비교한 횟수
			
            // adj와 revAdj 초기화
            adj = new ArrayList[N+1]; // 크기 지정
            revAdj = new ArrayList[N+1];
            for(int i = 1; i <= N; i++) {
                adj[i] = new ArrayList<>();
                revAdj[i] = new ArrayList<>();
            }
            
            // a b 입력받은 후 연관관계 만들기
            for (int i = 0; i < M; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                
                adj[from].add(to);
                revAdj[to].add(from);
            }
            
            // 자신보다 작은 사람과 자신보다 큰 사람의 합이 전체 인원에서 -1한 값이 동일한지 확인
            int answer = 0;
            for(int i = 1; i <= N; i++) {
                if(bfs(i, adj) + bfs(i, revAdj) == N - 1) {
                    answer++;
                }
            }
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }
 		System.out.print(sb);       
	}
    
    // start에서 방문할 수 있는 노드의 수 출력
    static int bfs(int start, ArrayList<Integer>[] graph) {
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[N+1]; // 각 노드별 방문 여부
        queue.add(start);
        visited[start] = true;
        
        int count = 0; // 방문 노드 수
        while(!queue.isEmpty()) {
            int curr = queue.poll();
            for(int next : graph[curr]) { // next : 현재 노드와 연결된 노드
                if(!visited[next]) {
                    visited[next] = true; // 다음 노드 방문
                    queue.add(next); // 그 다음 노드 방문을 위해 큐에 추가
                    count++; // 방문 노드 수 +1
                }
            }
        }
        
        return count;
    }
}