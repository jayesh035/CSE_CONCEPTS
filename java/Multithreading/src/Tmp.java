//
//2508. Add Edges to Make Degrees of All Nodes Even
//        Hard
//Topics
//        Companies
//Hint
//There is an undirected graph consisting of n nodes numbered from 1 to n. You are given the integer n and a 2D array edges where edges[i] = [ai, bi] indicates that there is an edge between nodes ai and bi. The graph can be disconnected.
//
//You can add at most two additional edges (possibly none) to this graph so that there are no repeated edges and no self-loops.
//
//        Return true if it is possible to make the degree of each node in the graph even, otherwise return false.
//
//The degree of a node is the number of edges connected to it.
//
//
//
//        Example 1:
//
//
//Input: n = 5, edges = [[1,2],[2,3],[3,4],[4,2],[1,4],[2,5]]
//Output: true
//Explanation: The above diagram shows a valid way of adding an edge.
//Every node in the resulting graph is connected to an even number of edges.
//Example 2:
//
//
//Input: n = 4, edges = [[1,2],[3,4]]
//Output: true
//Explanation: The above diagram shows a valid way of adding two edges.
//        Example 3:
//
//
//Input: n = 4, edges = [[1,2],[1,3],[1,4]]
//Output: false
//Explanation: It is not possible to obtain a valid graph with adding at most 2 edges.
//
//
//        Constraints:
//
//        3 <= n <= 105
//        2 <= edges.length <= 105
//edges[i].length == 2
//        1 <= ai, bi <= n
//ai != bi
//There are no repeated edges.
//        Seen this question in a real interview before?
//        1/5
//Yes
//        No
//Accepted
//16.2K
//        Submissions
//48.6K
//Acceptance Rate
//33.4%
//Topics
//        Companies
//Hint 1
//Hint 2
//Similar Questions
//Discussion (16)
//
//Choose a type
//
//
//
//Copyright ©️ 2025 LeetCode All rights reserved

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Tmp {



//    int findParent(int node,List<Integer>parent)

    public static boolean isPossible(int n, List<List<Integer>> edges) {



        List<Integer>[] G=new List[n+1];
        List<Integer>nodes=new ArrayList<>(n+1);
        HashSet<List<Integer>>map=new HashSet<>();
        for(int i=0;i<=n;i++)
        {
            G[i]=new ArrayList<>();

        }
        for(List<Integer> pair:edges)
        {
            map.add(pair);
            G[pair.get(0)].add(pair.get(1));
            G[pair.get(1)].add(pair.get(0));
        }

        int odds=0;


        for(int i=1;i<=n;i++)
        {
            if((G[i].size() % 2) ==1)
            {
                nodes.add(i);
            }
        }

        if(nodes.size()>4)
        {
            return  false;
        }

       if(nodes.size()==0)
       {
           return  true;
       }
       else if(nodes.size()==2)
       {
//           map.

           for(int i=1;i<=n;i++)
           {
               if(i!=nodes.get(0) && i!=nodes.get(1))
               {
                   if(!map.contains(Arrays.asList(i,nodes.get(0)))
                   && !map.contains(Arrays.asList(i,nodes.get(1)))
                   && !map.contains(Arrays.asList(nodes.get(0),i))
                   && !map.contains(Arrays.asList(nodes.get(1),i)))
                   {
                       return true;
                   }
               }
           }
           return false;
       }
       else
       {
           for(int i=0;i<nodes.size();i++)
           {
               for(int j=i+1;j<nodes.size();j++)
               {
                   if(!map.contains(Arrays.asList(nodes.get(i),nodes.get(j)))
                           && !map.contains(Arrays.asList(nodes.get(j),nodes.get(i))))
                   {
                       for(int k=0;k<nodes.size();k++)
                       {
                           if(k==i || k==j)
                           {
                               continue;
                           }
                           for(int l=k+1;l<nodes.size();l++)
                           {

                               if(!map.contains(Arrays.asList(nodes.get(k),nodes.get(l)))
                                       && !map.contains(Arrays.asList(nodes.get(l),nodes.get(k))))
                               {
                                   return true;
                               }
                           }
                       }
                   }

               }
           }
       }





        return false;
    }

    public static void main(String[] args) {


//isPossible()


    }
}
