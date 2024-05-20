package Logic;

import java.io.File;
import java.util.ArrayList;

public class QuickSort {
    private ArrayList<File> files;
    private int number;

    public void sort(ArrayList<File> list) {
        // check for empty or null array
        if (list == null || list.isEmpty()) {
            return;
        }
        this.files = list;
        number = list.size();
        quicksort(0, number - 1);
    }

    private void quicksort(int low, int high) {
        int i = low, j = high;
        File pivot = files.get(low + (high - low) / 2);

        while (i <= j) {
            while (files.get(i).getName().compareToIgnoreCase(pivot.getName()) < 0) {
                i++;
            }
            while (files.get(j).getName().compareToIgnoreCase(pivot.getName()) > 0) {
                j--;
            }
            if (i <= j) {
                exchange(i, j);
                i++;
                j--;
            }
        }
        if (low < j)
            quicksort(low, j);
        if (i < high)
            quicksort(i, high);
    }

    private void exchange(int i, int j) {
        File temp = files.get(i);
        files.set(i, files.get(j));
        files.set(j, temp);
    }
}

