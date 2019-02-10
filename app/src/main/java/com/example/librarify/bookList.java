package com.example.librarify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
public class bookList extends AppCompatActivity {
    private FloatingActionButton addBookButton;
    private Toolbar topToolBarBook;
    private RecyclerView bookListView;
    private BookAdapter adapter;
    private BookViewModel bookModel;
    private static int intID=0;
    public static final int NEW_BOOK_ACTIVITY_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        topToolBarBook = (Toolbar) findViewById(R.id.topToolBarBook);
        setSupportActionBar(topToolBarBook);
        bookListView = findViewById(R.id.bookListView);

        bookListView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(this);
        bookListView.setAdapter(adapter);
        bookModel = ViewModelProviders.of(this).get(BookViewModel.class);
        bookModel.getAllBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                adapter.setBooks(books);
            }
        });



        addBookButton = findViewById(R.id.addBookButton);
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(bookList.this,cameraCapture.class),NEW_BOOK_ACTIVITY_REQUEST_CODE);
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent dat){
        super.onActivityResult(requestCode,resultCode,dat);

        if(requestCode== NEW_BOOK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){


            Book book = new Book(intID++,new Gson().fromJson(dat.getStringExtra(cameraCapture.EXTRA_REPLY),OuterURL.class), java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
            bookModel.insert(book);


        }else{
            Toast.makeText(getApplicationContext(),"Entry Failed",Toast.LENGTH_LONG).show();
        }
    }
}
