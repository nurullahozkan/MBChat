package com.example.testnew.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testnew.Model.Status;
import com.example.testnew.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {

    private Context mContext;
    private List<Status> mStatus;

    FirebaseUser fuser;

    public StatusAdapter(Context mContext, List<Status> mStatus){
        this.mContext = mContext;
        this.mStatus = mStatus;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view =LayoutInflater.from(mContext).inflate(R.layout.fragment_status, parent, false);

        return new StatusAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

     Status status = mStatus.get(position);

        String from_user = status.getSender();
        String message_type = status.getType();

        DatabaseReference mUserDatabase =FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
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
        });

        if (message_type.equals("image")) {
            holder.status_image.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(status.getMessage()).into(holder.status_image);
        }else {
            Toast.makeText(mContext, "Message must be image", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return mStatus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView status_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            status_image = itemView.findViewById(R.id.status_image);
        }
    }

}
