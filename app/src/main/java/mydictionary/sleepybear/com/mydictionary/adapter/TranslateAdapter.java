/*
 * Copyright (c) 2018. Rosdyana Kusuma.
 */

package mydictionary.sleepybear.com.mydictionary.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mydictionary.sleepybear.com.mydictionary.R;
import mydictionary.sleepybear.com.mydictionary.model.DictionaryModel;

public class TranslateAdapter extends RecyclerView.Adapter<TranslateAdapter.SuggestionHolder> {
    private ArrayList<DictionaryModel> dictionaryModelArrayList = new ArrayList<>();

    public TranslateAdapter() {
    }

    public void updateData(ArrayList<DictionaryModel> items) {
        dictionaryModelArrayList = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SuggestionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SuggestionHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_custom_suggestion, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionHolder suggestionHolder, int i) {
        suggestionHolder.keyword.setText(dictionaryModelArrayList.get(i).getKeyword());
        suggestionHolder.value.setText(dictionaryModelArrayList.get(i).getValue());
    }

    @Override
    public int getItemCount() {
        return dictionaryModelArrayList.size();
    }

    public void clear() {
        dictionaryModelArrayList.clear();
    }

    static class SuggestionHolder extends RecyclerView.ViewHolder {
        protected TextView keyword;
        protected TextView value;
        protected ImageView image;

        public SuggestionHolder(View itemView) {
            super(itemView);
            keyword = (TextView) itemView.findViewById(R.id.keyword);
            value = (TextView) itemView.findViewById(R.id.value);
        }
    }
}
