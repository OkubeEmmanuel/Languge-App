package com.example.odeh.benue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign tiv_text_view to a variable
        TextView tiv = findViewById(R.id.tiv_text_view);

        //Assign idoma_text_view to a variable
        TextView idoma = findViewById(R.id.idoma_text_view);

        //set onClick listener to the Tiv textView
        tiv.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the Tiv View is clicked on.
            @Override
            public void onClick(View view) {
                Intent tivIntent = new Intent(MainActivity.this, CategoryActivity.class);
                tivIntent.putExtra("lang", "tiv" );
                startActivity(tivIntent);
            }
        });

        //set onClick listener to the idoma textView
        idoma.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the idoma View is clicked on.
            @Override
            public void onClick(View view) {
                Intent idomaIntent = new Intent(MainActivity.this, CategoryActivity.class);
                idomaIntent.putExtra("lang","idoma");
                startActivity(idomaIntent);
            }
        });
    }
}
