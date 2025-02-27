/*
 * Given a string s of length n and an integer k, determine whether it is possible to select k disjoint special substrings.

A special substring is a
substring
where:

    Any character present inside the substring should not appear outside it in the string.
    The substring is not the entire string s.

Note that all k substrings must be disjoint, meaning they cannot overlap.

Return true if it is possible to select k such disjoint special substrings; otherwise, return false.

 

Example 1:

Input: s = "abcdbaefab", k = 2

Output: true

Explanation:

    We can select two disjoint special substrings: "cd" and "ef".
    "cd" contains the characters 'c' and 'd', which do not appear elsewhere in s.
    "ef" contains the characters 'e' and 'f', which do not appear elsewhere in s.

Example 2:

Input: s = "cdefdc", k = 3

Output: false

Explanation:

There can be at most 2 disjoint special substrings: "e" and "f". Since k = 3, the output is false.

Example 3:

Input: s = "abeabe", k = 0

Output: true

 

Constraints:

    2 <= n == s.length <= 5 * 104
    0 <= k <= 26
    s consists only of lowercase English letters.


 */

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class pair{
    int len;
    int ele;
    int l;
    int r;
    pair(int len,int ele,int l,int r)
    {
        this.len=len;
        this.ele=ele;

        this.l=l;
        this.r=r;
    }

   
}
class Soln{




    public boolean canChoose(String s, int k) {
        int n = s.length();
        int[] start = new int[26];
        int [] end = new int[26];

        for(int i = 0; i < n; i++){
            int c = s.charAt(i) - 'a';
            if(start[c] == 0){
                start[c] = i + 1;
            }
            end[c] = i + 1;
        }

        Comparator<pair> comparator = (p1, p2) -> {
            if (p1.len != p2.len)
                return Integer.compare(p1.len, p2.len); // Sort by len first
            return Integer.compare(p1.ele, p2.ele); // If len is same, sort by ele
        };

        Set<pair> set = new TreeSet<>(comparator);
        for(int i=0;i<26;i++)
        {

        //    System.out.println((char)(i+'a')+" "+start[i]+" "+end[i]);

        start[i]--;
        end[i]--;

        }
        for(int i=0;i<26;i++)
        {
            if(start[i]!=-1)
            {
                int min=start[i];
                int max=end[i];

                for(int j=start[i];j<=end[i];j++)
                {
                    min=Math.min(min,start[s.charAt(j)-'a']);
                    max=Math.max(max,end[s.charAt(j)-'a']);
                }
                
                start[i]=min;
                end[i]=max;
            }
        }
        for(int i=0;i<26;i++)
        {
            if(start[i]!=-1)
            {
                int min=start[i];
                int max=end[i];

                for(int j=start[i];j<=end[i];j++)
                {
                    min=Math.min(min,start[s.charAt(j)-'a']);
                    max=Math.max(max,end[s.charAt(j)-'a']);
                }
                set.add(new pair(max-min+1,i,min,max));
            }
        }
        
        



        List<Pair> li = new ArrayList<>();
        
    //    List<integer> list=new ArrayList<>();
        for(pair p:set)
        {

            // System.out.println(p.len+" "+p.ele+" "+p.l+" "+p.r);
          if(k==0)
          {
                return true;
          } 

          if(p.len== n)
          {
            return false;
          }
          boolean flg=true;
          
          for(Pair pr: li)
          {
                if(pr.first > p.r || pr.second < p.l)
                {
                    continue;
                }
                else    
                {
                    flg=false;
                    break;
                }
          }
          if(flg)
          {
            li.add(new Pair(p.l,p.r));
            k--;
          }
        }

        

        

        
        if (k==0) {
            return true;
        }

        return false;
    }
 }

 public class LC_C {
    
    

    public static void main(String[] args) {
        

        String s="cbcaba";

        Soln s1=new Soln();

     System.out.println(s1.canChoose(s, 1));
    }
}
