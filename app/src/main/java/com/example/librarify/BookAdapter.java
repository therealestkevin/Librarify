package com.example.librarify;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

   // ArrayList<String> books;

    private List<Book> mBook;
    private LayoutInflater inflate1;
    private Context context;
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
           final Book cur = mBook.get(position);
           holder.bookName.setText(cur.getBookList().getItems().get(0).getVolumeInfo().getTitle());
           try {
               holder.bookViewImg.setImageDrawable(new BookViewActivity.RetrieveDrawableTask(cur.getBookList()
               .getItems().get(0).getVolumeInfo().getImageLinks().getThumbnail()).execute().get());
           } catch (ExecutionException e) {
               e.printStackTrace();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
        holder.timeAdded.setText("Added "+cur.getDateTime());
           holder.bookInfoBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent bookInfoIntent = new Intent(context,BookViewActivity.class);
                   bookInfoIntent.putExtra("jsonStuff", new Gson().toJson(cur.getBookList()));
                   context.startActivity(bookInfoIntent);
               }
           });
       }

       // holder.bookName.setText(books.get(position));
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
        mBook = books;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView bookName;
        public ImageView bookViewImg;
        public TextView timeAdded;
        public Button bookInfoBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.book_name);
            bookViewImg = itemView.findViewById(R.id.bookViewImg);
            timeAdded = itemView.findViewById(R.id.timeAdded);
            bookInfoBtn = itemView.findViewById(R.id.bookInfoBtn);

        }
    }
}
