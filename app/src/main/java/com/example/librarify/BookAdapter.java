package com.example.librarify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

   // ArrayList<String> books;

    private List<Book> mBook;
    private LayoutInflater inflate1;

    BookAdapter(Context context){
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
           Book cur = mBook.get(position);
           holder.bookName.setText(cur.getBookList().getItems().get(0).getVolumeInfo().getTitle());
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.book_name);
        }
    }
}
