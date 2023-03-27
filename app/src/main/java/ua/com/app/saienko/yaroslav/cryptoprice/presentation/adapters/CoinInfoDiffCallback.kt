package ua.com.app.saienko.yaroslav.cryptoprice.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import ua.com.app.saienko.yaroslav.cryptoprice.domain.CoinInfo

class CoinInfoDiffCallback: DiffUtil.ItemCallback<CoinInfo>() {

    override fun areItemsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem == newItem
    }
}