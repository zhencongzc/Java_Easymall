import org.junit.Test;

import java.util.Scanner;

import static java.lang.StrictMath.max;

class Test1 {

    public static void main(String[] args) {

        while (true) {
            Scanner s = new Scanner(System.in);
            String s1 = s.nextLine();
            System.out.println(lengthOfLongestSubstring2(s1));
        }
    }

    static int lengthOfLongestSubstring2(String s) {
        int start = 0, end = 0, cur_len = 0, max_len = 0;
        char[] arr = s.toCharArray();
        while (end < arr.length) {
            char cur_char = arr[end];

            // 关注当前位置前的最长无重复子串中是否有和当前位置字符重复的情况
            for (int i = start; i < end; i++) {
                if (arr[i] == cur_char) {
                    start = i + 1;
                    cur_len = end - start;
                    break;
                }
            }
            end++;
            cur_len++;
            max_len = max(max_len, cur_len);
        }
        return max_len;
    }
}
