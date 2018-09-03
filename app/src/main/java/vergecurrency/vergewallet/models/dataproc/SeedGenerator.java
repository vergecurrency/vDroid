package vergecurrency.vergewallet.models.dataproc;

import android.content.Context;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import vergecurrency.vergewallet.Constants;

public class SeedGenerator {

    private Context theContext;
    private ArrayList<String> wordlist;

    public SeedGenerator(Context context) {
        theContext = context;
    }

    public ArrayList<String> generateSeed() {
        //build the wordlist from the text file
        wordlist = buildWordList();
        //create the arraylist that will contain the seed
        ArrayList<String> list = new ArrayList<String>();

        //populate the arraylist with random words from the seed
        for (int i = 0; i < Constants.SEED_SIZE; i++) {
            int index = getRandomNumber(Constants.WORDLIST_SIZE);
            list.add(wordlist.get(index));
            //avoid getting same word twice
            wordlist.remove(index);
        }

        return list;
    }

    public int getRandomNumber(int max) {
        return (int) (Math.random() * max);
    }

    private ArrayList<String> buildWordList() {

        ArrayList<String> wordlist = new ArrayList<String>();
        //Scan the file to get the list of words.
        //TODO : Jump to the lines defined by a line number, so that I don't have to parse the whole file.
        try {
            Scanner s = new Scanner(theContext.getAssets().open("wordlist.txt"));

            while (s.hasNextLine()) {
                wordlist.add(s.nextLine());
            }
            return wordlist;
        } catch (Exception e) {
            Toast.makeText(theContext,"Can't get the wordlist. did you give the application READ_WRITE permission?",Toast.LENGTH_LONG);
            return null;
        }
    }

}
