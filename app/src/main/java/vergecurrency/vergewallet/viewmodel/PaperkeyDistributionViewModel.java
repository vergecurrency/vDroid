package vergecurrency.vergewallet.viewmodel;

import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import vergecurrency.vergewallet.service.model.MnemonicManager;
import vergecurrency.vergewallet.wallet.WalletManager;

import static vergecurrency.vergewallet.Constants.SEED_SIZE;
import static vergecurrency.vergewallet.wallet.VergeWalletApplication.PREFERENCES_MANAGER;

public class PaperkeyDistributionViewModel extends ViewModel {

    String[] seed;
    private int currentWordIndex = -1;
    private WalletManager wm;

    public PaperkeyDistributionViewModel() {
        wm = WalletManager.init();
        if (!PREFERENCES_MANAGER.getFirstLaunch()) {
            seed = MnemonicManager.getMnemonicFromJSON(PREFERENCES_MANAGER.getMnemonic());
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
        seed = MnemonicManager.getMnemonicFromJSON(PREFERENCES_MANAGER.getMnemonic());
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
