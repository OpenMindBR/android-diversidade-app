package br.edu.ifce.engcomp.francis.diversidade.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import br.edu.ifce.engcomp.francis.diversidade.R;
import br.edu.ifce.engcomp.francis.diversidade.interfaces.RecyclerViewOnClickListenerHack;
import br.edu.ifce.engcomp.francis.diversidade.model.TextBlog;

/**
 * Created by Joamila on 11/03/2016.
 */
public class TextRecyclerViewAdapter extends RecyclerView.Adapter<TextRecyclerViewAdapter.ViewHolder> {
    private ArrayList<TextBlog> textsDataSource = new ArrayList<>();
    private Context context;
    private RecyclerViewOnClickListenerHack mRecycleViewOnClickListenerHack;
    private TextRecyclerViewAdapter itemListener;

    public TextRecyclerViewAdapter(Context context, ArrayList<TextBlog> texts) {
        this.textsDataSource = texts;
        this.context = context;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TextBlog textBlog = this.textsDataSource.get(position);

        holder.textTitleTextView.setText(textBlog.getTitle());
        holder.textContentTextView.setText(textBlog.getContent());
        holder.textSourceTextView.setText(textBlog.getSource());
    }

    @Override
    public int getItemCount() {
        return textsDataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textTitleTextView;
        private TextView  textContentTextView;
        private TextView  textSourceTextView;

        public ViewHolder(final View itemView) {
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
