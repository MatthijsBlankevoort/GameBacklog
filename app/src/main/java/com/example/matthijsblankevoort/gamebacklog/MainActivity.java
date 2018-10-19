package com.example.matthijsblankevoort.gamebacklog;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Canvas;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addGameButton;
    public static AppDatabase database;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter gameAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<GameEntity> gameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addGameButton = findViewById(R.id.addGameButton);

        final AppDatabase database = AppDatabase.getAppDatabase(this);
        layoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);

        gameList = database.gameDAO().getAll();
        gameAdapter = new GameAdapter(this, gameList);
        recyclerView.setAdapter(gameAdapter);


        addGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateGameActivity.class);

                startActivity(intent);
            }
        });

        ItemTouchHelper.SimpleCallback swipeDelete = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int mPosition = (viewHolder.getAdapterPosition());

                database.gameDAO().delete(gameList.get(mPosition));

                gameList.remove(mPosition);
                gameAdapter.notifyItemRemoved(mPosition);
            }

        };

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(swipeDelete);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }
}