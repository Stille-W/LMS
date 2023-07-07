package com.reality;

import org.apache.commons.collections4.MapUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Demo {

    public static void main(String[] args) {
//        int[] nums = {3,2,4};
//        int target = 6;
//        int[] result = twoSum(nums, target);
//        System.out.println(result[0]+","+result[1]);

//        String s ="abcabcbb";
//        System.out.println(lengthOfLongestSubstring(s));
    }

    public static int[] twoSum(int[] nums, int target) {
//        for (int i=0;i<nums.length-1;i++) {
//            for (int j=i+1;j<nums.length;j++) {
//                if (nums[i]+nums[j]==target) {
//                    return new int[]{i, j};
//                }
//            }
//        }
//        return new int[]{0,0};

        Map<Integer, Integer> map = new HashMap<>();
        for (int i=0;i<nums.length;i++) {
            System.out.println(i+","+nums[i]);

            if(map.containsKey(target-nums[i])){
                return new int[]{map.get(target-nums[i]),i};
            }
            map.put(nums[i],i);
        }
        return new int[]{0,0};
    }

    public static int lengthOfLongestSubstring(String s) {
//        List<String> strList2 = Arrays.stream(s.split("")).toList();
//        int count =0;
//        Map<String, Integer> map = new HashMap<>();
//        for (int i=0; i<strList2.size(); i++){
//            System.out.println(":"+strList2.get(i)+","+count);
//            if (map.containsKey(strList2.get(i)) && count<map.size()){
//                count = map.size();
//                System.out.println(map.keySet()+","+count);
//                map.clear();
//            }
//            map.put(strList2.get(i), i);
//        }
//        return count;

        int count =0;
        Map<String, Integer> map = new HashMap<>();
        for (int i=0; i<s.length(); i++){
            if (map.containsKey(s.split("")[i]) && count<map.size()){
                count = map.size();
                map.clear();
            }
            map.put(s.split("")[i], i);
        }
        return count;
    }

    class palindrome {
        static int max = 0;
        static int start = 0;
        public static String longestPalindrome(String s) {
            char[] chars = s.toCharArray();


            for (int i=0;i<chars.length;i++){
                expand(chars, i, i);
                expand(chars, i, i+1);
            }

            return s.substring(start, start+max-2);
        }
        private static void expand(char[] chars, int left, int right) {
            while (left>=0 && right<chars.length && chars[left]==chars[right]){
                left--;
                right++;
            }
            if (max<right-left+1) {
                max = right-left+1;
                start = left+1;
            }
        }
    }

    public int maxArea(int[] height) {
        int max = 0;

        for (int i = 0; i < height.length; i++) {
            int w = height.length-i-1;
            int h = Math.min(height[i], height[i+w]);
            if (max<h*w){
                max =w*h;
            }
        }
        return max;
    }

    public List<List<Integer>> threeSum(int[] nums) {


        return new ArrayList<List<Integer>>();
    }
}