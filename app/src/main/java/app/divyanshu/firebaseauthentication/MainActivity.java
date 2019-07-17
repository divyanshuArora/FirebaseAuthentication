package app.divyanshu.firebaseauthentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText email,pass,confPass;
    Button register;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    TextView loginIntetn;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();


        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        confPass = findViewById(R.id.conf_pass);
        register = findViewById(R.id.register);


        loginIntetn = findViewById(R.id.loginIntent);
        loginIntetn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginAct = new Intent(getApplicationContext(),Login.class);
                startActivity(loginAct);
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerValidation();
            }
        });




    }

    private void registerValidation() {

        String name1, email1, number1, pass1, confPass1;
        email1 = email.getText().toString();
        pass1 = pass.getText().toString();
        confPass1 = confPass.getText().toString();

        if (email1.isEmpty()) {
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
        }

    final ProgressDialog pg = new ProgressDialog(MainActivity.this);
        pg.setTitle("Registring");
        pg.setMessage("Loading");
        pg.setCancelable(false);
        pg.show();
        firebaseAuth.createUserWithEmailAndPassword(email1, pass1).
                addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {



                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Registration UnSuccessful", Toast.LENGTH_SHORT).show();
                            pg.dismiss();
                        } else
                            {
                            Intent x = new Intent(getApplicationContext(),Login.class);
                            startActivity(x);
                            Toast.makeText(MainActivity.this, "registration Successfull", Toast.LENGTH_SHORT).show();
                            pg.dismiss();
                        }


                    }
                });


    }
}

