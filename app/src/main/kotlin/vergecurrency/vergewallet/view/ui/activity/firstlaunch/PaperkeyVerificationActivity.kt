package vergecurrency.vergewallet.view.ui.activity.firstlaunch

import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import vergecurrency.vergewallet.Constants
import vergecurrency.vergewallet.R
import vergecurrency.vergewallet.helpers.utils.MathUtils
import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.viewmodel.PaperkeyVerificationViewModel
import java.util.*

class PaperkeyVerificationActivity : BaseActivity() {

    private lateinit var firstWordCaption: TextView
    private lateinit var secondWordCaption: TextView
    private lateinit var firstWordInput: EditText
    private lateinit var secondWordInput: EditText
    private lateinit var confirmButton: Button
    private lateinit var verificationWords: Pair<Array<String>, IntArray>
    private lateinit var seed: Array<String>

    internal lateinit var mViewModel: PaperkeyVerificationViewModel

    //TODO : Def move this shit to viewmodel
    private val twoRandomWordsFromSeed: Pair<Array<String>, IntArray>
        get() {
            val words = Array(2) { "" }
            val positions = IntArray(2)
            positions[0] = MathUtils.getRandomNumber(Constants.SEED_SIZE)
            var second = MathUtils.getRandomNumber(Constants.SEED_SIZE)
            while (positions[0] == second) {
                second = MathUtils.getRandomNumber(Constants.SEED_SIZE)
            }
            positions[1] = second
            Arrays.sort(positions)
            words[0] = seed[positions[0]]
            words[1] = seed[positions[1]]
            return Pair(words, positions)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paperkey_confirm_seed)

        mViewModel = ViewModelProvider(this).get(PaperkeyVerificationViewModel::class.java)

        //Get the shared preferences

        seed = mViewModel.seed

        verificationWords = twoRandomWordsFromSeed

        initComponents()

    }

    private fun initComponents() {

        //set the caption for the first word according to its position
        firstWordCaption = findViewById(R.id.label_first_word)
        firstWordCaption.text = String.format("Word #%d", verificationWords.second[0] + 1)
        //set the caption for the second word according to its position
        secondWordCaption = findViewById(R.id.label_second_word)
        secondWordCaption.text = String.format("Word #%d", verificationWords.second[1] + 1)

        firstWordInput = findViewById(R.id.seed_first_word)
        secondWordInput = findViewById(R.id.seed_second_word)


        //assign a click listener to the
        confirmButton = findViewById(R.id.paper_key_confirm_next)
        confirmButton.setOnClickListener(onNextClick())
    }

    private fun onNextClick(): View.OnClickListener {
        return View.OnClickListener {

            if (firstWordInput.text.toString() == verificationWords.first[0].toLowerCase()) {
                if (secondWordInput.text.toString() == verificationWords.first[1].toLowerCase()) {

                    startActivity(Intent(applicationContext, PassphraseCreationActivity::class.java))


                } else {
                    secondWordInput.setBackgroundResource(R.color.material_red_200)
                }
            } else {
                firstWordInput.setBackgroundResource(R.color.material_red_200)
            }


        }
    }
}
