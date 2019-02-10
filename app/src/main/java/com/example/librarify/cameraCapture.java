package com.example.librarify;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.gson.Gson;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import dmax.dialog.SpotsDialog;


public class cameraCapture extends AppCompatActivity {
    private CameraView cameraView;
    private Button btnDetect;
    private AlertDialog waitingDialog;
    private String browserKey = "AIzaSyB4FziQm9LM2Nahb3SsKbME7_cTq60x2_Q";
    private String imageURLPassed = "";
    private String bookDescriptionPassed = "";
    private double ratingStar;
    private int numRatings;
    private String jsonString;
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    private androidx.appcompat.app.AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_capture);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cameraView = (CameraView) findViewById(R.id.cameraview);
        btnDetect = (Button)findViewById(R.id.button5);
        waitingDialog = new SpotsDialog.Builder().setContext(this).setMessage("Please wait").setCancelable(false).build();



        btnDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraView.start();
                cameraView.captureImage();
            }
        });
        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                waitingDialog.show();
                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap,cameraView.getWidth(), cameraView.getHeight(),false);
                cameraView.stop();
                runDetector(bitmap,waitingDialog);

                cameraView.start();

            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });
    }

    private void runDetector(Bitmap bitmap,final AlertDialog bob)  {
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

        for(FirebaseVisionBarcode item : firebaseVisionBarcodes){
            int val_type = item.getValueType();
            switch(val_type){

                case FirebaseVisionBarcode.TYPE_ISBN:{

                    try {


                        String bookSearchString = "https://www.googleapis.com/books/v1/volumes?" +
                                "q=isbn:" + item.getRawValue() + "&key=" + browserKey;
                        System.out.println(bookSearchString);
                        RetrieveJSONTask bob = new RetrieveJSONTask(bookSearchString);

                        String jsonReqText = bob.execute().get();

                        System.out.println(jsonReqText);
                        Gson gson = new Gson();
                        OuterURL temp = gson.fromJson(jsonReqText, OuterURL.class);
                        jsonString = gson.toJson(temp);
                        imageURLPassed = temp.items.get(0).volumeInfo.imageLinks.thumbnail;
                        bookDescriptionPassed = temp.items.get(0).volumeInfo.description;
                        ratingStar = temp.getItems().get(0).getVolumeInfo().averageRating;
                        numRatings = temp.getItems().get(0).getVolumeInfo().ratingsCount;
                        Log.i("author", temp.items.get(0)
                                .volumeInfo.authors.toString());
                       String authors= temp.items.get(0).volumeInfo.authors
                                .toString().replace("[","").replace("]","");

                         builder = new androidx.appcompat.app.AlertDialog.Builder(cameraCapture.this);
                        builder.setMessage("ISBN: " + item.getRawValue() + "\nFORMAT: " + getType(item.getFormat()) + "\nTITLE: " + temp.items.get(0)
                                .volumeInfo.title +"\nAUTHOR(S): " +authors);
                        builder.setNegativeButton("Info", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent viewBook = new Intent(getApplicationContext(),BookViewActivity.class);
                                viewBook.putExtra("imageURLPassed",imageURLPassed);
                                viewBook.putExtra("bookDescriptionPassed",bookDescriptionPassed);
                                viewBook.putExtra("ratingStars", ratingStar);
                                viewBook.putExtra("numberRatings",numRatings);
                                viewBook.putExtra("jsonString",jsonString);
                                startActivity(viewBook);
                            }
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).setNeutralButton("Add Book", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent replyIntent = new Intent();
                                if(TextUtils.isEmpty(jsonString)){
                                    setResult(RESULT_CANCELED,replyIntent);
                                }else{
                                    replyIntent.putExtra(EXTRA_REPLY,jsonString);
                                    setResult(RESULT_OK,replyIntent);
                                    Toast.makeText(getApplicationContext(),"Entry Success",Toast.LENGTH_LONG).show();
                                }
                                finish();
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

   static class RetrieveJSONTask extends AsyncTask<String, Void, String> {
        String urlString;

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


    @Override
    protected void onResume(){
        super.onResume();
        cameraView.start();
    }
    @Override
    protected void onPause(){
        super.onPause();
        cameraView.stop();
        waitingDialog.dismiss();

    }
    private String getType(int n){
        switch(n){
            case 32: {
                return "EAN_13";
            }


        }
        return "UNKNOWN";
    }




}
