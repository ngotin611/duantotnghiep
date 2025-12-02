package com.example.duantn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duantn.R;
import com.example.duantn.models.NotificationDomain;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private ArrayList<NotificationDomain> notifications;
    private Context context;
    private OnNotificationClickListener listener;

    public interface OnNotificationClickListener {
        void onNotificationClick(NotificationDomain notification);
        void onMarkAsRead(NotificationDomain notification);
    }

    public NotificationAdapter(ArrayList<NotificationDomain> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;
    }

    public void setOnNotificationClickListener(OnNotificationClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationDomain notification = notifications.get(position);

        holder.notificationTitle.setText(notification.getTitle());
        holder.notificationMessage.setText(notification.getMessage());
        holder.notificationTime.setText(notification.getTime());

        // Set icon based on notification type
        switch (notification.getType()) {
            case "order":
                holder.notificationIcon.setImageResource(R.drawable.ic_notification_order);
                break;
            case "promotion":
                holder.notificationIcon.setImageResource(R.drawable.ic_notification_promotion);
                break;
            case "system":
                holder.notificationIcon.setImageResource(R.drawable.ic_notification_system);
                break;
        }

        // Show/hide badge based on read status
        if (notification.isRead()) {
            holder.notificationBadge.setVisibility(View.GONE);
            holder.btnMarkRead.setVisibility(View.GONE);
        } else {
            holder.notificationBadge.setVisibility(View.VISIBLE);
            holder.btnMarkRead.setVisibility(View.VISIBLE);
        }

        // Click listeners
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNotificationClick(notification);
            }
        });

        holder.btnMarkRead.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMarkAsRead(notification);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void updateNotifications(ArrayList<NotificationDomain> newNotifications) {
        this.notifications = newNotifications;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView notificationIcon, btnMarkRead;
        TextView notificationTitle, notificationMessage, notificationTime;
        View notificationBadge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationIcon = itemView.findViewById(R.id.notification_icon);
            notificationTitle = itemView.findViewById(R.id.notification_title);
            notificationMessage = itemView.findViewById(R.id.notification_message);
            notificationTime = itemView.findViewById(R.id.notification_time);
            btnMarkRead = itemView.findViewById(R.id.btn_mark_read);
            notificationBadge = itemView.findViewById(R.id.notification_badge);
        }
    }
}