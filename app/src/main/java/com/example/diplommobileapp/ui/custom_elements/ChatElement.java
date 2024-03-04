package com.example.diplommobileapp.ui.custom_elements;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.diplommobileapp.R;
import com.example.diplommobileapp.data.viewModels.ChatViewModel;
import com.example.diplommobileapp.services.imageworker.ImageUtils;

import org.w3c.dom.Text;

public class ChatElement extends LinearLayout {
    private final Context context;
    ChatViewModel chat;
    public ChatElement(Context context) {
        super(context);
        this.context=context;
        init();
    }
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.chat_view, this, true);
    }
    public void setData(ChatViewModel vm){
        ((TextView)findViewById(R.id.chatName)).setText(vm.getName());
        ((TextView)findViewById(R.id.lastMessage)).setText(vm.getLastMessage());
        ((TextView)findViewById(R.id.lastMessageTime)).setText(vm.getLastMessageTime());
        ((TextView)findViewById(R.id.notCheckCount)).setText(vm.getNotCheckedCount());
        byte[] image = vm.getPreviewImage();
        if (image!=null){
            ImageUtils.setImageViewFromByteArray(image, ((ImageView)findViewById(R.id.eventImageView)));
        }
    }
}
