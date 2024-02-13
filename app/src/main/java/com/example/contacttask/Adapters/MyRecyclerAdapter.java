package com.example.contacttask.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacttask.R;
import com.example.contacttask.database.Contact;

import java.util.ArrayList;
import java.util.List;


public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ContactHolder> {
    private List<Contact> contacts = new ArrayList<>();
    private OnItemClickListener listener;
    private boolean showNameOnly = false;

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new ContactHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        Contact currentContact = contacts.get(position);
        if (showNameOnly) {
            holder.phoneTv.setVisibility(View.GONE); // Hide phone number TextView
            holder.nameTv.setText(currentContact.getFirstName());
        } else {
            holder.phoneTv.setVisibility(View.VISIBLE); // Show phone number TextView
            holder.phoneTv.setText(currentContact.getPhoneNumber());
            holder.nameTv.setText(currentContact.getFirstName());
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContact(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public Contact getContactPos(int position) {
        return contacts.get(position);
    }

    class ContactHolder extends RecyclerView.ViewHolder {
        private TextView nameTv;
        private TextView phoneTv;


        public ContactHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.item_username);
            phoneTv = itemView.findViewById(R.id.item_Phone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAbsoluteAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(contacts.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setShowNameOnly(boolean showNameOnly) {
        this.showNameOnly = showNameOnly;
        notifyDataSetChanged();
    }

        //tried to cnahge item to item2


    /*class ContactWithLastNameHolder extends RecyclerView.ViewHolder {
        private TextView nameTv;
        private TextView lastNameTv;
        private TextView phoneTv;

        public ContactWithLastNameHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.item_username);
            lastNameTv = itemView.findViewById(R.id.item_lastname);
            phoneTv = itemView.findViewById(R.id.item_Phone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(contacts.get(position));
                    }
                }
            });
        }

        public void bind(Contact contact) {
            nameTv.setText(contact.getName());
            lastNameTv.setText(contact.getLastName());
            phoneTv.setText(contact.getPhoneNumber());
        }
    }*/
}
