package com.rrr.swift.LocationActivities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rrr.swift.MainActivity.UserHomeActivity;
import com.rrr.swift.R;

import java.io.File;
import java.io.IOException;

public class AddLocation extends AppCompatActivity
{

    private static final String TAG = "AddLocation";

    static final int CAMERA_PERM_CODE = 101;
    static final int CAMERA_REQUEST_CODE = 102;
    static final int GALLERY_REQUEST_CODE = 105;


    private Button mSubmitBtn;
    private Button mGalleryBtn;
    private EditText mNewLocation;
    private ImageView mImagePreview;
    String currentPhotoPath;
    StorageReference storageReference;


    File fileTemp = null;
    Uri uriTemp = null;
    String imageFileNameTemp = null;


    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;

    int maxID = 0;
    int choice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        Log.d(TAG,"onCreate: started");

        mSubmitBtn = (Button) findViewById(R.id.add_location_btn);
        mNewLocation = (EditText) findViewById(R.id.et_address);
        mImagePreview = (ImageView) findViewById(R.id.iv_preview);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("Locations");
        storageReference = FirebaseStorage.getInstance().getReference();


        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {
                    //User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Signed-in with: " + user.getEmail());
                } else
                    {
                    //User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Signed-out. Must sign-in.");
                    }
            }
        };

        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    maxID = (int) dataSnapshot.getChildrenCount();
                }

                Object value = dataSnapshot.getValue();
                Log.d(TAG, "Value is:" + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.w(TAG, "Failed to read value", databaseError.toException());
            }
        });

            mSubmitBtn.setOnClickListener(new View.OnClickListener()
            {
            @Override
            public void onClick(View view)
            {
            Log.d(TAG, "onClick: Attempting to add object to database.");

            String newLocation = mNewLocation.getText().toString().trim();

            if(!newLocation.equals("") && uriTemp != null)
         {
                FirebaseUser user = mAuth.getCurrentUser();
//                String userID = user.getUid();  //causes crash if User Login Screen is bypassed

//                    Log.d(TAG,"User ID: "+userID);

                Log.d(TAG,"maxID before increment is: " + maxID);
                myRef.child(String.valueOf(maxID+1)).child("address").setValue(newLocation);
                myRef.child(String.valueOf(maxID+1)).child("addressImage").setValue(uriTemp.toString().trim());

                toastMessage("Adding " + newLocation + " to database...");

                //reset the text
                mNewLocation.setText("");
                maxID = maxID + 1;
                Log.d(TAG,"maxID after increment is: " + maxID);

                    Intent myIntent = new Intent(AddLocation.this, UserHomeActivity.class);
                    AddLocation.this.startActivity(myIntent);

             if(imageFileNameTemp == null)
             {
                 choice = 1;
             }
             else if(imageFileNameTemp != null)
             {
                 choice = 2;
             }

             switch (choice)
                {
                    case 1:
                        Log.d(TAG,"Choice: "+choice);
                        uploadImageToFirebase(fileTemp.getName(), uriTemp);
                        break;

                    case 2:
                        Log.d(TAG,"Choice: "+choice);   //Camera Picture
                        uploadImageToFirebase(imageFileNameTemp, uriTemp);
                        break;

                    default:
                        Log.d(TAG,"Choice: "+choice);
                        break;
                }
             

         }
                else
                    {
                    toastMessage("Must have location with picture to submit ");
                    }
            }
            });


            mImagePreview.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    toastMessage("Camera Button Clicked");
                    askCameraPermissions();
                }
            });

    }


    private void askCameraPermissions()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }else
            {
            dispatchTakePictureIntent();
            }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(requestCode == CAMERA_PERM_CODE)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                dispatchTakePictureIntent();
            }else
                {
                Toast.makeText(this, "Camera Permission is Required to Use camera.", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            File f = new File(currentPhotoPath);
            mImagePreview.setImageURI(Uri.fromFile(f));
            Log.d("TAG", "Absolute Url of Image is " + Uri.fromFile(f));

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);

            fileTemp = new File(f.getName());
            uriTemp = contentUri;
        }
        else if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            Uri contentUri = data.getData();
//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//            String imageFileName = "JPEG_" + timeStamp + "."+getFileExt(contentUri);
            String newLocation3 = mNewLocation.getText().toString().trim();
            String imageFileName = newLocation3;

            Log.d(TAG, "onActivityResult: Gallery Image Uri: " + imageFileName);
            mImagePreview.setImageURI(contentUri);

            imageFileNameTemp = imageFileName;
            uriTemp = contentUri;

        }
    }

    private void uploadImageToFirebase(String name, Uri contentUri)
    {
        final StorageReference image = storageReference.child(name);
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
        {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                {
                    @Override
                    public void onSuccess(Uri uri)
                    {
                        Log.d("tag", "onSuccess: Uploaded Image URl is " + uri.toString());
                    }
                });

                Toast.makeText(AddLocation.this, "Image Is Uploaded.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(AddLocation.this, "Upload Failled.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private File createImageFile() throws IOException
    {
        String locationName = mNewLocation.getText().toString().trim();
        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
        String imageFileName = locationName;

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName,".jpg",storageDir/* directory */);

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        imageFileNameTemp = imageFileName;
        return image;
    }

    private void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            // Create the File where the photo should go
            File photoFile = null;
            try
            {
                photoFile = createImageFile();
            } catch (IOException ex)
            {
                // Error occurred while creating the File
            toastMessage("Error creating the file");
            Log.e(TAG,"Error Creating File", ex);

            }
            // Continue only if the File was successfully created
            if (photoFile != null)
            {
                Uri photoURI = FileProvider.getUriForFile(AddLocation.this,
                        "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

//    private String getFileExt(Uri contentUri)
//    {
//        ContentResolver c = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(c.getType(contentUri));
//    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void openAddEditLocationActivity(View view)
    {
        Intent myIntent = new Intent(AddLocation.this, AddEditLocationActivity.class);
        AddLocation.this.startActivity(myIntent);
    }

    private void toastMessage(String s)
    {
        Toast.makeText(this, s,Toast.LENGTH_SHORT).show();
    }

}
