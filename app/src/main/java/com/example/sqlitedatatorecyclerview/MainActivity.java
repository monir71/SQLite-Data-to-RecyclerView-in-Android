package com.example.sqlitedatatorecyclerview;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        /*
        try {
            SQLiteDataModel model = new SQLiteDataModel(this);
            //model.addData("Md Moniruzzaman", "01710389323");
            //model.addData("Hamidur Rahman", "017403896542");
            //model.addData("Jahidul Islam", "01841589674");

            ArrayList<ContactModel> data = model.fetchContactData();

            for (int i = 0; i < data.size(); i++)
            {
                Log.d("Contact Data: ", "ID: " + data.get(i).id + ", Name : " + data.get(i).name + ", Contact Number: " +
                        data.get(i).contact_no + "!");
            }

        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        */

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SQLiteDataModel model = new SQLiteDataModel(this);
        ArrayList<ContactModel> data = model.fetchContactData();

        /*
        ArrayList<ContactModel> data = new ArrayList<>();
        ContactModel model = new ContactModel();
        model.id = 1;
        model.name = "Monir";
        model.contact_no = "01710389323";
        data.add(model);
        data.add(model);
        data.add(model);

        for (int i = 0; i < data.size(); i++)
        {
            Log.d("Contact Data: ", "ID: " + data.get(i).id + ", Name : " + data.get(i).name + ", Contact Number: " +
                    data.get(i).contact_no + "!");
        }
        */

        RecyclerContactAdapter adapter = new RecyclerContactAdapter(this, data);
        recyclerView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}