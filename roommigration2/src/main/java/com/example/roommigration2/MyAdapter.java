package com.example.roommigration2;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Word> allWords = new ArrayList<>();
    private boolean useCardView;

    private WordViewModel wordViewModel;

    public MyAdapter(boolean useCardView, WordViewModel wordViewModel) {
        this.wordViewModel = wordViewModel;
        this.useCardView = useCardView;
    }

    public void setAllWords(List<Word> allWords) {
        this.allWords = allWords;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        if (useCardView){
            itemView = layoutInflater.inflate(R.layout.cell_card_2,parent, false);
        }else{
            itemView = layoutInflater.inflate(R.layout.cell_normal_2,parent,false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Word word = allWords.get(position);
        holder.textViewNumber.setText(String.valueOf(position+1));
        holder.textViewEnglish.setText(word.getWord());
        holder.textViewChinese.setText(word.getChineseMeaning());
        //TODO
        holder.aSwitchChineseInvisible.setOnCheckedChangeListener(null);
        if (word.isChineseInvisible()){
            holder.textViewChinese.setVisibility(View.GONE);
            holder.aSwitchChineseInvisible.setChecked(true);
        }else {
            holder.textViewChinese.setVisibility(View.VISIBLE);
            holder.aSwitchChineseInvisible.setChecked(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://dict.youdao.com/result?word="+ holder.textViewEnglish.getText()+"&lang=en");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.aSwitchChineseInvisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    holder.textViewChinese.setVisibility(View.GONE);
                    word.setChineseInvisible(true);
                    wordViewModel.updateWords(word);
                }else {
                    holder.textViewChinese.setVisibility(View.VISIBLE);
                    word.setChineseInvisible(false);
                    wordViewModel.updateWords(word);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.allWords.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNumber,textViewEnglish,textViewChinese;
        Switch aSwitchChineseInvisible;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewChinese = itemView.findViewById(R.id.textViewChinese);
            this.textViewEnglish = itemView.findViewById(R.id.textViewEnglish);
            this.textViewNumber = itemView.findViewById(R.id.textViewNumber);
            this.aSwitchChineseInvisible = itemView.findViewById(R.id.switchChineseInvisible);
        }
    }
}
