package com.example.diplommobileapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplommobileapp.R;
import com.example.diplommobileapp.data.viewModels.ChatViewModel;
import com.example.diplommobileapp.services.imageworker.ImageUtils;
import com.example.diplommobileapp.viewholders.ChatViewHolder;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    private List<ChatViewModel> chats;
    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_view,parent,false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatViewModel chat = chats.get(position);
        holder.getCountTv().setText(chat.getNotCheckedCount());
        holder.getMessageTv().setText(chat.getLastMessage());
        holder.getTimeTv().setText(chat.getLastMessageTime());
        holder.getNameTv().setText(chat.getName());

        byte[] preview = chat.getPreviewImage();
        if (preview!=null)
            ImageUtils.setImageViewFromByteArray(preview, holder.getChatImage());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
}
