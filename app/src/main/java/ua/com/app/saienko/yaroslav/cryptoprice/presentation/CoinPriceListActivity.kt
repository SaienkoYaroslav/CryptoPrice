package ua.com.app.saienko.yaroslav.cryptoprice.presentation

// ctrl + alt + o   - Видалити імпорти, які не використовуються
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ua.com.app.saienko.yaroslav.cryptoprice.CryptoApp
import ua.com.app.saienko.yaroslav.cryptoprice.R
import ua.com.app.saienko.yaroslav.cryptoprice.databinding.ActivityCoinPriceListBinding
import ua.com.app.saienko.yaroslav.cryptoprice.domain.CoinInfo
import ua.com.app.saienko.yaroslav.cryptoprice.presentation.adapters.CoinInfoAdapter
import javax.inject.Inject

class CoinPriceListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory:CoinViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory) [CoinViewModel::class.java]
    }

    private lateinit var binding: ActivityCoinPriceListBinding

    private val component by lazy {
        (application as CryptoApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                // fragmentContainer == null, значить ми в портретному ренжимі
                if (binding.fragmentContainer == null) {
                    launchDetailActivity(coinPriceInfo.fromSymbol)
                } else {
                    launchDetailFragment(coinPriceInfo.fromSymbol)
                }
            }

        }
        binding.rvCoinPriceList.adapter = adapter
        viewModel.coinInfoList.observe(this) {
            adapter.submitList(it)
        }

    }

    private fun launchDetailActivity(fromSymbol: String) {
        // замість цього використаємо метод newIntent з CoinDetailActivity
//                val intent = Intent(this@CoinPriceListActivity, CoinDetailActivity::class.java)
//                intent.putExtra(CoinDetailActivity.EXTRA_FROM_SYMBOL, coinPriceInfo.fromSymbol)
        val intent = CoinDetailActivity.newIntent(
            this@CoinPriceListActivity,
            fromSymbol
        )
        startActivity(intent)
    }

    private fun launchDetailFragment(fromSymbol: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }

}