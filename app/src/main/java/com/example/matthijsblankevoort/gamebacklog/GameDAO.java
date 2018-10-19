package com.example.matthijsblankevoort.gamebacklog;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GameDAO {
    @Query("SELECT * FROM game")
    List<GameEntity> getAll();

    @Update
    void update(GameEntity gameEntity);

    @Insert
    void insert(GameEntity gameEntity);

    @Delete
    void delete(GameEntity gameEntity);
}
