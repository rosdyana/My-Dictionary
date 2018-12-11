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

import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import mydictionary.sleepybear.com.mydictionary.R;
import mydictionary.sleepybear.com.mydictionary.model.DictionaryModel;

public class CustomSuggestionAdapter extends SuggestionsAdapter<DictionaryModel, CustomSuggestionAdapter.SuggestionHolder> {
    public CustomSuggestionAdapter(LayoutInflater inflater) {
        super(inflater);
    }

    @Override
    public void onBindSuggestionHolder(DictionaryModel suggestion, CustomSuggestionAdapter.SuggestionHolder holder, int position) {
        holder.keyword.setText(suggestion.getKeyword());
        holder.value.setText(suggestion.getValue());
    }

    @Override
    public int getSingleViewHeight() {
        return 0;
    }

    @NonNull
    @Override
    public SuggestionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = getLayoutInflater().inflate(R.layout.item_custom_suggestion, viewGroup, false);
        return new SuggestionHolder(view);
    }

    static class SuggestionHolder extends RecyclerView.ViewHolder{
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
