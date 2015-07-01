package it.ninjatech.kvo;

import java.util.ArrayDeque;
import java.util.Arrays;

public class Codility {

	public static void main(String[] args) {
//		for (int i = 0; i < 5000; i++) {
//			char c = (char)(i + 33);
//			System.out.print(i);
//			System.out.print("=");
//			System.out.print(c);
//			System.out.println("-");
//			System.out.println((int)c - 33);
//		}
		
//		String a = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur sodales ligula in libero. Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem. Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum. Nulla metus metus, ullamcorper vel, tincidunt sed, euismod in, nibh. Quisque volutpat condimentum velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam nec ante. Sed lacinia, urna non tincidunt mattis, tortor neque adipiscing diam, a cursus ipsum ante quis turpis. Nulla facilisi. Ut fringilla. Suspendisse potenti. Nunc feugiat mi a tellus consequat imperdiet. Vestibulum sapien. Proin quam. Etiam ultrices. Suspendisse in justo eu magna luctus suscipit. Sed lectus. Integer euismod lacus luctus magna. Quisque cursus, metus vitae pharetra auctor, sem massa mattis sem, at interdum magna augue eget diam. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Morbi lacinia molestie dui. Praesent blandit dolor. Sed non quam. In vel mi sit amet augue congue elementum. Morbi in ipsum sit amet pede facilisis laoreet. Donec lacus nunc, viverra nec, blandit vel, egestas et, augue. Vestibulum tincidunt malesuada tellus. Ut ultrices ultrices enim. Curabitur sit amet mauris. Morbi in dui quis est pulvinar ullamcorper. Nulla facilisi. Integer lacinia sollicitudin massa. Cras metus. Sed aliquet risus a tortor. Integer id quam. Morbi mi. Quisque nisl felis, venenatis tristique, dignissim in, ultrices sit amet, augue. Proin sodales libero eget ante. Nulla quam. Aenean laoreet. Vestibulum nisi lectus, commodo ac, facilisis ac, ultricies eu, pede. Ut orci risus, accumsan porttitor, cursus quis, aliquet eget, justo. Sed pretium blandit orci. Ut eu diam at pede suscipit sodales. Aenean lectus elit, fermentum non, convallis id, sagittis at, neque. Nullam mauris orci, aliquet et, iaculis et, viverra vitae, ligula. Nulla ut felis in purus aliquam imperdiet. Maecenas aliquet mollis lectus. Vivamus consectetuer risus et tortor. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur sodales ligula in libero. Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem. Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum. Nulla metus metus, ullamcorper vel, tincidunt sed, euismod in, nibh. Quisque volutpat condimentum velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam nec ante. Sed lacinia, urna non tincidunt mattis, tortor neque adipiscing diam, a cursus ipsum ante quis turpis. Nulla facilisi. Ut fringilla. Suspendisse potenti. Nunc feugiat mi a tellus consequat imperdiet. Vestibulum sapien. Proin quam. Etiam ultrices. Suspendisse in justo eu magna luctus suscipit. Sed lectus. Integer euismod lacus luctus magna. Quisque cursus, metus vitae pharetra auctor, sem massa mattis sem, at interdum magna augue eget diam. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Morbi lacinia molestie dui. Praesent blandit dolor. Sed non quam. In vel mi sit amet augue congue elementum. Morbi in ipsum sit amet pede facilisis laoreet. Donec lacus nunc, viverra nec, blandit vel, egestas et, augue. Vestibulum tincidunt malesuada tellus. Ut ultrices ultrices enim. Curabitur sit amet mauris. Morbi in dui quis est pulvinar ullamcorper. Nulla facilisi. Integer lacinia sollicitudin massa. Cras metus. Sed aliquet risus a tortor. Integer id quam. Morbi mi. Quisque nisl felis, venenatis tristique, dignissim in, ultrices sit amet, augue. Proin sodales libero eget ante. Nulla quam. Aenean laoreet. Vestibulum nisi lectus, commodo ac, facilisis ac, ultricies eu, pede. Ut orci risus, accumsan porttitor, cursus quis, aliquet eget, justo. Sed pretium blandit orci. Ut eu diam at pede suscipit sodales. Aenean lectus elit, fermentum non, convallis id, sagittis at, neque. Nullam mauris orci, aliquet et, iaculis et, viverra vitae, ligula. Nulla ut felis in purus aliquam imperdiet. Maecenas aliquet mollis lectus. Vivamus consectetuer risus et tortor. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa. Vestibulum lacinia arcu eget nulla. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Curabitur sodales ligula in libero. Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem. Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum. Nulla metus metus, ullamcorper vel, tincidunt sed, euismod in, nibh. Quisque volutpat condimentum velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam nec ante. Sed lacinia, urna non tincidunt mattis, tortor neque adipiscing diam, a cursus ipsum ante quis turpis. Nulla facilisi. Ut fringilla. Suspendisse potenti. Nunc feugiat mi a tellus consequat imperdiet. Vestibulum sapien. Proin quam. Etiam ultrices. Suspendisse in justo eu magna luctus suscipit. Sed lectus. Integer euismod lacus luctus magna. Quisque cursus, metus vitae pharetra auctor, sem massa mattis sem, at interdum magna augue eget diam. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Morbi lacinia molestie dui. Praesent blandit dolor. Sed non quam. In vel mi sit amet augue congue elementum. Morbi in ipsum si.";
//		System.out.println(Solution.solution(a));
		
		int[] a = new int[] {5, 1, 3};
		System.out.println(Solution.solution3(a));
		
//		String a = "1+*";
//		System.out.println(Solution.solution2(a));
	}
	
	public static class Solution {
		
	    public static String solution(String S) {
	    	char[] result = new char[S.length()];
	    	
	    	int lastIndex = 0;
	    	for (int i = 0; i < S.length(); i++) {
	    		char c = S.charAt(i);
	    		result[i] = ' ';
	    		if (c == ' ') {
	    			result[lastIndex] = (char)(i - lastIndex + 33);
	    			lastIndex = i + 1;
	    		}
	    	}
	    	result[lastIndex] = (char)(S.length() - lastIndex + 33);
	    	
	    	int pointer = 0;
	    	do {
	    		int wordLength = (int)result[pointer] - 33;
	    		
	    		for (int i = pointer; i < pointer + wordLength; i++) {
	    			result[i] = S.charAt(wordLength + pointer - (i - pointer) - 1);
	    		}
	    		pointer += wordLength + 1;
	    		
	    	} while (pointer < result.length);
	    	
	    	return new String(result);
	    }

	    public static int solution2(String S) {
	    	if (S.length() < 3) {
	    		return -1;
	    	}
	    	
	    	ArrayDeque<Long> deque = new ArrayDeque<>();
	    	for (int i = 0; i < S.length(); i++) {
	    		char c = S.charAt(i);
	    		switch (c) {
	    		case '0':
	    		case '1':
	    		case '2':
	    		case '3':
	    		case '4':
	    		case '5':
	    		case '6':
	    		case '7':
	    		case '8':
	    		case '9':
	    			deque.push((long)c - 48);
	    			break;
	    		case '+':
	    			if (deque.size() < 2) {
	    				return -1;
	    			}
	    			long sr = deque.pop() + deque.pop();
	    			if (sr > Integer.MAX_VALUE || sr < Integer.MIN_VALUE) {
	    				return -1;
	    			}
	    			deque.push(sr);
	    			break;
	    		case '*':
	    			if (deque.size() < 2) {
	    				return -1;
	    			}
	    			long mr = deque.pop() * deque.pop();
	    			if (mr > Integer.MAX_VALUE || mr < Integer.MIN_VALUE) {
	    				return -1;
	    			}
	    			deque.push(mr);
	    			break;
	    		}
	    	}
	    	
	    	if (deque.size() > 1) {
	    		return -1;
	    	}
	    	
	    	return deque.pop().intValue();
	    }
	    
	    public static boolean solution3(int[] A) {
	    	boolean result = false;
	    	
	    	int[] copyOfA = new int[A.length];
	    	for (int i = 0; i < A.length; i++) {
	    		copyOfA[i] = A[i];
	    	}
	    	Arrays.sort(copyOfA);
	    	
	    	int differences = 0;
	    	
	    	for (int i = 0; i < A.length; i++) {
	    		if (A[i] != copyOfA[i]) {
	    			differences++;
	    		}
	    	}

	    	result = differences == 2 || differences == 0;
	    	
	    	return result;
	    }
	    
	}
	
}

