package com.example.odeh.benue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Intent intent = getIntent();

        /*Variable that holds crypto currency*/
        final String lang = intent.getStringExtra("lang");

        TextView family = findViewById(R.id.family);

        //set onClick listener to the family textView
        family.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the idoma View is clicked on.
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, FamilyActivity.class);
                intent.putExtra("lang",lang);
                startActivity(intent);
            }
        });

        TextView numbers = findViewById(R.id.numbers);

        //set onClick listener to the numbers textView
        numbers.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the idoma View is clicked on.
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, NumbersActivity.class);
                intent.putExtra("lang",lang);
                startActivity(intent);
            }
        });

        TextView phrases = findViewById(R.id.phrases);

        //set onClick listener to the phrases textView
        phrases.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the idoma View is clicked on.
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, PhrasesActivity.class);
                intent.putExtra("lang",lang);
                startActivity(intent);
            }
        });

        TextView colors = findViewById(R.id.colors);

        //set onClick listener to the colours textView
        colors.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the idoma View is clicked on.
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, ColorsActivity.class);
                intent.putExtra("lang",lang);
                startActivity(intent);
            }
        });

        TextView words = findViewById(R.id.words);

        //set onClick listener to the word textView
        words.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the idoma View is clicked on.
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, WordsActivity.class);
                intent.putExtra("lang",lang);
                startActivity(intent);
            }
        });
    }
}
