package app.divyanshu.firebaseauthentication;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class Login extends AppCompatActivity {
    TextView registerIntent,forgot;
    EditText email,pass;
    Button login;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth auth;
    SessionManager sessionManager;
    ProgressDialog pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        overridePendingTransition(R.transition.left_to_right, R.transition.right_to_left);
        auth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(Login.this);

        pg= new ProgressDialog(Login.this);
      checkSession();




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

    private void checkSession()
    {
        if (!sessionManager.getString("user_id").isEmpty())
        {

            Intent a = new Intent(getApplicationContext(),Dashboard.class);
            startActivity(a);
            finish();
        }
    }

    private void forgotMethod(String emailForgot)
    {
        pg.setTitle("Registring");
        pg.setMessage("Loading");
        pg.setCancelable(false);
        pg.show();

        auth.sendPasswordResetEmail(emailForgot).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task)
            {
             if (!task.isSuccessful())
             {
                 Toast.makeText(Login.this, "Email Is Not Registered", Toast.LENGTH_SHORT).show();

             }
             else
             {
                 Toast.makeText(Login.this, "Reset Link Sent On Your Email", Toast.LENGTH_SHORT).show();

             }
            }
        });
    }

    private void LoginMethod()
    {
        String  email1,  pass1;
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
        pg.setTitle("Logging In");
        pg.setMessage("Loading");
        pg.setCancelable(false);
        pg.show();

        auth.signInWithEmailAndPassword(email1,pass1).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
               if (!task.isSuccessful())
               {
                   Toast.makeText(Login.this, "Wrong Credantial", Toast.LENGTH_SHORT).show();
                   pg.dismiss();
               }
               else
               {
                   FirebaseUser user =  auth.getCurrentUser();
                   String registeredEmail = user.getEmail();
                   String registeredUserId = user.getUid();
                   Toast.makeText(Login.this, "LoggedIn", Toast.LENGTH_SHORT).show();

                   Intent x = new Intent(Login.this,Dashboard.class);
                   x.putExtra("email",registeredEmail);
                   x.putExtra("uId",registeredUserId);

                   sessionManager.addString("user_id",registeredUserId);
                   sessionManager.addString("user_email",registeredEmail);
                   startActivity(x);
                   pg.dismiss();
                   finish();
               }
                }
            });
    }
}

