package com.example.diplommobileapp.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplommobileapp.R;

public class ChatViewHolder extends RecyclerView.ViewHolder {
    private ImageView chatImage;
    private TextView nameTv,messageTv,timeTv,countTv;
    public ChatViewHolder(@NonNull View itemView) {
        super(itemView);
        chatImage =itemView.findViewById(R.id.chatImage);
        nameTv = itemView.findViewById(R.id.chatName);
        messageTv = itemView.findViewById(R.id.lastMessage);
        timeTv = itemView.findViewById(R.id.lastMessageTime);
        countTv = itemView.findViewById(R.id.notCheckCount);
    }

    public ImageView getChatImage() {
        return chatImage;
    }
    public TextView getCountTv() {
        return countTv;
    }
    public TextView getMessageTv() {
        return messageTv;
    }
    public TextView getTimeTv() {
        return timeTv;
    }
    public TextView getNameTv() {
        return nameTv;
    }
}
