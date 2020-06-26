package vergecurrency.vergewallet.view.ui.activity.firstlaunch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.model.WalletConfiguration
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.viewmodel.PaperkeyDistributionViewModel
import vergecurrency.vergewallet.viewmodel.WalletConfigurationFactory

class PaperkeyDistributionActivity : BaseActivity() {


    //variable decl.
    private lateinit var mViewModel: WalletConfiguration

    private lateinit var nextButton: Button
    private lateinit var previousButton: Button
    private lateinit var wordView: TextView
    private var currentWordIndex = -1

    private lateinit var seed: Array<ByteArray>

    private val word: ByteArray
        get() = mViewModel.getSeed()[currentWordIndex]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paperkey_seed)

        //learn it and shut up
        mViewModel = ViewModelProvider(this, WalletConfigurationFactory()).get(WalletConfiguration::class.java)

        initComponents()

        generateSeed()
        //get the first word
        nextWord()

    }

    private fun generateSeed() {
        mViewModel.generateMnemonics()

    }


    private fun initComponents() {
        wordView = findViewById(R.id.paper_key_logo)

        nextButton = findViewById(R.id.paperkey_next_word)
        nextButton.setOnClickListener(onNextListener())

        previousButton = findViewById(R.id.paperkey_previous_word)
        previousButton.setOnClickListener(onPreviousListener())
    }

    internal fun onNextListener(): View.OnClickListener {
        return View.OnClickListener {
            if (currentWordIndex == 11) {
                startActivity(Intent(applicationContext, PaperkeyVerificationActivity::class.java))
            }
            nextWord()
        }
    }

    internal fun onPreviousListener(): View.OnClickListener {
        return View.OnClickListener {
            //get to the previous word.
            previousWord()
        }
    }

    private fun nextWord() {
        //Increase if not the last
        if (currentWordIndex < 11) {
            currentWordIndex += 1
            nextButton.text = getString(R.string.paperkey_view_next)
        }
        //change button text if last
        if (currentWordIndex == 11) {
            nextButton.text = getString(R.string.paperkey_view_done)
        }
        wordView.text = String(word)
    }

    private fun previousWord() {
        //Decrease if not the first
        if (currentWordIndex > 0) {
            currentWordIndex -= 1
            nextButton.text = getString(R.string.paperkey_view_next)
        }
        wordView.text = String(word)
    }
}
