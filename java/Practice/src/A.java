import java.util.ArrayList;
import java.util.List;

public class  A {
    
    public static void main(String[] args) {
        
        // 1 4 3 4 5 6 7 8
        //0 1
        //3 5
        //2 4
        //0 7
        
        int n=8;
        List<Integer> SGT = new ArrayList<>(4*n);


        int [] arr={1,2,7,4,5};
        build(0,0,arr.length-1,SGT,arr);
    }

    static void build(int indx,int i,int j,List<Integer> SGT,int [] arr)
    {
        if(i==j)
        {
            SGT.set(indx,arr[i]);
            return;
        }
        int mid=(i+j)/2;
        build(2*indx+1,i,mid,SGT,arr);
        build(2*indx+2,mid+1,j,SGT,arr);
        SGT.set(indx,Math.max(SGT.get(2*indx+1),SGT.get(2*indx+2)));
    }

}
