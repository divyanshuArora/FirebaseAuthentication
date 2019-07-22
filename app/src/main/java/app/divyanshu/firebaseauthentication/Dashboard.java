package app.divyanshu.firebaseauthentication;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;


public class Dashboard extends AppCompatActivity {

    List<UserModel> userModelList = new ArrayList<>();
    UserDetailsAdapter userDetailsAdapter;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    TextView logout;

    RecyclerView recyclerView;

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users/");
        Log.d("Dash"," db ref :"+databaseReference );
        recyclerView = findViewById(R.id.recycle);
        sessionManager = new SessionManager(getApplicationContext());
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Dashboard.this,Login.class);
                sessionManager.clear();
                startActivity(i);
                finish();
            }
        });




        load_data();
    }

    private void load_data()
    {


    final ProgressDialog pg = new ProgressDialog(Dashboard.this);
        pg.setTitle("User List");
        pg.setMessage("Loading...");
        pg.show();

       databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               Log.d("Dash", "onDataChange: ");

               for (DataSnapshot dataSnapshot1 :  dataSnapshot.getChildren())
               {

                   String name1 = dataSnapshot1.child("name").getValue().toString();
                   String email1 = dataSnapshot1.child("email").getValue().toString();
                   String number1 = dataSnapshot1.child("number").getValue().toString();
                   String gender = dataSnapshot1.child("gender").getValue().toString();
                   String userImg  =dataSnapshot1.child("StoredImageName").getValue().toString();
                   String user_id  =dataSnapshot1.child("user_id").getValue().toString();
                   String user_password  =dataSnapshot1.child("password").getValue().toString();


                   Log.d("dash", "onDataChange: "+name1);
                   Log.d("dash", "onDataChange: "+email1);
                   Log.d("dash", "onDataChange: "+number1);
                   Log.d("dash", "onDataChange: "+gender);
                   Log.d("dash", "onDataChange: "+userImg);
                   Log.d("dash", "onDataChange: "+user_id);
                   Log.d("dash", "onDataChange: "+user_password);


                   userModelList.add(new UserModel(email1,name1,number1,user_password,gender,userImg,user_id));
                   LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                   linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                   DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
                   recyclerView.setLayoutManager(linearLayoutManager);
                   recyclerView.addItemDecoration(dividerItemDecoration);
                   userDetailsAdapter = new UserDetailsAdapter(getApplicationContext(),userModelList);
                   recyclerView.setAdapter(userDetailsAdapter);

                   pg.dismiss();



               }



           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
               Log.d("Dash", "onCancelled: ");
           }
       });




    }


}
