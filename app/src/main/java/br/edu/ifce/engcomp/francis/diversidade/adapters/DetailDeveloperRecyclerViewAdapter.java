package br.edu.ifce.engcomp.francis.diversidade.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.ifce.engcomp.francis.diversidade.R;
import br.edu.ifce.engcomp.francis.diversidade.interfaces.RecyclerViewOnClickListenerHack;
import br.edu.ifce.engcomp.francis.diversidade.model.Contact;
import br.edu.ifce.engcomp.francis.diversidade.model.TextBlog;

/**
 * Created by Bolsista on 07/04/2016.
 */
public class DetailDeveloperRecyclerViewAdapter extends RecyclerView.Adapter<DetailDeveloperRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Contact> dataSource;
    private Context context;
    private RecyclerViewOnClickListenerHack mRecycleViewOnClickListenerHack;

    public DetailDeveloperRecyclerViewAdapter(ArrayList<Contact> dataSource, Context context) {
        this.dataSource = dataSource;
        this.context = context;
    }

    public Contact getItem(int positon) {
        return dataSource.get(positon);
    }

    public void setRecycleViewOnClickListenerHack(RecyclerViewOnClickListenerHack rView) {
        this.mRecycleViewOnClickListenerHack = rView;
    }

    @Override
    public DetailDeveloperRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.adapter_detail_developer, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DetailDeveloperRecyclerViewAdapter.ViewHolder holder, int position) {
        Contact contact = this.dataSource.get(position);
        holder.nameTextView.setText(contact.getPath());

        switch (contact.getMedia()) {
            case "gmail":
                holder.contactImageView.setImageResource(R.drawable.ic_gmail);
                break;
            case "facebook":
                holder.contactImageView.setImageResource(R.drawable.ic_facebook);
                break;
            case "twitter":
                holder.contactImageView.setImageResource(R.drawable.ic_twitter);
                break;
            case "instagram":
                holder.contactImageView.setImageResource(R.drawable.ic_instagram);
                break;
            case "github":
                holder.contactImageView.setImageResource(R.drawable.ic_github);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameTextView;
        private ImageView contactImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            this.nameTextView = (TextView) itemView.findViewById(R.id.name_contact_text_view);
            this.contactImageView = (ImageView) itemView.findViewById(R.id.ic_contact);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mRecycleViewOnClickListenerHack != null) {
                mRecycleViewOnClickListenerHack.onClickListener(view, getAdapterPosition());
            }
        }
    }
}
