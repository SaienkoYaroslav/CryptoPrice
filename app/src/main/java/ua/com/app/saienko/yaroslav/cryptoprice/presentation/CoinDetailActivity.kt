package ua.com.app.saienko.yaroslav.cryptoprice.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ua.com.app.saienko.yaroslav.cryptoprice.databinding.ActivityCoinDetailBinding
import java.text.DecimalFormat

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    private lateinit var binding: ActivityCoinDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[CoinViewModel::class.java]

        // Якщо в інтенті немає цього ключа, тоді виходим з актівіті
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }

        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
        val formattedDouble = DecimalFormat("#0.000000000")

        viewModel.getDetailInfo(fromSymbol!!).observe(this, Observer {
            binding.tvPrice.text =
                if (it.price?.compareTo(0.1) == -1) formattedDouble.format(it.price) else it.price.toString()
            binding.tvMinPrice.text =
                if (it.lowDay?.compareTo(0.1) == -1) formattedDouble.format(it.lowDay) else it.lowDay.toString()
            binding.tvMaxPrice.text =
                if (it.highDay?.compareTo(0.1) == -1) formattedDouble.format(it.highDay) else it.highDay.toString()
            binding.tvLastDeal.text = it.lastMarket
            binding.tvUpdated.text = it.getFormattedTime()
            binding.tvFromSymbol.text = it.fromSymbol
            binding.tvToSymbol.text = it.toSymbol
            Picasso.get().load(it.getFullImageUrl()).into(binding.ivLogoCoin)
        })
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        // вказуємо які нам потрібні параметри і контекст (звідки буде запускатись інтент)
        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }

}