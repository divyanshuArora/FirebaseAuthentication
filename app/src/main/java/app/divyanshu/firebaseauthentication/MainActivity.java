package app.divyanshu.firebaseauthentication;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity {

    EditText email, pass, confPass, name, number;
    RadioButton radioGender;
    Button register;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    RadioGroup radioGroup;
    TextView loginIntetn;
    CircleImageView selectImage;
    private FirebaseAuth firebaseAuth;
    int onStartCount = 0;
    int  PICK_IMAGE_REQUEST = 71;
    public int  MY_REQUEST_CAMERA = 1888;
    private Uri filePath;
    String imageSuccess;
    DatabaseReference databaseReference;

     ProgressDialog pg;

           FirebaseStorage storage;
        StorageReference storageRef;
        StorageReference mountainImagesRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        pg= new ProgressDialog(MainActivity.this);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mountainImagesRef = storageRef.child("images/");


        selectImage = findViewById(R.id.select_image);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        confPass = findViewById(R.id.conf_pass);
        register = findViewById(R.id.register);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        radioGroup = findViewById(R.id.radioGrp);
        loginIntetn = findViewById(R.id.loginIntent);
        loginIntetn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent loginAct = new Intent(getApplicationContext(), Login.class);

                startActivity(loginAct);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerValidation();
            }
        });


        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_REQUEST_CAMERA);

                    } else {

                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/");
                        startActivityForResult(intent, 0);


                    }
                }
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            if (requestCode == MY_REQUEST_CAMERA)
            {
                    if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
//                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(camera,MY_REQUEST_CAMERA);


                        Intent intent  = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/");
                        startActivityForResult(intent,0);
                    } }
    }

    Uri Picuri=null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == Activity.RESULT_OK)
        {
            Picuri = data.getData();
            try {
                Bitmap bitmap =  MediaStore.Images.Media.getBitmap(this.getContentResolver(), Picuri);

                selectImage.setImageBitmap(bitmap);
                //                uploadImageToFirebase();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

     String name1;
     String email1;
     String number1;
     String pass1;
     String confPass1;
     String gender;

    private void registerValidation() {


        name1 = name.getText().toString();
        number1 = number.getText().toString();
        email1 = email.getText().toString();
        pass1 = pass.getText().toString();
        confPass1 = confPass.getText().toString();


        final int[] radioId = {radioGroup.getCheckedRadioButtonId()};
        radioGender = (RadioButton) findViewById(radioId[0]);

         gender = radioGender.getText().toString();

        if (Picuri == null)
        {
            Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (name1.isEmpty()) {
            name.setError("enter your password");
            return;
        } else if (email1.isEmpty()) {
            email.setError("enter your email");
            return;
        } else if (pass1.isEmpty()) {
            pass.setError("enter your password");
            return;
        } else if (pass1.length() < 6) {
            pass.setError("enter atleast 6 digits");
            return;
        } else if (confPass1.isEmpty()) {
            confPass.setError("enter confirm password");
            return;
        } else if (!confPass1.equals(pass1)) {
            confPass.setError("password not matched");
            return;
        } else if (!email1.matches(emailPattern)) {
            email.setError("enter correct email format");
            return;
        } else if (number1.isEmpty()) {
            number.setError("enter your password");
            return;
        } else if (pass1.isEmpty()) {
            pass.setError("enter your password");
            return;
        } else if (gender.isEmpty()) {
            Toast.makeText(this, "Select Your Gender", Toast.LENGTH_SHORT).show();
            return;
        }



        pg.setTitle("Registring");
        pg.setMessage("Loading");
        pg.setCancelable(false);
        pg.show();
        firebaseAuth.createUserWithEmailAndPassword(email1, pass1).
                addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(final Task<AuthResult> task) {


                        if (!task.isSuccessful()) {
                            Log.e("Main", "onComplete: "+"Error" );


                            Toast.makeText(MainActivity.this, "Registration UnSuccessful", Toast.LENGTH_SHORT).show();

                        } else
                            {
                                Log.e("Main", "onCompleteSuccess: " );
                                uploadImageToFirebase();
                            }
                    }
                }); }



                private void uploadImageToFirebase()
                {

                    String uid = firebaseAuth.getCurrentUser().getUid();

                    StorageReference storageRef = storage.getReference();
                    final StorageReference mountainImagesRef = storageRef.child("images/"+uid);

                    Bitmap bitmap = ((BitmapDrawable) selectImage.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    UploadTask uploadTask = mountainImagesRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String filePath= uri.toString();

                                    Log.e("FilePath",filePath);


                                    Intent x = new Intent(getApplicationContext(), Login.class);
                                    startActivity(x);
                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                    String user_id = firebaseUser.getUid();

                                    updateTheDatabase(name1, email1, number1, pass1, gender, filePath,user_id);
                                    Toast.makeText(MainActivity.this, "registration Successfull", Toast.LENGTH_SHORT).show();
                                    pg.dismiss();
                                }

                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("Main", "onFailure: " +e);
                                    pg.dismiss();
                                }
                            }); }
                    });
                }



    private void updateTheDatabase(final String registeredName, final String registeredEmail, final String registeredNumber, final String registeredPassword, final String radioGender,final String storedImageName,String user_id
    ) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/users").child(registeredNumber);
        UserModel model = new UserModel(registeredEmail, registeredName, registeredNumber, registeredPassword, radioGender,storedImageName,user_id);
        databaseReference.setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                Intent x = new Intent(MainActivity.this, Login.class);
//                x.putExtra("name", registeredName);
//                x.putExtra("email", registeredEmail);
//                x.putExtra("number", registeredNumber);
//                x.putExtra("password", registeredPassword);
//                x.putExtra("gender", radioGender);
//                x.putExtra("StoredImageName",storedImageName);
//                //     x.putExtra("email",user_id);

                startActivity(x);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Register", "onFailure: "+"not registered" );
            }
        });


    }


}
