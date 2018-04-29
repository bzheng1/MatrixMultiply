import java.util.Random;

/**
 * @author Bryan Zheng
 * @version 4/26/2018.
 */
public class MatrixMultiplication{
    public static long [][] matrix1 = new long[3][3];
    public static long [][] matrix2 = new long[3][3];
    public static long [][] myMatrixC = new long[3][3];
    public static Random number = new Random();
    public static void main(String[] args) throws InterruptedException {
        generateRandomMatrix(matrix1);
        generateRandomMatrix(matrix2);
        int rowsA = matrix1.length;
        int colsA = matrix1[0].length;
        int rowsB = matrix2.length;
        int colsB = matrix2[0].length;
        Runnable run = () -> {
            //rows and columns for each matrix

            //start across rows of A
            for (int i = 0; i < rowsA; i++) { // aRow
                for (int j = 0; j < colsB; j++) { // bColumn
                    for (int k = 0; k < colsA; k++) { // aColumn
                        myMatrixC[i][j] += matrix1[i][k] * matrix2[k][j];

                    }
                }
            }
        };
        new Thread(run).start();
        Thread.sleep(10);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(myMatrixC[i][j] + "\t");
            }System.out.print("\n");

        }
    }

    public static long [][] generateRandomMatrix(long[][] emptyMatrix){
        for(int i = 0;i < 3;i++){
            for(int j = 0; j < 3; j++){
                emptyMatrix[i][j] = number.nextInt(100) - number.nextInt(100);
                System.out.print(emptyMatrix[i][j] + "\t");
            }
           System.out.print("\n");
        }System.out.print("\n");
        return emptyMatrix;
    }


    //Timer functions
    public static float startTimer(){
        long startTime = System.currentTimeMillis();
        return startTime;
    }
    public static float endTimer(){
        long endTime = System.currentTimeMillis();
        return endTime;
    }
}
