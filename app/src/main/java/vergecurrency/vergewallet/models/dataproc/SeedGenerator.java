package vergecurrency.vergewallet.models.dataproc;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class SeedGenerator {

    private String[] seed;
    private int seedSize = 12;

    public SeedGenerator() {

    }

    int getRandomNumber(int maxNumber) {
        return 0;
    }

    public ArrayList<String> buildWordList() {
        ArrayList<String> wordlist = new ArrayList<String>();
        Scanner s = new Scanner(getClass().getResourceAsStream("wordlist.txt"));

        while(s.hasNextLine()){
            wordlist.add(s.nextLine());
        }
        return wordlist;
    }

}
