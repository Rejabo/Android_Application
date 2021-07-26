package com.example.MainActivites;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import SearchActivity.Search;

import static maes.tech.intentanim.CustomIntent.customType;

// Profile activity

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText descriptionEdition;
    private Button submitEdition;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private static final int REQUEST_PERMISSION = 0;


    public TextView profileDesc;
    public String profileDescContent;

    Firebase reference1;
    Button btnbrowse, btnupload;
    ImageView imgview;
    Uri FilePathUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;
    FirebaseDatabase db;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customType(MainActivity.this,"right-to-left");


        FirebaseDatabase db = FirebaseDatabase.getInstance();

        DatabaseReference myRef = db.getReference("users").child(UserDetails.username).child("description");

        profileDesc = findViewById(R.id.profileDescription);
        profileDescContent = profileDesc.getText().toString();
        profileDescContent =  db.getReference("users").child(UserDetails.username).child("description").toString();


        Firebase.setAndroidContext(this);
        reference1 = new Firebase(profileDescContent);


        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String descp = dataSnapshot.getValue(String.class);
                profileDesc.setText(descp);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) { }
        });


       Firebase.setAndroidContext(this);
       storageReference = FirebaseStorage.getInstance().getReference("Images");
       databaseReference = db.getReference("users");
       btnbrowse = (Button)findViewById(R.id.imageButton);
       btnupload= (Button)findViewById(R.id.button_upload);
       imgview = (ImageView)findViewById(R.id.imageView);
       progressDialog = new ProgressDialog(MainActivity.this);// context name as per your project name


        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);

            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UploadImage();
            }
        });

        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("users").child(UserDetails.chatWith).child("image");
    }


    @Override
    protected void onStart() {
        super.onStart();
            if (UserDetails.uri != null) {
                System.out.println("in onStart" + UserDetails.uri);
                Glide.with(getApplicationContext()).load(UserDetails.uri).into(imgview);
    }
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();
            UserDetails.uri = FilePathUri;

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), UserDetails.uri);
                imgview.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;
    }


    public void UploadImage() {

        if (FilePathUri != null) {

            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //String TempImageName = txtdata.getText().toString().trim();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            UploadInfo imageUploadInfo = new UploadInfo(taskSnapshot.getUploadSessionUri().toString());
                            //String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child(UserDetails.username).child("image").setValue(imageUploadInfo);
                        }
                    });
        } else {
            Toast.makeText(MainActivity.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item2:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, Login.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.chatButton) {
            Intent i = new Intent(this, UserList.class);
            startActivity(i);
        }
        if (v.getId() == R.id.profileButton) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        if (v.getId() == R.id.searchButton) {
            Intent i = new Intent(this, Search.class);
            startActivity(i);
        }

        if (v.getId() == R.id.editDescriptionButton) {
            editDescriptionDialog();
        }
    }



    public void editDescriptionDialog() { //for creating new dialog
        dialogBuilder = new AlertDialog.Builder(this);
        final View editDescriptionPopupView = getLayoutInflater().inflate(R.layout.editdescriptionpopup, null);
        descriptionEdition = editDescriptionPopupView.findViewById(R.id.editDescriptionText);

        submitEdition = (Button) editDescriptionPopupView.findViewById(R.id.submitEditButton);

        dialogBuilder.setView(editDescriptionPopupView);
        dialog = dialogBuilder.create();
        dialog.show();


        submitEdition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String edit_text_string = descriptionEdition.getText().toString();


                FirebaseDatabase db = FirebaseDatabase.getInstance();

                DatabaseReference myRef = db.getReference("users").child(UserDetails.username).child("description");

                myRef.setValue((edit_text_string));
                
                profileDesc.setText(edit_text_string);

                dialog.dismiss();
            }
        });
    }
}