package com.patrick.rxstudy01;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockUpdateViewHolder extends RecyclerView.ViewHolder {
    private static final NumberFormat PRICE_FORMAT = new DecimalFormat("#0.00");

    @BindView(R.id.stock_item_symbol)
    TextView mStockSymbol;
    @BindView(R.id.stock_item_price)
    TextView mPrice;
    @BindView(R.id.stock_item_date)
    TextView mDate;

    public StockUpdateViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setStockSymbol(String stockSymbol) {
        mStockSymbol.setText(stockSymbol);
    }

    public void setPrice(BigDecimal price) {
        mPrice.setText(PRICE_FORMAT.format(price.floatValue()));
    }
    public void setDate(Date date) {
        mDate.setText(DateFormat.format("yyyy-MM-dd hh:mm", date));
    }
}
