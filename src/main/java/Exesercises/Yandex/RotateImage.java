package Exesercises.Yandex;

public class RotateImage {
    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        rotate(matrix);
        for(int[] arr : matrix){
            for (int el : arr){
                System.out.print(el + " ");
            }
            System.out.println();
        }
    }

    static void rotate(int[][] matrix) {
        int[][] matrix1 = new int[matrix.length][matrix.length];
        for (int k = 0; k < matrix.length; k++) {
            for (int i = matrix.length - 1; i >= 0; i--) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (j == k) {
                        int el = matrix[i][j];
                        matrix1[j][matrix.length - 1 - i] = el;
                    }
                }
            }
        }
        for(int i = 0; i < matrix1.length; i++){
            System.arraycopy(matrix1[i], 0, matrix[i], 0, matrix1[i].length);
        }
    }
}
