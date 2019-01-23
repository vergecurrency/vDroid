/*package vergecurrency.vergewallet.wallet;


import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

import org.bitcoinj.core.Coin;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.wallet.Wallet;

import java.io.File;

import vergecurrency.vergewallet.Constants;

public class WalletManager {

    public WalletManager() {

    }

    public void initWallet() {
        NetworkParameters params;
        String filePrefix;
        params = MainNetParams.get();
        filePrefix = "forwarding-service";

        WalletAppKit kit = new WalletAppKit(params, new File("."), filePrefix) {
          @Override
          protected void onSetupCompleted() {
              if(wallet().getKeyChainGroupSize() < 1) {
                  wallet().importKey(new ECKey());
              }
          }
        };

        kit.startAsync();
        kit.awaitRunning();

        createWalletListeners(kit.wallet());

    }

    private void createWalletListeners(Wallet wallet) {

        wallet.addCoinsReceivedEventListener((w, tx, prevBalance, newBalance) -> {
            Coin value = tx.getValueSentToMe(w);

            Futures.addCallback(tx.getConfidence().getDepthFuture(Constants.NEEDED_CONFIRMATIONS), new FutureCallback<TransactionConfidence>() {
                @Override
                public void onSuccess(TransactionConfidence result) {
                    // "result" here is the same as "tx" above, but we use it anyway for clarity.
                    // forwardCoins(result);
                }

                @Override
                public void onFailure(Throwable t) {}
            });

        });


    }

    private void createKeyChain() {

    }


    private void createKeyPair() {

    }




    private String baseUrl = "";
}
*/