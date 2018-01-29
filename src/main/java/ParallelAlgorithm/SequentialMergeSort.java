package ParallelAlgorithm;

import java.util.Arrays;

public class SequentialMergeSort {

    private static int[] numbers = {3, 1, 5, 7};
    private static int[] tempArrays;


    public static void main(String[] args) {
        new SequentialMergeSort(numbers, tempArrays).mergeSort(0, numbers.length - 1);
        System.out.println(Arrays.toString(numbers));
    }

    public SequentialMergeSort(int[] numbers, int[] tempArrays) {
        this.numbers = numbers;
        this.tempArrays = new int[numbers.length];
    }

    public void mergeSort(int lowIndex, int highIndex) {
        if (lowIndex >= highIndex) {
            return;
        }

        int middleIndex = (lowIndex + highIndex) / 2;

        mergeSort(lowIndex, middleIndex);
        mergeSort(middleIndex + 1, highIndex);
        merge(lowIndex, middleIndex, highIndex);
    }

    private void merge(int lowIndex, int middleIndex, int highIndex) {

        /**
         * Copy all the numbers from numbers to temporary array
         */
        for (int i = 0; i <= highIndex; i++) {
            tempArrays[i] = numbers[i];
        }

        int i = lowIndex;
        int j = middleIndex + 1;
        int k = lowIndex;

        while (i <= middleIndex && j <= highIndex) {
            if (tempArrays[i] <= tempArrays[j]) {
                numbers[k] = tempArrays[i];
                i++;
            } else {
                numbers[k] = tempArrays[j];
                j++;
            }
            k++;
        }

        while (i <= middleIndex) {
            numbers[k] = tempArrays[i];
            k++;
            i++;
        }

        while (j <= middleIndex) {
            numbers[k] = tempArrays[j];
            k++;
            j++;
        }
    }

}
