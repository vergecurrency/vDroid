package vergecurrency.vergewallet.models.dataproc;

import java.util.ArrayList;
import java.util.Scanner;

import vergecurrency.vergewallet.Constants;

public final class SeedGenerator {



    private static ArrayList<String> wordlist;

    private SeedGenerator() {
        wordlist = buildWordList();
    }

    public static ArrayList<String> generateSeed() {

        ArrayList<String> list = new ArrayList<String>();

        for (int i = 0; i < Constants.SEED_SIZE; i++) {
            list.add(wordlist.get(getRandomNumber(Constants.WORDLIST_SIZE)));
        }

        return list;
    }

    public static int getRandomNumber(int max) {
        return (int) (Math.random() * max + 1);
    }

    private ArrayList<String> buildWordList() {
        ArrayList<String> wordlist = new ArrayList<String>();
        Scanner s = new Scanner(getClass().getResourceAsStream("wordlist.txt"));

        while (s.hasNextLine()) {
            wordlist.add(s.nextLine());
        }
        return wordlist;
    }

}
