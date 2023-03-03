package ua.com.app.saienko.yaroslav.cryptoprice

// ctrl + alt + o   - Видалити імпорти, які не використовуються
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ua.com.app.saienko.yaroslav.cryptoprice.adapters.CoinInfoAdapter
import ua.com.app.saienko.yaroslav.cryptoprice.databinding.ActivityCoinPriceListBinding
import ua.com.app.saienko.yaroslav.cryptoprice.pojo.CoinPriceInfo

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
            override fun onCoinClick(coinPriceInfo: CoinPriceInfo) {
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
        viewModel.priceList.observe(this, Observer {
            adapter.coinInfoList = it
        })

    }
}