package ua.com.app.saienko.yaroslav.cryptoprice.presentation

// ctrl + alt + o   - Видалити імпорти, які не використовуються
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ua.com.app.saienko.yaroslav.cryptoprice.databinding.ActivityCoinPriceListBinding
import ua.com.app.saienko.yaroslav.cryptoprice.domain.CoinInfo
import ua.com.app.saienko.yaroslav.cryptoprice.presentation.adapters.CoinInfoAdapter

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    private lateinit var binding: ActivityCoinPriceListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                // замість цього використаємо метод newIntent з CoinDetailActivity
//                val intent = Intent(this@CoinPriceListActivity, CoinDetailActivity::class.java)
//                intent.putExtra(CoinDetailActivity.EXTRA_FROM_SYMBOL, coinPriceInfo.fromSymbol)
                val intent = CoinDetailActivity.newIntent(
                    this@CoinPriceListActivity,
                    coinPriceInfo.fromSymbol
                )
                startActivity(intent)
            }

        }
        binding.rvCoinPriceList.adapter = adapter
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[CoinViewModel::class.java]
        viewModel.coinInfoList.observe(this) {
            adapter.coinInfoList = it
        }

    }
}