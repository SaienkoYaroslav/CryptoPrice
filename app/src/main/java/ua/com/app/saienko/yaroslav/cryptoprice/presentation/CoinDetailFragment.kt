package ua.com.app.saienko.yaroslav.cryptoprice.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ua.com.app.saienko.yaroslav.cryptoprice.CryptoApp
import ua.com.app.saienko.yaroslav.cryptoprice.databinding.FragmentCoinDetailBinding
import javax.inject.Inject


class CoinDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: CoinViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory) [CoinViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as CryptoApp).component
    }

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding: FragmentCoinDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding == null")

    private lateinit var fromSymbol: String


    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fromSymbol = it.getString(FROM_SYMBOL_KEY, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getDetailInfo(fromSymbol).observe(viewLifecycleOwner) {
            with(binding) {
                tvPrice.text = it.price
                tvMinPrice.text = it.lowDay
                tvMaxPrice.text = it.highDay
                tvLastDeal.text = it.lastMarket
                tvUpdated.text = it.lastUpdate
                tvFromSymbol.text = it.fromSymbol
                tvToSymbol.text = it.toSymbol
                Picasso.get().load(it.imageUrl).into(ivLogoCoin)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val FROM_SYMBOL_KEY = "From_Symbol"

        @JvmStatic
        fun newInstance(fromSymbol: String) =
            CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(FROM_SYMBOL_KEY, fromSymbol)
                }
            }
    }
}