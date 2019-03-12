package kevin.xu.librarify;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.xu.librarify.R;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kevin.xu.gsonParsing.OuterURL;
import kevin.xu.roomDB.Book;

import static androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE;

public class bookList extends AppCompatActivity implements RecycleListener{
    private FloatingActionButton addBookButton;
    private Toolbar topToolBarBook;
    private RecyclerView bookListView;
    public static BookAdapter adapter;
    public static BookViewModel bookModel;
    private Menu menu;
    private ConstraintLayout constrainer;
    public static int sortMethod=-1;
    private Paint painter;
    private ColorDrawable mBackground;
    private int backgroundColor;
    private Drawable deleteDrawable;
    private int intrinsicWidth;
    private int intrinsicHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle.getString("GoBack")!=null){
               moveTaskToBack(true);
               startActivity(new Intent(getApplicationContext(),cameraCapture.class));
            }
        }
        constrainer = findViewById(R.id.constrainer);
        setContentView(R.layout.activity_book_list);
        setupMainWindowDisplayMode();
        topToolBarBook = findViewById(R.id.topToolBarBook);
        setSupportActionBar(topToolBarBook);
        topToolBarBook.setTitle("Your Library");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mBackground = new ColorDrawable();
        backgroundColor = Color.parseColor("#b80f0a");
        painter = new Paint();
        painter.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        deleteDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.trash);
        //ColorFilter whiteFilter = new LightingColorFilter(Color.WHITE,Color.WHITE);
        //deleteDrawable.setColorFilter(whiteFilter);
        intrinsicWidth = deleteDrawable.getIntrinsicWidth();
        intrinsicHeight = deleteDrawable.getIntrinsicHeight();
        topToolBarBook.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(returnIntent);

                //onBackPressed();
            }
        });
        bookListView = findViewById(R.id.bookListView);

        bookListView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(this);
        bookListView.setAdapter(adapter);
        adapter.setRecycleListener(this);


        bookModel = ViewModelProviders.of(this).get(BookViewModel.class);

       bookModel.getAllBooks().observeForever(new Observer<List<Book>>() {
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
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
              ) {
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;
                int itemHeight = itemView.getHeight();

                boolean isCancelled = dX == 0 && !isCurrentlyActive;

                if (isCancelled) {
                    clearDraw(c, itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    return;
                }

                mBackground.setColor(backgroundColor);
                mBackground.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                mBackground.draw(c);

                int deleteIconTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
                int deleteIconLeft = itemView.getRight() - deleteIconMargin - intrinsicWidth;
                int deleteIconRight = itemView.getRight() - deleteIconMargin;
                int deleteIconBottom = deleteIconTop + intrinsicHeight;


                deleteDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
                deleteDrawable.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
            private void clearDraw(Canvas c, Float left, Float top, Float right, Float bottom) {
                c.drawRect(left, top, right, bottom, painter);

            }
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0, ItemTouchHelper.LEFT);
            }
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                return false;
            }
            @Override
            public float getSwipeThreshold(@NonNull RecyclerView.ViewHolder viewHolder) {
                return 0.7f;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                final int position = viewHolder.getAdapterPosition();
                Book tempBook = BookAdapter.mBook.get(position);
                bookModel.deleteCertain(BookAdapter.mBook.get(position).getId());
                BookAdapter.mBook.remove(position);
                adapter.notifyItemRemoved(position);

                Snackbar snackUndo = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content),"A Book Was Removed.",Snackbar.LENGTH_LONG);
                snackUndo.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter.restoreItem(tempBook,position);
                        bookListView.scrollToPosition(position);
                    }
                });
                snackUndo.setActionTextColor(Color.YELLOW);
                snackUndo.show();
            }
        };
        ItemTouchHelper helper = new ItemTouchHelper(simpleItemTouchCallback);

        helper.attachToRecyclerView(bookListView);

        addBookButton = findViewById(R.id.addBookButton);
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(bookList.this,cameraCapture.class));
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
    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent dat){
        super.onActivityResult(requestCode,resultCode,dat);

        if(requestCode== NEW_BOOK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            String isbnResult = dat.getStringExtra(cameraCapture.EXTRA_REPLY2);
            Log.i("isbn",isbnResult);

            Book book = new Book(new Gson().fromJson(dat.getStringExtra(cameraCapture.EXTRA_REPLY), OuterURL.class),
                    java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()),isbnResult);

            bookModel.insert(book);


        }else{
            Toast.makeText(getApplicationContext(),"Entry Failed",Toast.LENGTH_LONG).show();
        }
    }*/
    @Override
    public void onClick(View view, int position) {
         OuterURL book= BookAdapter.mBook.get(position).getBookList();

        Intent bookInfoIntent = new Intent(getApplicationContext(),BookViewActivity.class);
        bookInfoIntent.putExtra("jsonStuff", new Gson().toJson(book));
        bookInfoIntent.putExtra("BookPosition2",position);
        startActivity(bookInfoIntent);


    }
    private void setupMainWindowDisplayMode() {
        View decorView = setSystemUiVisibilityMode();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                setSystemUiVisibilityMode();
            }
        });
    }
    private View setSystemUiVisibilityMode() {
        View decorView = getWindow().getDecorView();
        int options;
        options =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(options);
        return decorView;
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
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




}
