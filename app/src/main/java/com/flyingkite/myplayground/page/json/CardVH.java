package com.flyingkite.myplayground.page.json;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyingkite.myplayground.R;
import com.flyingkite.myplayground.data.Card;

class CardVH extends RecyclerView.ViewHolder {
    private Card card;
    ImageView thumb;
    TextView txt;

    public CardVH(View v) {
        super(v);
        thumb = (ImageView) v.findViewById(R.id.squareImg);
        txt = (TextView) v.findViewById(R.id.squareTxt);
    }

    public void setCard(Card card) {
        this.card = card;
        txt.setText(card.name);
    }
}
