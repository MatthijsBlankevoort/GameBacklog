package com.example.matthijsblankevoort.gamebacklog;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    private List<GameEntity> gameList;
    private Context context;

    public GameAdapter(Context context, List<GameEntity> gameList){
        this.context = context;
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, final int position) {
        System.out.println(position);
        holder.titleText.setText(gameList.get(position).getTitle());
        holder.platformText.setText(gameList.get(position).getPlatform());
        holder.statusText.setText(gameList.get(position).getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context = view.getContext();
                Intent intent =  new Intent(context, CreateGameActivity.class);
                intent.putExtra("gameEntity", gameList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText;
        private TextView platformText;
        private TextView statusText;
        private TextView dateText;

        public GameViewHolder(View gameCardView) {
            super(gameCardView);
            titleText = itemView.findViewById(R.id.titleText);
            platformText = itemView.findViewById(R.id.platformText);
            statusText = itemView.findViewById(R.id.statusText);
            dateText = itemView.findViewById(R.id.dateText);
        }
    }
}
