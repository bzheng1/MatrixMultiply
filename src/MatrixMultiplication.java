import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Bryan Zheng
 * @version 4/26/2018.
 */
public class MatrixMultiplication{
    public static long [][] matrix1 = new long[100][100];
    public static long [][] matrix2 = new long[100][100];
    public static long [][] myMatrixC = new long[100][100];
    public static float exeTime;
    public static Random number = new Random();
    public static int threadcount;
    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<WorkerThread> threads = new ArrayList<>(threadcount);

    public static void main(String[] args) throws InterruptedException {

        generateRandomMatrix(matrix1);
        generateRandomMatrix(matrix2);
//        int rowsA = matrix1.length;
//        int colsA = matrix1[0].length;
//        int rowsB = matrix2.length;
//        int colsB = matrix2[0].length;
        threadcount = scanner.nextInt();

        for(int i = 0; i<threadcount;i++){
            threads.add(new WorkerThread(matrix1,matrix2,myMatrixC,threadcount,i));
        }for(int i =0;i<threadcount;i++){
            threads.get(i).start();
        }for(int i =0;i<threadcount;i++){
            try{
                threads.get(i).join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }


        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                System.out.print(myMatrixC[i][j] + "\t");
            }System.out.print("\n");
        }
    }
    public static long [][] generateRandomMatrix(long[][] emptyMatrix){
        for(int i = 0;i < 100;i++){
            for(int j = 0; j < 100; j++){
                emptyMatrix[i][j] = number.nextInt(100) - number.nextInt(100);
                System.out.print(emptyMatrix[i][j] + "\t");
            }
           System.out.print("\n");
        }System.out.print("\n");
        return emptyMatrix;
    }

    public static void writeMatrix(String filename, long[][] matrix) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));

            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    bw.write(matrix[i][j] + ",");
                }
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class WorkerThread extends Thread {
        private long matrixA[][];
        private long matrixB[][];
        private long results[][];
        private static int numberofthreads;
        private int threadNo;

        public WorkerThread(long matrixA[][],long matrixB[][],long results[][], int threadCount, int threadNo){
            this.matrixA = matrixA;
            this.matrixB = matrixB;
            this.results = results;
            this.numberofthreads = threadCount;
            this.threadNo = threadNo;
        }

        @Override
        public void run() {
            for(int k = 0; k < matrixB.length; k++) {
                multiplyMatrix(matrixA,matrixB,results);
            }
        }

        public void multiplyMatrix(long[][] first, long[][] second, long[][] results)
        {
            int aRows = first.length;
            int aColumns = first[0].length;
            int bColumns = second[0].length;
            int divisionOfWork = 100 / numberofthreads;

            if(numberofthreads == 1) {
                for (int i = 0; i < aRows; i++) {
                    for (int j = 0; j < bColumns; j++) {
                        for (int k = 0; k < aColumns; k++) {
                            results[i][j] += first[i][k] * second[k][j];
                        }
                    }
                }
            }
            if((100 % numberofthreads) == 0)
            {
                for (int i = (threadNo * numberofthreads); i < (threadNo * numberofthreads) + numberofthreads; i++) {
                    for (int j = 0; j < 100; j++) {
                        for (int k = 0; k < 100; k++) {
                            results[i][j] += first[i][k] * second[k][j];
                        }
                    }
                }
            }else{
                if(threadNo != (threadcount - 1)) {
                    for (int i = (threadNo * divisionOfWork); i<(threadNo * divisionOfWork) + divisionOfWork; i++) {
                        for (int j = 0; j < 100; j++) {
                            for (int k = 0; k < 100; k++){
                                results[i][j] += first[i][k] * second[k][j];
                            }
                        }
                    }
                }else{
                    for (int i = (threadNo * divisionOfWork); i < (threadNo * divisionOfWork) + divisionOfWork + (100 % threadNo); i++) {
                        for (int j = 0; j < 100; j++)
                        {
                            for (int k = 0; k < 100; k++) {
                                results[i][j] += first[i][k] * second[k][j];
                            }
                        }
                    }
                }
            }
        }
    }
}
