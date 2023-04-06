package ua.com.app.saienko.yaroslav.cryptoprice.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ua.com.app.saienko.yaroslav.cryptoprice.R
import ua.com.app.saienko.yaroslav.cryptoprice.databinding.ActivityCoinDetailBinding

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoinDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // Якщо в інтенті немає цього ключа, тоді виходим з актівіті
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }

        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: EMPTY_SYMBOL
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbol))
                .commit()
        }
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_SYMBOL = ""

        // вказуємо які нам потрібні параметри і контекст (звідки буде запускатись інтент)
        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }

}