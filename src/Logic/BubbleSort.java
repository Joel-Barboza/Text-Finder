package Logic;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;

public class BubbleSort {
 
    public ArrayList<File> sortByDate(ArrayList<File> list) throws ParseException {

        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).lastModified() > list.get(j + 1).lastModified()) {
                    // swap temp and arr[i]
                    File temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }


        return list;
    }
}
