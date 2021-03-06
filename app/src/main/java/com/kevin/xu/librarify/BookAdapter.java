package com.kevin.xu.librarify;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xu.librarify.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kevin.xu.roomDB.Book;

class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>{

   // ArrayList<String> books;

    public static List<Book> mBook = new ArrayList<>();
    public static List<Book> copyBook;
    //copyBook used to facilitate searching mechanism
    private LayoutInflater inflate1;
    private Context context;
    private RecycleListener itemClicked;
    private final DateFormat generalFormat =new SimpleDateFormat("MMM d,yyyy HH:mm:ss aa");
    BookAdapter(Context context){
        this.context=context;

        inflate1 = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflate1.inflate(R.layout.book_row, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
       if(mBook!=null){
           //Setting up the view for each cardview, setting all fields
           final Book cur = mBook.get(position);
           holder.bookName.setText(cur.getBookList().getItems().get(0).getVolumeInfo().getTitle());
            if(cur.isStartSchedule()){
                holder.bookSchedule.setText("Resume Schedule");
            }
           holder.authorView.setText(cur.getAuthor().toString()
                   .replace("[", "")
                   .replace("]", "")
                   .trim());
           try {
               if(cur.getBookList().getItems().get(0).getVolumeInfo().getImageLinks().getThumbnail()!=null
                       && !"".equals(cur.getBookList().getItems().get(0).getVolumeInfo().getImageLinks().getThumbnail())){
                   holder.bookViewImg.setImageDrawable(new BookViewActivity.RetrieveDrawableTask(cur.getBookList()
                           .getItems().get(0).getVolumeInfo().getImageLinks().getThumbnail()).execute().get());
               }

           }catch (NullPointerException e){
            e.printStackTrace();
           }catch (ExecutionException e) {
               e.printStackTrace();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
        holder.timeAdded.setText("Added "+cur.getDateTime());
        holder.bookSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scheduleIntent = new Intent(context, bookSchedule.class);
                scheduleIntent.putExtra("BookPosition",position);
                context.startActivity(scheduleIntent);
            }
        });
       }

       // holder.bookName.setText(books.get(position));
    }

    public void restoreItem(Book book, int position){
        //Used in junction with the undo snackbar
        bookList.bookModel.insert(book);
        mBook.add(position,book);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        //return books.size();
        if(mBook!=null){
            return mBook.size();
        }else{
            return 0;
        }
    }

    void setBooks(List<Book> books){

        //Updates Adapter's dataset upon LiveData change via Observer

        mBook = books;

        copyBook = books;

        notifyDataSetChanged();
    }

    void filter(String text){
        //Handles Search functionality withint toolbar
        List<Book> temp = new ArrayList<>();
        for(Book b : copyBook){
            boolean author = false;
            for(String authors : b.getAuthor()){
                if(authors.toLowerCase().contains(text)){
                    author=true;
                    //Checks if keyword is contained in authors
                }
            }
            if(b.getTitle().toLowerCase().contains(text)||author){
                temp.add(b);
                //keyword either contained in title or author, then added to temp arraylist
            }
        }
        mBook = temp;
        //setting recyclerview data to those that are searched
        notifyDataSetChanged();

    }
    public void filterByAlpha(boolean direction){
        if(direction==true){
            Collections.sort(mBook, new Comparator<Book>() {
                @Override
                public int compare(Book book, Book t1) {
                    return book.getTitle().compareToIgnoreCase(t1.getTitle());
                }
            });
            notifyDataSetChanged();
        }else if(direction==false){
            Collections.sort(mBook,new Comparator<Book>() {
                @Override
                public int compare(Book book, Book t1) {
                    return t1.getTitle().compareToIgnoreCase(book.getTitle());
                }
            });
            notifyDataSetChanged();

        }


    }
    public void filterByAuthor(boolean direction){
        if(direction==true){
            //Using comparator interface to compare author names
            Collections.sort(mBook, new Comparator<Book>() {
                @Override
                public int compare(Book book, Book t1) {
                    Log.i("last Names",book.getLastName()+t1.getLastName());
                    return book.getLastName().compareToIgnoreCase(t1.getLastName());
                }
            });
            notifyDataSetChanged();
        }else if(direction==false){
            Collections.sort(mBook, new Comparator<Book>() {
                @Override
                public int compare(Book book, Book t1) {
                    return t1.getLastName().compareToIgnoreCase(book.getLastName());
                }
            });
            notifyDataSetChanged();
        }
    }
    public void filterByDate(boolean direction){
        if(direction==true){
            //Comparator is built into Date
            Collections.sort(mBook, new Comparator<Book>() {
                @Override
                public int compare(Book book, Book t1) {
                    try {
                        return generalFormat.parse(t1.getDateTime()).compareTo(generalFormat.parse(book.getDateTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
            notifyDataSetChanged();
        }else if(direction==false){
            Collections.sort(mBook, new Comparator<Book>() {
                @Override
                public int compare(Book book, Book t1) {
                    try {
                        return generalFormat.parse(book.getDateTime()).compareTo(generalFormat.parse(t1.getDateTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });
            notifyDataSetChanged();
        }
    }
    public void setRecycleListener(RecycleListener onClick){
        itemClicked = onClick;
    }
    //Functions as the onClick listener for each card in RecyclerView, leading to BookViewActivity
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookAdapter that = (BookAdapter) o;
        return Objects.equals(inflate1, that.inflate1) &&
                Objects.equals(context, that.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inflate1, context);
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //Holds card info
        public TextView bookName;
        public ImageView bookViewImg;
        public TextView timeAdded;
        public Button bookSchedule;
        public TextView authorView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.book_name);
            bookViewImg = itemView.findViewById(R.id.bookViewImg);
            timeAdded = itemView.findViewById(R.id.timeAdded);
            authorView = itemView.findViewById(R.id.authorView);
            bookSchedule = itemView.findViewById(R.id.startSchedule);
            itemView.setTag(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(itemClicked!=null){
                itemClicked.onClick(view,getAdapterPosition());
            }
        }
    }
}
