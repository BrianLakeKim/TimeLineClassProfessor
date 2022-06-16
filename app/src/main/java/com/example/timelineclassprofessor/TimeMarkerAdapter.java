package com.example.timelineclassprofessor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TimeMarkerAdapter extends RecyclerView.Adapter<TimeMarkerAdapter.ViewHolder> {
    ArrayList<Comment> items;
    Context context;

    public interface OnItemClickListener{
        void onItemClicked(int position, String data);
    }

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        itemClickListener = listener;
    }

    public TimeMarkerAdapter(ArrayList<Comment> items, Context context){
        this.items = items;
        this.context = context;
    }


    @NonNull
    @Override
    public TimeMarkerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.timemarker_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeMarkerAdapter.ViewHolder holder, int position) {

        holder.tvName.setText(items.get(position).getName());
        holder.tvVideoTime.setText(items.get(position).getVideoTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "";
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION){
                    data = holder.tvVideoTime.getText().toString();
                }
                itemClickListener.onItemClicked(position, data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public void addItem(Comment item){items.add(item);}

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvVideoTime;

        public ViewHolder(View itemView){
            super(itemView);

            tvName = itemView.findViewById(R.id.studentName);
            tvVideoTime = itemView.findViewById(R.id.videoTime);

        }

        public void setItem(Comment item){
            tvName.setText(item.getName());
            tvVideoTime.setText(item.getVideoTime());
        }
    }
}
