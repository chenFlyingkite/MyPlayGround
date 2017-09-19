package com.flyingkite.mybattery.page.json;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.flyingkite.mybattery.R;
import com.flyingkite.mybattery.data.Card;
import com.flyingkite.util.Say;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CardAdapter extends RecyclerView.Adapter<CardVH> {
    private List<Card> cards = new ArrayList<>();

    public void setCards(Card[] cards) {
        this.cards = cards == null ? new ArrayList<Card>() : Arrays.asList(cards);
    }

    @Override
    public CardVH onCreateViewHolder(ViewGroup parent, int viewType) {
        //Say.LogF("create type #%s", viewType);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_square_image, parent, false);
        return new CardVH(v);
    }

    @Override
    public void onBindViewHolder(CardVH holder, int position) {
        Context ctx = holder.thumb.getContext();
        Card c = cards.get(position);
        Say.LogF("bind #%2d -> %s ,name = %s", position, c.icon_url, c.name);
        if (position % 2 == 0) {
            Glide.with(ctx)
                    //.load(Uri.parse())
                    .load(c.icon_url).into(holder.thumb);
        } else {
            Picasso.with(ctx).load(c.icon_url).into(holder.thumb);
        }
        holder.setCard(c);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }
}
