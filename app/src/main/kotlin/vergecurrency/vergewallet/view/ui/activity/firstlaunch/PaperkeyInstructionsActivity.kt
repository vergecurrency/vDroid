package vergecurrency.vergewallet.view.ui.activity.firstlaunch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button

import vergecurrency.vergewallet.view.base.BaseActivity
import vergecurrency.vergewallet.R

class PaperkeyInstructionsActivity : BaseActivity() {


    //The dumbest class you'll find here... jump to the next one
    internal lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_paperkey_instructions)

        nextButton = findViewById(R.id.paperkey_next_button)
        nextButton.setOnClickListener(nextButtonListener())
    }

    private fun nextButtonListener(): View.OnClickListener {
        return View.OnClickListener {
        finish()
            startActivity(Intent(applicationContext, PaperkeyDistributionActivity::class.java))
        }
    }
}
