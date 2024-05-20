package Logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class RadixSort {
    // A utility function to get maximum value in list[]
    private File getMax(ArrayList<File> list) throws IOException {
        File mx = null;
        if (!list.isEmpty()) {
            mx = list.get(0);
            for (File file : list) {
                if (getFileSize(file) > getFileSize(mx)) {
                    mx = file;
                }
            }
        }
        return mx;
    }

    private int getFileSize(File file) throws IOException {
        return (int) (Files.size(Path.of(file.getAbsolutePath())) / 1024);
    }

    // A function to do counting sort of list[] according to the digit represented by exp.
    public void countSort(ArrayList<File> list, int exp) throws IOException {
        int n = list.size();
        File[] output = new File[n]; // output array
        int[] count = new int[10];
        Arrays.fill(count, 0);

        // Store count of occurrences in count[]
        for (File file : list) {
            int index = (getFileSize(file) / exp) % 10;
            count[index]++;
        }

        // Change count[i] so that count[i] now contains actual position of this digit in output[]
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Build the output array
        for (int i = n - 1; i >= 0; i--) {
            File file = list.get(i);
            int index = (getFileSize(file) / exp) % 10;
            output[count[index] - 1] = file;
            count[index]--;
        }

        // Copy the output array to list[], so that list[] now contains sorted numbers according to current digit
        for (int i = 0; i < n; i++) {
            list.set(i, output[i]);
        }
    }

    // The main function to that sorts arr[] of size n using Radix Sort
    public void sort(ArrayList<File> list) throws IOException {
        // Find the maximum number to know number of digits
        File m = getMax(list);

        // Do counting sort for every digit. Note that instead of passing digit number, exp is passed.
        // exp is 10^i where i is current digit number
        if (m != null) {
            for (int exp = 1; getFileSize(m) / exp > 0; exp *= 10) {
                countSort(list, exp);
            }
        }
    }
}
