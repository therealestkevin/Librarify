package com.kevin.xu.librarify;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.camerakit.CameraKitView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xu.librarify.R;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import dmax.dialog.SpotsDialog;
import com.kevin.xu.gsonParsing.OuterURL;
import com.kevin.xu.roomDB.Book;


public class cameraCapture extends AppCompatActivity {
    private CameraKitView cameraView;
    private FloatingActionButton btnDetect;
    private AlertDialog waitingDialog;
    //API Key
    public static final String EXTRA_REPLY = "REPLY";
    public static final String EXTRA_REPLY2 = "ISBN";
    private String ISBN;
    private androidx.appcompat.app.AlertDialog.Builder builder;
    private boolean execute;
    private BookViewModel bookModel2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_capture);
        //setupMainWindowDisplayMode();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bookModel2 = ViewModelProviders.of(cameraCapture.this).get(BookViewModel.class);
        cameraView = (CameraKitView) findViewById(R.id.camera);
        btnDetect = findViewById(R.id.button5);
        waitingDialog = new SpotsDialog.Builder().setContext(this).setMessage("Please wait").setCancelable(false).build();



        btnDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cameraView.captureImage(new  CameraKitView.ImageCallback() {
                    @Override
                    public void onImage(CameraKitView cameraKitView, final byte[] capturedImage) {
                        waitingDialog.show();
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inMutable = true;
                        //Making a Bitmap to be able to be used by Firebase ML
                        Bitmap bmp = BitmapFactory.decodeByteArray(capturedImage, 0, capturedImage.length, options);
                        bmp = Bitmap.createScaledBitmap(bmp,cameraView.getWidth(), cameraView.getHeight(),false);
                        runDetector(bmp,waitingDialog);
                        //Runs Image Recognition

                    }
                });

            }


        });
    }
    static {
        System.loadLibrary("keys");
    }

    public native String getNativeKey();

    private void runDetector(Bitmap bitmap,final AlertDialog bob)  {
        //Detects image and relays informaiton to the process result method
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
        FirebaseVisionBarcodeDetectorOptions options =new FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(
                        FirebaseVisionBarcode.FORMAT_ALL_FORMATS
                )
                .build();
        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance().getVisionBarcodeDetector(options);

        detector.detectInImage(image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionBarcode> firebaseVisionBarcodes) {
                       int i= processResult(firebaseVisionBarcodes);
                        bob.hide();
                        if(i!=1){
                            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(cameraCapture.this);
                            builder.setMessage("No Barcode Detected, Try Again");
                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            androidx.appcompat.app.AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                Toast.makeText(cameraCapture.this, e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private int processResult(List<FirebaseVisionBarcode> firebaseVisionBarcodes)  {
        //evalutes what type of code the image is, then detects the text from it and allows you to fetch that number

        for(FirebaseVisionBarcode item : firebaseVisionBarcodes){
            int val_type = item.getValueType();
            switch(val_type){
            // Only detecting ISBN because the app only wants books
                case FirebaseVisionBarcode.TYPE_ISBN:{

                    try {
                        ISBN = item.getRawValue();
                        //Setting up Google Books API call
                        String key = new String(Base64.decode(getNativeKey(),Base64.DEFAULT));

                        String bookSearchString = "https://www.googleapis.com/books/v1/volumes?" +
                                "q=isbn:" + item.getRawValue() + "&key=" + key;
                        System.out.println(bookSearchString);
                        RetrieveJSONTask bob = new RetrieveJSONTask(bookSearchString);

                        String jsonReqText = bob.execute().get();

                        System.out.println(jsonReqText);
                        Gson gson = new Gson();
                        OuterURL temp = gson.fromJson(jsonReqText, OuterURL.class);

                        ArrayList<String> infoBook = new RetrieveDescriptions(temp.getItems().get(0).getSelfLink()).execute().get();
                        if(temp.getItems().get(0).getVolumeInfo().getDescription()==null){
                            temp.getItems().get(0).getVolumeInfo().setDescription(infoBook.get(0));
                        }
                        Log.i("infoBob", infoBook.toString());
                       String authors= temp.getItems().get(0).getVolumeInfo().getAuthors()
                                .toString().replace("[","").replace("]","");
                       //Setting up Dialog info
                        execute=true;
                        for (int b = 0; b < BookAdapter.mBook.size(); b++) {
                            if (BookAdapter.mBook.get(b).getISBN().equals(ISBN)) {
                                execute = false;

                            }
                        }
                         builder = new androidx.appcompat.app.AlertDialog.Builder(cameraCapture.this);
                        if(execute==true){
                            builder.setMessage("ISBN: " + item.getRawValue() + "\nFORMAT: " + getType(item.getFormat()) + "\nTITLE: " + temp.getItems().get(0)
                                    .getVolumeInfo().getTitle() +"\nAUTHOR(S): " +authors);
                        }else{

                            builder.setMessage(   "\nTITLE: " + temp.getItems().get(0)
                                    .getVolumeInfo().getTitle() + "\n\nAlready In Library. Are You Sure?");
                        }

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setNeutralButton("Add Book", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                    if (temp==null) {
                                    } else {
                                       try{
                                           Book book = new Book(temp, java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()),ISBN,infoBook);
                                           //Adding to DB
                                           bookModel2.insert(book);
                                           Toast.makeText(getApplicationContext(), "Entry Success", Toast.LENGTH_LONG).show();

                                           Intent goToLibrary = new Intent(getApplicationContext(), bookList.class);
                                           startActivity(goToLibrary);
                                       }catch(Exception e){

                                       }

                                    }

                                }


                        });
                        androidx.appcompat.app.AlertDialog dialog = builder.create();
                        dialog.show();

                        return 1;
                    }catch(Exception e){
                            System.out.println("Parsing Failed");
                    }

                }
                break;
                case FirebaseVisionBarcode.TYPE_URL:{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getRawValue()));
                    startActivity(intent);
                }



                default:{


                }
                break;
            }

        }
        return 0;
    }
   static class RetrieveDescriptions extends AsyncTask<Void, Void, ArrayList<String>>{
        private String urlString;
        public RetrieveDescriptions(String urlString){
            this.urlString=urlString;
        }

       @Override
       protected ArrayList<String> doInBackground(Void... voids) {
           String resultString2 = "";
            ArrayList<String> returnInfo = new ArrayList<>();
           try {
               BufferedReader reader = null;
               try {
                   URL url = new URL(urlString);
                   reader = new BufferedReader(new InputStreamReader(url.openStream()));
                   StringBuilder buffer = new StringBuilder();
                   int read;
                   char[] chars = new char[1024];
                   while ((read = reader.read(chars)) != -1)
                       buffer.append(chars, 0, read);

                   resultString2 = buffer.toString();
               } finally {
                   if (reader != null)
                       reader.close();
               }


           } catch (Exception e) {
               e.printStackTrace();
           }
           try{
               JsonParser parser = new JsonParser();
               JsonObject obj = parser.parse(resultString2).getAsJsonObject();
               JsonObject obj2 = obj.get("volumeInfo").getAsJsonObject();
               JsonElement descrip = obj2.get("description");
               returnInfo.add(descrip.getAsString());
               JsonElement categories = obj2.get("categories");
               if(categories==null){

               }else{
                   String[] elements = new Gson().fromJson(categories.toString(),String[].class);

                   returnInfo.addAll(Arrays.asList(elements));
               }

           }catch(Exception e){
               Log.i("errorParse","There was no jsonelement");
               e.printStackTrace();
           }
            if(returnInfo.size()<1){
                returnInfo.add("");
                returnInfo.add("");
            }else if(returnInfo.size()<2){
                returnInfo.add("");
            }
            return returnInfo;
       }
   }
   static class RetrieveJSONTask extends AsyncTask<String, Void, String> {
        String urlString;
        //Goes to API call and result, fetching the whole text JSON from the webpage
        private RetrieveJSONTask(String urlString) {
            super();
            this.urlString = urlString;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                BufferedReader reader = null;
                try {
                    URL url = new URL(urlString);
                    reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    StringBuilder buffer = new StringBuilder();
                    int read;
                    char[] chars = new char[1024];
                    while ((read = reader.read(chars)) != -1)
                        buffer.append(chars, 0, read);

                    return buffer.toString();
                } finally {
                    if (reader != null)
                        reader.close();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
    return null;
        }
    }

    //All of these overrides handle camera functions upon the Activity lifecycle

    @Override
    protected void onResume(){
        super.onResume();
        cameraView.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();
        cameraView.onPause();
        waitingDialog.dismiss();




    }
    @Override
    protected void onStop() {
        cameraView.onStop();
        super.onStop();
    }
    @Override
    protected void onStart() {
        super.onStart();
        cameraView.onStart();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    /*
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
    }*/
    private String getType(int n){
        switch(n){
            case 32: {
                return "EAN_13";
            }
            case 64: {
                return "EAN_8";
            }



        }
        return "UNKNOWN";
    }




}
