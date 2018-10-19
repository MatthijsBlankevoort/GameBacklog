package com.example.matthijsblankevoort.gamebacklog;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

public class CreateGameActivity extends AppCompatActivity {

    private FloatingActionButton saveButton;

    private TextInputLayout titleInput;
    private TextInputLayout platformInput;
    private TextInputLayout notesInput;
    private Spinner statusInput;
    private GameEntity gameEntity;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        saveButton = findViewById(R.id.saveButton);

        titleInput = findViewById(R.id.titleInput);
        platformInput = findViewById(R.id.platFormInput);
        notesInput = findViewById(R.id.notesInput);
        statusInput = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusInput.setAdapter(adapter);

        if (getIntent().getSerializableExtra("gameEntity") != null) {
            gameEntity = (GameEntity) getIntent().getSerializableExtra("gameEntity");
            titleInput.getEditText().setText(gameEntity.getTitle());
            platformInput.getEditText().setText(gameEntity.getPlatform());
            notesInput.getEditText().setText(gameEntity.getNotes());
            List<String> statusArrayList = Arrays.asList(getResources().getStringArray(R.array.status_array));
            statusInput.setSelection(statusArrayList.indexOf(gameEntity.getStatus()));

            saveButton.setOnClickListener(new View.OnClickListener() {
                /**
                 * 
                 * @param view
                 */
                @Override
                public void onClick(View view) {
                    gameEntity.setTitle(titleInput.getEditText().getText().toString());
                    gameEntity.setPlatform(platformInput.getEditText().getText().toString());
                    gameEntity.setNotes(notesInput.getEditText().getText().toString());
                    gameEntity.setStatus(statusInput.getSelectedItem().toString());

                    AppDatabase.getAppDatabase(CreateGameActivity.this).gameDAO().update(gameEntity);
                    startActivity(new Intent(CreateGameActivity.this, MainActivity.class));
                }
            });
        } else {
            saveButton.setOnClickListener(new View.OnClickListener() {
                /**
                 *
                  * @param view
                 */
                @Override
                public void onClick(View view) {
                    GameEntity gameEntity = new GameEntity();

                    gameEntity.setTitle(titleInput.getEditText().getText().toString());
                    gameEntity.setPlatform(platformInput.getEditText().getText().toString());
                    gameEntity.setNotes(notesInput.getEditText().getText().toString());
                    gameEntity.setStatus(statusInput.getSelectedItem().toString());
                    AppDatabase.getAppDatabase(CreateGameActivity.this).gameDAO().insert(gameEntity);
                    startActivity(new Intent(CreateGameActivity.this, MainActivity.class));
                }
            });
        }
    }
}
