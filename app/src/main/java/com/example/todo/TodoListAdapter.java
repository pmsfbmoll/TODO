package com.example.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ItemViewHolder> {
    private MyDB md;
    private LayoutInflater mInflater;
    private Cursor c;
    private Context context;
    public TodoListAdapter(Context context, MyDB md) {
        mInflater = LayoutInflater.from(context);
        this.md = md;
        this.context=context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){ // Inflate an item view.
                View mItemView = mInflater.inflate(R.layout.item, parent, false);
                return new ItemViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        String id=md.getId(position);
        this.c=md.selectContact(id);
        Log.d("patata",id);
        holder.nameView.setText(c.getString(1));
        holder.dateView.setText(c.getString(2));
        holder.setId(id);
    }

    @Override
    public int getItemCount() {
        return md.getCount();
    }



    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private TextView nameView;
        private TextView dateView;
        private CheckBox completedView;
        private TodoListAdapter mAdapter;
        private String id;

        public ItemViewHolder(View itemView, TodoListAdapter adapter) {
            super(itemView);
            Log.d("patata", "New item");
            this.nameView = (TextView) itemView.findViewById(R.id.name);
            this.dateView= (TextView) itemView.findViewById(R.id.date);
            this.completedView= (CheckBox) itemView.findViewById(R.id.checkBox);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, EditItem.class);
                    i.putExtra("id", id);
                    i.putExtra("name", nameView.getText().toString());
                    i.putExtra("creation", dateView.getText().toString());
                    String comp;
                    if (completedView.isChecked()){
                        comp="1";
                    }else{
                        comp="0";
                    }
                    i.putExtra("complete", comp);
                    context.startActivity(i);
                }
            });
        }

        public void setId(String s){
            this.id=s;
        }
    }
}
