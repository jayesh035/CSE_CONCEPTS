package Collection;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

//
//ou are given an integer side, representing the edge length of a square with corners at (0, 0), (0, side), (side, 0), and (side, side) on a Cartesian plane.
//
//You are also given a positive integer k and a 2D integer array points, where points[i] = [xi, yi] represents the coordinate of a point lying on the boundary of the square.
//
//You need to select k elements among points such that the minimum Manhattan distance between any two points is maximized.
//
//Return the maximum possible minimum Manhattan distance between the selected k points.
//
//The Manhattan Distance between two cells (xi, yi) and (xj, yj) is |xi - xj| + |yi - yj|.

public class test {


     int recursive_lower_bound(long array[], int low,
                                     int high, long key)
    {
        // Base Case
        if (low > high) {
            return low;
        }

        // Find the middle index
        int mid = low + (high - low) / 2;

        // If key is lesser than or equal to
        // array[mid] , then search
        // in left subarray
        if (key <= array[mid]) {

            return recursive_lower_bound(array, low,
                    mid - 1, key);
        }

        // If key is greater than array[mid],
        // then find in right subarray
        return recursive_lower_bound(array, mid + 1, high,
                key);
    }

    boolean find(long [] extended,long d,int k,long lim)
    {


        int n=extended.length/2;


        for(int i=0;i<n;i++)
        {

            int idx=(int)extended[i];
            int count=0;
            for(int j=0;j<k;j++)
            {


                int res=recursive_lower_bound(extended,idx+1,i+n,(idx+d));

                if(res<i+n)
                {
                    count++;
                    idx=(int)extended[res];
                }
                else
                {
                    break;
                }


            }
            if((count==k) && (lim-extended[idx]+extended[i]) >= (k-1)*d)
            {
                return true;
            }


//            ConcurrentSkipListMap<>

        }

        return false;
    }
    long map(long side,int x,int y)
    {
        if(y==0)
        {
            return x;
        }
        else if(side==x)
        {
            return  (long)2*side+y;
        }
        else if(side == y)
        {
            return (long)3*side-x;
        }

        return (long)4*side-y;
    }
    public int maxDistance(long side, int[][] points, int k) {


        int n= points.length;
        long[] it=new long[n];
        for(int i=0;i<points.length;i++)
        {
            it[i]=map(side,points[i][0],points[i][1]);


        }

    Arrays.sort(it);
        long [] extended=new long[2*n];
        long lim=4*side;
        for(int i=0;i<n;i++)
        {
            extended[i]=it[i];
            extended[n+1]=it[i]+lim;
        }



        long  low=0;
        long high=2*side;


        long ans=0;
        while(low<=high)
        {
            long mid=(low+high)/2;


            if(find(extended,mid,k,lim))
            {
                ans=Math.max(ans,mid);
                low=mid+1;
            }
            else
            {
                high=mid-1;
            }
        }







       return (int)ans;


    }
    public static void main(String[] args) {





    }
}


//[[4,4],[3,4],[2,0],[4,3],[4,0]]