package com.example.sqlitedatatorecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerContactAdapter extends RecyclerView.Adapter<RecyclerContactAdapter.ViewHolder> {
    Context context;
    ArrayList<ContactModel> contactList;

    public RecyclerContactAdapter(Context context, ArrayList<ContactModel> contactList)
    {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.col_id.setText(String.valueOf(contactList.get(position).id));
        holder.col_name.setText(contactList.get(position).name);
        holder.col_contact_no.setText(contactList.get(position).contact_no);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView col_id, col_name, col_contact_no;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            col_id = itemView.findViewById(R.id.col_id);
            col_name = itemView.findViewById(R.id.col_name);
            col_contact_no = itemView.findViewById(R.id.col_contact_no);
        }
    }
}
