package com.thanhan.realtimedatabasefirebase_197pm09415;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ListView listcontact;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matching();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listcontact.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference reference = database.getReference("contacts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()
                     ) {
                    String key = data.getKey();
                    String value = data.getValue().toString();
                    adapter.add(key+"\n"+value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Firebase", "loadPost:onCancelled", databaseError.toException());
            }
        });

        listcontact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = adapter.getItem(position);
                String key = data.split("\n")[0];
                Intent intent = new Intent(getApplicationContext(),CapNhatContactActivity.class);
                intent.putExtra("ID",key);
                startActivity(intent);
            }
        });
    }

    private void matching() {
        listcontact = (ListView) findViewById(R.id.lv_contact);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menuAdd){
            String data = adapter.getItem(adapter.getCount()-1);
            String sid = data.split("\n")[0];
            int i_id = Integer.parseInt(sid);
            Intent intent = new Intent(this,ThemContactActivity.class);
            intent.putExtra("ID", i_id+1);
            startActivity(intent);
        }
        else if (item.getItemId()==R.id.menuIntro){
            Toast.makeText(this,"Welcome to my app", Toast.LENGTH_LONG).show();
        }
        else if (item.getItemId()==R.id.menuSignup){
            startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}