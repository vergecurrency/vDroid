package vergecurrency.vergewallet.viewmodel;

import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import vergecurrency.vergewallet.service.model.MnemonicManager;
import vergecurrency.vergewallet.service.model.PreferencesManager;
import vergecurrency.vergewallet.wallet.WalletManager;

import static vergecurrency.vergewallet.Constants.SEED_SIZE;

public class PaperkeyDistributionViewModel extends ViewModel {

    String[] seed;
    PreferencesManager pm;
    private int currentWordIndex = -1;
    private WalletManager wm;

    public PaperkeyDistributionViewModel() {
        wm = WalletManager.init();
        pm = PreferencesManager.getInstance();
        if (!pm.getFirstLaunch()) {
            seed = MnemonicManager.getMnemonicFromJSON(pm.getMnemonic());
        }

    }

    public String[] getSeed() throws Exception {
        return seed;
    }

    public void setSeed(String[] seed) {
        this.seed = seed;
    }

    public boolean isPaperKeyCompleted() {
        return this.currentWordIndex == SEED_SIZE - 1;
    }

    public void nextWord(TextView textView, Button button) {
        if (isLastWord()) {
            button.setText("Done");
        } else {
            button.setText("Next");
        }
        this.increaseWordIndex();
        textView.setText(this.getWord());
    }

    public void previousWord(TextView textView, Button button) {
        if (!isLastWord()) {
            button.setText("Next");
        }
        this.decreaseWordIndex();
        textView.setText(this.getWord());
    }


    public void generateMnemonics() {
        //Should be created at the launch
        wm.generateMnemonic();
        seed = MnemonicManager.getMnemonicFromJSON(pm.getMnemonic());
    }

    private void increaseWordIndex() {
        if (currentWordIndex < SEED_SIZE - 1) {
            currentWordIndex += 1;
        }
    }

    private void decreaseWordIndex() {
        if (currentWordIndex > 0) {
            currentWordIndex --;
        }
    }

    private boolean isLastWord() {
        return this.currentWordIndex == SEED_SIZE - 2;
    }

    private String getWord() {
        return seed[currentWordIndex];
    }
}
