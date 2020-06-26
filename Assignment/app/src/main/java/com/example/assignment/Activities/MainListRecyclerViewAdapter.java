package com.example.assignment.Activities;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.Entities.Contact;
import com.example.assignment.R;

import java.util.ArrayList;
import java.util.Collections;

public class MainListRecyclerViewAdapter extends RecyclerView.Adapter<MainListRecyclerViewAdapter.MainListItemViewHolder> {
    private ArrayList<Contact> contactList;
    private ContactRecordListener contactRecordListener;

    public MainListRecyclerViewAdapter(ArrayList<Contact> contactList, ContactRecordListener contactRecordListener) {
        this.contactList = contactList;
        this.contactRecordListener = contactRecordListener;
    }

    public void reloadContactList (ArrayList<Contact> contactList) {
        this.contactList = contactList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact, parent, false);
        MainListItemViewHolder vh = new MainListItemViewHolder(v, contactRecordListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MainListItemViewHolder holder, int position) {
        holder.txtViewName.setText(contactList.get(position).getfName());
        holder.txtViewEmail.setText(contactList.get(position).getEmail());
        holder.txtViewPhone.setText(contactList.get(position).getPhone());
        holder.txtViewDate.setText(contactList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return contactList == null? 0 : contactList.size();
    }

    class MainListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView txtViewName;
        private TextView txtViewPhone;
        private TextView txtViewEmail;
        private TextView txtViewDate;

        ContactRecordListener contactRecordListener;

        public MainListItemViewHolder(@NonNull View itemView, ContactRecordListener contactRecordListener) {
            super(itemView);
            txtViewName = itemView.findViewById(R.id.txtName_Contact);
            txtViewPhone = itemView.findViewById(R.id.txtPhone_Contact);
            txtViewEmail = itemView.findViewById(R.id.txtEmail_Contact);
            txtViewDate = itemView.findViewById(R.id.txtDate_Contact);

            this.contactRecordListener = contactRecordListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            contactRecordListener.onClickOfContact(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            contactRecordListener.onLongClickOfContact(getAdapterPosition());
            ClipData data = ClipData.newPlainText("ClipData","What's this?");
            View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, myShadowBuilder, v, 0);

            return false;
        }
    }

    public interface ContactRecordListener{
        void onClickOfContact(int position);
        void onLongClickOfContact(int position);
    }

    public ArrayList<Contact> getContact(){
        return contactList;
    }
}
