package com.patrick.rxstudy09;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class StockDataAdapter extends RecyclerView.Adapter<StockUpdateViewHolder> {
    private final List<StockUpdate> data = new ArrayList<>();

    @NonNull
    @Override
    public StockUpdateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_update_item, parent, false);
        StockUpdateViewHolder vh = new StockUpdateViewHolder(layout);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull StockUpdateViewHolder holder, int position) {
        StockUpdate stockUpdate = data.get(position);
        holder.setStockSymbol(stockUpdate.getStockSymbol());
        holder.setPrice(stockUpdate.getPrice());
        holder.setDate(stockUpdate.getDate());
        holder.setTwitterStatus(stockUpdate.getTwitterStatus());
        holder.setIsStatusUpdate(stockUpdate.isTwitterStatusUpdate());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(StockUpdate newStockUpdate) {
        data.add(0, newStockUpdate);
        notifyItemInserted(0);
    }

    public boolean contains(StockUpdate newStockUpdate) {
        for (StockUpdate stockUpdate : data) {
            if (stockUpdate.getStockSymbol()
                    .equals(newStockUpdate.getStockSymbol())) {
                if (stockUpdate.getPrice().equals(newStockUpdate.getPrice())
                        && stockUpdate.getTwitterStatus().equals(
                        newStockUpdate.getTwitterStatus())) {
                    return true;
                }
                break;
            }
        }
        return false;
    }
}
