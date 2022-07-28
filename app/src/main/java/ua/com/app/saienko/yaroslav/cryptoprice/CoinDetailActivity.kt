package ua.com.app.saienko.yaroslav.cryptoprice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private lateinit var ivLogoCoin: ImageView
    private lateinit var tvFromSymbol: TextView
    private lateinit var tvToSymbol: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvMinPrice: TextView
    private lateinit var tvMaxPrice: TextView
    private lateinit var tvLastDeal: TextView
    private lateinit var tvUpdated: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_detail)

        init()

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
            tvPrice.text =
                if (it.price?.compareTo(0.1) == -1) formattedDouble.format(it.price) else it.price.toString()
            tvMinPrice.text =
                if (it.lowDay?.compareTo(0.1) == -1) formattedDouble.format(it.lowDay) else it.lowDay.toString()
            tvMaxPrice.text =
                if (it.highDay?.compareTo(0.1) == -1) formattedDouble.format(it.highDay) else it.highDay.toString()
            tvLastDeal.text = it.lastMarket
            tvUpdated.text = it.getFormattedTime()
            tvFromSymbol.text = it.fromSymbol
            tvToSymbol.text = it.toSymbol
            Picasso.get().load(it.getFullImageUrl()).into(ivLogoCoin)
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

    private fun init() {
        ivLogoCoin = findViewById(R.id.ivLogoCoin)
        tvFromSymbol = findViewById(R.id.tvFromSymbol)
        tvToSymbol = findViewById(R.id.tvToSymbol)
        tvPrice = findViewById(R.id.tvPrice)
        tvMinPrice = findViewById(R.id.tvMinPrice)
        tvMaxPrice = findViewById(R.id.tvMaxPrice)
        tvLastDeal = findViewById(R.id.tvLastDeal)
        tvUpdated = findViewById(R.id.tvUpdated)
    }

}