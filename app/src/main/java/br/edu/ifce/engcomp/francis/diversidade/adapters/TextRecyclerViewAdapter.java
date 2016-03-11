package br.edu.ifce.engcomp.francis.diversidade.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.ifce.engcomp.francis.diversidade.R;
import br.edu.ifce.engcomp.francis.diversidade.interfaces.RecyclerViewOnClickListenerHack;
import br.edu.ifce.engcomp.francis.diversidade.model.TextBlog;

/**
 * Created by Joamila on 11/03/2016.
 */
public class TextRecyclerViewAdapter extends RecyclerView.Adapter<TextRecyclerViewAdapter.ViewHolder> {
    private ArrayList<TextBlog> textsDataSource;
    private Context context;
    private RecyclerViewOnClickListenerHack mRecycleViewOnClickListenerHack;
    private TextRecyclerViewAdapter itemListener;

    public TextRecyclerViewAdapter(Context context, ArrayList<TextBlog> texts) {
        this.textsDataSource = texts;
        this.context = context;
    }

    public TextRecyclerViewAdapter(Context context, ArrayList<TextBlog> texts, TextRecyclerViewAdapter itemListener) {
        this.textsDataSource = texts;
        this.context = context;
        this.itemListener = itemListener;
    }

    public TextBlog getItem(int positon){
        return textsDataSource.get(positon);
    }

    public void setRecycleViewOnClickListenerHack(RecyclerViewOnClickListenerHack rView) {
        this.mRecycleViewOnClickListenerHack = rView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_layout_text_info,parent,false);

        return new ViewHolder (itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        TextBlog textBlog = this.textsDataSource.get(position);

        viewHolder.textTitleTextView.setText(textBlog.getTitle());
        viewHolder.textContentTextView.setText(textBlog.getContent());
        viewHolder.textSourceTextView.setText(textBlog.getSource());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textTitleTextView;
        private TextView  textContentTextView;
        private TextView  textSourceTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            this.textTitleTextView             = (TextView)  itemView.findViewById(R.id.adapter_text_title);
            this.textContentTextView           = (TextView)  itemView.findViewById(R.id.adapter_text_content);
            this.textSourceTextView         = (TextView)  itemView.findViewById(R.id.adapter_text_source);

            itemView.setOnClickListener(this);
        }

        public TextView getTextTitleTextView() {
            return textTitleTextView;
        }

        public void setTextTitleTextView(TextView textTitleTextView) {
            this.textTitleTextView = textTitleTextView;
        }

        public TextView getTextContentTextView() {
            return textContentTextView;
        }

        public void setTextContentTextView(TextView textContentTextView) {
            this.textContentTextView = textContentTextView;
        }

        public TextView getTextSourceTextView() {
            return textSourceTextView;
        }

        public void setTextSourceTextView(TextView textSourceTextView) {
            this.textSourceTextView = textSourceTextView;
        }


        @Override
        public void onClick(View view) {
            if(mRecycleViewOnClickListenerHack != null){
                mRecycleViewOnClickListenerHack.onClickListener(view, getAdapterPosition());
            }
        }
    }
}
