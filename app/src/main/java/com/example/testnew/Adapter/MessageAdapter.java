package com.example.testnew.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testnew.Model.Chat;
import com.example.testnew.Model.Chatlist;
import com.example.testnew.Model.User;
import com.example.testnew.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RİGHT = 1;

    private Context mContext;
    private List<Chat> mChat;
    private String imageurl;
    private String messageType;

    Chatlist chatlist;
    Chat chat;
    User user;


    FirebaseUser fuser;

    public MessageAdapter(Context mContext, List<Chat> mChat, String imageurl){
        this.mChat = mChat;
        this.mContext = mContext;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RİGHT){

            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else {

            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @RequiresApi(api=Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final Chat chat = mChat.get(position);

        final String from_user = chat.getSender();
        String message_type = chat.getType();

        DatabaseReference mUserDatabase =FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);

    /*    mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild("image")){
                    String receiverImage = dataSnapshot.child("image").getValue().toString();

                    Picasso.with(mContext).load(receiverImage).placeholder(R.mipmap.ic_launcher);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        if (imageurl.equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }
        else{
            Glide.with(mContext).load(imageurl).into(holder.profile_image);
        }

        if (message_type.equals("text")){


            if (from_user.equals(fuser.getUid())){



                holder.txt_seen.setVisibility(View.VISIBLE);
                holder.show_message.setVisibility(View.VISIBLE);
                holder.show_message.setText(chat.getMessage());
                holder.txt_seen.setText(chat.getMessage());

            }else {

                holder.txt_seen.setVisibility(View.GONE);
                holder.show_message.setVisibility(View.VISIBLE);
                holder.show_message.setText(chat.getMessage());
                holder.txt_seen.setText(chat.getMessage());
            }

        }
        else if (message_type.equals("image")){

            if (from_user.equals(fuser.getUid())){

                holder.txt_seen.setVisibility(View.VISIBLE);
                holder.show_message.setVisibility(View.GONE);
                holder.pdfSender.setVisibility(View.GONE);
                holder.messageSenderPicture.setVisibility(View.VISIBLE);
                Picasso.with(mContext).load(chat.getMessage()).into(holder.messageSenderPicture);


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mChat.get(position).getMessage()));
                        holder.itemView.getContext().startActivity(intent);

                    }
                });

            }else {
                holder.txt_seen.setVisibility(View.GONE);
                holder.show_message.setVisibility(View.GONE);
                holder.messageReceiverPicture.setVisibility(View.VISIBLE);
                holder.pdfReciever.setVisibility(View.GONE);
                Picasso.with(mContext).load(chat.getMessage()).into(holder.messageReceiverPicture);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mChat.get(position).getMessage()));
                        holder.itemView.getContext().startActivity(intent);

                    }
                });

            }
        }

        else if (message_type.equals("pdf")){

            if (from_user.equals(fuser.getUid())){

                holder.txt_seen.setVisibility(View.VISIBLE);
                holder.show_message.setVisibility(View.GONE);
                holder.messageSenderPicture.setVisibility(View.GONE);
                holder.pdfSender.setVisibility(View.VISIBLE);
                holder.pdfSender.setBackgroundResource(R.drawable.add_file2);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mChat.get(position).getMessage()));
                        holder.itemView.getContext().startActivity(intent);

                    }
                });

            }else {

                holder.txt_seen.setVisibility(View.GONE);
                holder.show_message.setVisibility(View.GONE);
                holder.messageReceiverPicture.setVisibility(View.GONE);
                holder.pdfReciever.setVisibility(View.VISIBLE);
                holder.pdfReciever.setBackgroundResource(R.drawable.add_file2);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mChat.get(position).getMessage()));
                        holder.itemView.getContext().startActivity(intent);

                    }
                });

            }
        }

        if (position == mChat.size() - 1 ){
            if (chat.isIsseen()){
                holder.txt_seen.setText("Görüldü");
            }else {
                holder.txt_seen.setText("İletildi");
            }
        } else {
            holder.txt_seen.setVisibility(View.GONE);
        }

        }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
        public ImageView profile_image;
        public TextView txt_seen;
        public ImageView messageSenderPicture, messageReceiverPicture, pdfSender, pdfReciever;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen);
            messageSenderPicture = itemView.findViewById(R.id.message_sender_view);
            messageReceiverPicture = itemView.findViewById(R.id.message_receiver_view);
            pdfSender = itemView.findViewById(R.id.message_sender_pdf);
            pdfReciever = itemView.findViewById(R.id.message_receiver_pdf);

        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        if (mChat.get(position).getSender().equals(fuser.getUid())){  // mesajların sağ ve soldaki pozisyonunu belirler
            return MSG_TYPE_RİGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}
