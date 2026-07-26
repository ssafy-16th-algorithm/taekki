package boj;

import java.io.*;
import java.util.*;

// 선수과목
public class boj_14567 {

	public static void main(String[] args) throws IOException {
		int n, m; // 전체 과목, 선수 과목
		int[] semester; // 과목 당 수강하는 학기
		int[] child; // 선수 과목 개수
		List<List<Integer>> graph = new ArrayList<>();
		Queue<Integer> q = new LinkedList<>();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 전체 과목과 선수 과목 입력
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		// 크기 지정
		semester = new int[n+1];
		child = new int[n+1];
		
		for(int i = 0; i <= n; i++) {
			graph.add(new ArrayList<>());
		}
		
		for(int i = 0; i < m; i++) {
			// A과목과 B과목 입력
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			graph.get(a).add(b); // a 과목에서 뻗어나가는 b 과목 추가
			child[b]++; // b 과목의 선수과목 개수 증가
		}
		
		for(int i = 1; i <= n; i++) {
			// 선수 과목이 하나도 없는 과목을 q에 추가
			if(child[i] == 0) {
				q.offer(i);
				semester[i] = 1; // 1학기 수강
			}
		}
		
		while(!q.isEmpty()) {
			int cur = q.poll();
			
			// 해당 과목의 선수과목을 가져와 
			for(int g : graph.get(cur)) {
				child[g]--; // 연결된 과목 개수 감소
				semester[g] = Math.max(semester[g], semester[cur]+1); // 다음 과목의 수강 학기를 현재 과목의 수강 다음 학기로 변경
				
				if(child[g] == 0)
					q.offer(g);
			}
		}
		
		for(int i = 1; i <= n; i++) {
			sb.append(semester[i]).append(" ");
		}
		
		System.out.println(sb);

	}

}
