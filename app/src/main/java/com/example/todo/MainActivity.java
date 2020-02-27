package com.example.todo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbhelp;
    protected int count;
    private MyDB md;
    private SQLiteDatabase db;
    private RecyclerView mRecyclerView;
    private TodoListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CrearItem.class);
                startActivityForResult(intent,7);
            }
        });

        this.dbhelp = new MyDatabaseHelper(this);
        this.db = dbhelp.getWritableDatabase();
        this.md = new MyDB(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        try {
            //Aixo nomes son uns inserts inicials de prova
            String today = String.valueOf(new Date());
            md.createRecords("1", "Regar plantes", today, today, "0");
            md.createRecords("2", "Anar a comprar", today, today, "0");
            md.createRecords("3", "Demanar hora metge", today, today, "0");
            md.createRecords("4", "Aprendre a tocar la tuba", today, today, "0");

            Log.d("patata", md.selectContact("3").getString(3));
        } catch (Exception e) {
            Log.d("patata", "La entrada ja existeix");
        }
        count=md.getCount();


        mAdapter = new TodoListAdapter(this, md);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==7){
            md.createRecords(String.valueOf(++count),data.getStringExtra("name"),data.getStringExtra("creation"),data.getStringExtra("finish"),data.getStringExtra("checked"));
            mAdapter.notifyItemInserted(md.getCount());
        }
    }
}
