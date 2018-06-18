package javaTest;

public class ArrayCopyer {
    public ArrayCopyer(){

    }
    public  static int[] copy(int[] src,int start,int end){
        int lenNew = end - start + 1;
        int[] arrNew = new int[lenNew];
        for (int i=0,j=start;i<src.length;i++){
            if(i>=start&&i<=end){
                arrNew[j]=src[i];
                j++;
            }
        }
        return arrNew;
    }
}
