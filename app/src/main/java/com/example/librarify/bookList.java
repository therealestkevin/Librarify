package com.example.librarify;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import gsonParsing.OuterURL;

public class bookList extends AppCompatActivity implements RecycleListener{
    private FloatingActionButton addBookButton;
    private Toolbar topToolBarBook;
    private RecyclerView bookListView;
    private BookAdapter adapter;
    private BookViewModel bookModel;
    private Menu menu;

    private static int intID=0;
    public static final int NEW_BOOK_ACTIVITY_REQUEST_CODE = 1;
    public static int sortMethod=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        topToolBarBook = (Toolbar) findViewById(R.id.topToolBarBook);

        setSupportActionBar(topToolBarBook);
        topToolBarBook.setTitle("Your Library");



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        topToolBarBook.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        bookListView = findViewById(R.id.bookListView);

        bookListView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(this);
        bookListView.setAdapter(adapter);
        adapter.setRecycleListener(this);
        bookModel = ViewModelProviders.of(this).get(BookViewModel.class);
        bookModel.getAllBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                adapter.setBooks(books);
                switch(bookList.sortMethod){
                    case -1:{
                        break;
                    }
                    case 0:{
                        adapter.filterByAlpha(true);
                        break;
                    }
                    case 1:{
                        adapter.filterByAlpha(false);
                        break;
                    }
                    case 2:{
                        adapter.filterByAuthor(true);
                    }
                    case 3:{
                        adapter.filterByAuthor(false);
                    }
                    case 4:{
                        adapter.filterByDate(true);
                    }
                    case 5:{
                        adapter.filterByDate(false);
                    }
                }
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
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        this.menu=menu;
       android.widget.SearchView sview = (android.widget.SearchView) menu.findItem(R.id.action_search).getActionView();
        sview.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                    adapter.filter(s);
                return true;
            }
        });

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.titleasc: {

                adapter.filterByAlpha(true);
                sortMethod = 0;
                unCheckAll(topToolBarBook.getMenu());
                if (!item.isChecked()) {
                    item.setChecked(true);
                }
                return true;
            }
            case R.id.titledesc: {
                sortMethod = 1;
                adapter.filterByAlpha(false);
                unCheckAll(topToolBarBook.getMenu());
                if (!item.isChecked()) {
                    item.setChecked(true);
                }
                return true;
            }
            case R.id.authorasc: {
                sortMethod = 2;
                adapter.filterByAuthor(true);
                unCheckAll(topToolBarBook.getMenu());
                if (!item.isChecked()) {
                    item.setChecked(true);
                }
                return true;
            }
            case R.id.authordesc: {
                sortMethod = 3;
                adapter.filterByAuthor(false);
                unCheckAll(topToolBarBook.getMenu());
                if (!item.isChecked()) {
                    item.setChecked(true);
                }
                return true;
            }
            case R.id.dateasc: {
                sortMethod = 4;
                adapter.filterByDate(true);
                unCheckAll(topToolBarBook.getMenu());
                if (!item.isChecked()) {
                    item.setChecked(true);
                }
                return true;
            }
            case R.id.datedesc: {
                sortMethod = 5;
                adapter.filterByDate(false);
                unCheckAll(topToolBarBook.getMenu());
                if (!item.isChecked()) {
                    item.setChecked(true);
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void unCheckAll(Menu menu){
        for(int i=0; i<menu.size();i++){
            MenuItem b = menu.getItem(i);

            if(b.hasSubMenu()){
                unCheckAll(b.getSubMenu());
            }else{
                b.setChecked(false);
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent dat){
        super.onActivityResult(requestCode,resultCode,dat);

        if(requestCode== NEW_BOOK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            String isbnResult = dat.getStringExtra(cameraCapture.EXTRA_REPLY2);
            Log.i("isbn",isbnResult);

            Book book = new Book(intID++,new Gson().fromJson(dat.getStringExtra(cameraCapture.EXTRA_REPLY), OuterURL.class),
                    java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()),isbnResult);

            bookModel.insert(book);

        }else{
            Toast.makeText(getApplicationContext(),"Entry Failed",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onClick(View view, int position) {
         OuterURL book= BookAdapter.mBook.get(position).getBookList();

        Intent bookInfoIntent = new Intent(getApplicationContext(),BookViewActivity.class);
        bookInfoIntent.putExtra("jsonStuff", new Gson().toJson(book));
        startActivity(bookInfoIntent);


    }
    public void onEntrySort(int code){
        switch(code){
            case -1:{
                break;
            }
            case 0:{
                adapter.filterByAlpha(true);
            }
            case 1:{
                adapter.filterByAlpha(false);
            }
        }
    }


}
