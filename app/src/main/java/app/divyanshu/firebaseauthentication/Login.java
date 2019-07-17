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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {
    TextView registerIntent,forgot;
    EditText email,pass;
    Button login;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailLogin);
        pass = findViewById(R.id.passLogin);
        login = findViewById(R.id.login);
        forgot = findViewById(R.id.forgot);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email2  = email.getText().toString();

                if (email2.isEmpty())
                {
                    email.setError("enter your email");
                    return;
                }


                forgotMethod(email2);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginMethod();
            }
        });



        registerIntent = findViewById(R.id.registerIntent);
        registerIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(a);
            }
        });



    }

    private void forgotMethod(String emailForgot)
    {
        final ProgressDialog pg = new ProgressDialog(Login.this);
        pg.setTitle("Registring");
        pg.setMessage("Loading");
        pg.setCancelable(false);
        pg.show();

        auth.sendPasswordResetEmail(emailForgot).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
             if (!task.isSuccessful())
             {
                 Toast.makeText(Login.this, "Email Is Not Registered", Toast.LENGTH_SHORT).show();
                 pg.dismiss();
             }
             else
             {
                 Toast.makeText(Login.this, "Reset Link Sent On Your Email", Toast.LENGTH_SHORT).show();
                 pg.dismiss();
             }


            }
        });

    }

    private void LoginMethod()
    {

        String name1, email1, number1, pass1, confPass1;
        email1 = email.getText().toString();
        pass1 = pass.getText().toString();

        if (email1.isEmpty()) {
            email.setError("enter your email");
            return;
        } else if (pass1.isEmpty()) {
            pass.setError("enter your password");
            return;
        } else if (pass1.length() < 6) {
            pass.setError("enter atleast 6 digits");
            return;

        } else if (!email1.matches(emailPattern)) {
            email.setError("enter correct email format");
            return;
        }

        final ProgressDialog pg = new ProgressDialog(Login.this);
        pg.setTitle("Registring");
        pg.setMessage("Loading");
        pg.setCancelable(false);
        pg.show();


            auth.signInWithEmailAndPassword(email1,pass1).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
               if (!task.isSuccessful())
               {
                   Toast.makeText(Login.this, "Wrong Credantial", Toast.LENGTH_SHORT).show();
                   pg.dismiss();
               }
               else
               {
                   Toast.makeText(Login.this, "LoggedIn", Toast.LENGTH_SHORT).show();
                   pg.dismiss();
               }
                }
            });







    }
}
