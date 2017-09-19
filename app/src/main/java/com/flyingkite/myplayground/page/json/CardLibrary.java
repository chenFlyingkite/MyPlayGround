package com.flyingkite.myplayground.page.json;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.flyingkite.myplayground.data.Card;

public class CardLibrary {
    private RecyclerView recycler;
    private CardAdapter cardAdapter;

    public CardLibrary(RecyclerView recyclerView) {
        recycler = recyclerView;

        recycler.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 5));
        //recycler.setAdapter();
    }

    public void setDataSet(Card[] cards) {
        cardAdapter = new CardAdapter();
        cardAdapter.setCards(cards);
        recycler.setAdapter(cardAdapter);
    }

}
