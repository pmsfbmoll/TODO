package com.example.todo;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

public class CrearItem extends AppCompatActivity {
    private EditText nameView;
    private TextView dateView;
    private CheckBox checkbox;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_item);
        intent= getIntent();
        this.nameView= findViewById(R.id.name);
        this.dateView= findViewById(R.id.dateView);

        this.dateView.setText(String.valueOf(new Date()));

    }

    public void onClick(View v){
        String name = nameView.getText().toString();

        if(!name.equals("")) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", name);
            resultIntent.putExtra("creation", String.valueOf(new Date()));
            resultIntent.putExtra("finish",dateView.getText().toString());
            resultIntent.putExtra("checked", "0");
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

}
