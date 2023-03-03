package ua.com.app.saienko.yaroslav.cryptoprice.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ua.com.app.saienko.yaroslav.cryptoprice.R
import ua.com.app.saienko.yaroslav.cryptoprice.databinding.ItemCoinInfoBinding
import ua.com.app.saienko.yaroslav.cryptoprice.pojo.CoinPriceInfo
import java.text.DecimalFormat

class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoAdapter.ViewHolder>() {

    var coinInfoList: List<CoinPriceInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    inner class ViewHolder(val binding: ItemCoinInfoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCoinInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coin = coinInfoList[position]
        val formattedDouble = DecimalFormat("#0.00000000")
        with(holder) {
            with(coin) {
                val symbolsTemplate = context.resources.getString(R.string.symbols_template)
                val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
                binding.tvSymbols.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                binding.tvPrice.text = if (price?.compareTo(0.1) == -1) formattedDouble.format(price) else price.toString()
                binding.tvTime.text = String.format(lastUpdateTemplate, getFormattedTime())
                Picasso.get().load(getFullImageUrl()).into(binding.ivLogoCoin)
                itemView.setOnClickListener {
                    onCoinClickListener?.onCoinClick(this)
                }
            }
        }

    }

    override fun getItemCount() = coinInfoList.size

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }
}