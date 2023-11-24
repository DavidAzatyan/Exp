package Algorithms;

public class MergeTwoSortedArrays {
    public static void main(String[] args) {
        int[] arr = {1, 1, 2, 2, 2, 3, 4, 4, 8};
        int[] arr1 = {1, 3, 5, 5, 6, 6, 8, 9};

        int lenArr = arr.length;
        int lenArr1 = arr1.length;

        int[] mergedArr = new int[lenArr + lenArr1];

        int arrPosition, arr1Position, mergedPosition;
        arrPosition = arr1Position = mergedPosition = 0;

        while (arrPosition < lenArr && arr1Position < lenArr1){
            if(arr[arrPosition] < arr1[arr1Position]){
                mergedArr[mergedPosition++] = arr[arrPosition++];
            }else{
                mergedArr[mergedPosition++] = arr1[arr1Position++];
            }
        }

        while (arrPosition < lenArr){
            mergedArr[mergedPosition++] = arr[arrPosition++];
        }
        while (arr1Position < lenArr1){
            mergedArr[mergedPosition++] = arr1[arr1Position++];
        }

        for (int j : mergedArr) {
            System.out.print(j + " ");
        }
    }
}
