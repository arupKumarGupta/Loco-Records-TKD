package gupta.kumar.arup.tkdloco.logincreds;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import gupta.kumar.arup.tkdloco.R;
import gupta.kumar.arup.tkdloco.addToDb.NewEntry;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    ProgressBar pBar;
    EditText userEmail,userPassword;
    FirebaseAuth mAuth;
    Button loginButton;
    Toolbar toolbar;
    TextView tbTitle;
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    public static final String ISLOGGEDIN = "isLoggedIn";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        preferences = getSharedPreferences("LoginPref",MODE_PRIVATE);
        editor = preferences.edit();
        if(!preferences.contains(ISLOGGEDIN)){
            editor.putBoolean(ISLOGGEDIN,false);
            editor.commit();
        }

        //Log.d("Loggedin:",preferences.getBoolean(ISLOGGEDIN,false)+"");

    }



    private void init() {
        mAuth = FirebaseAuth.getInstance();
        Firebase.setAndroidContext(this);
        userEmail = findViewById(R.id.userEmailId);
        userPassword = findViewById(R.id.userPassword);
        pBar = findViewById(R.id.pBar);
        loginButton = findViewById(R.id.loginButton);
        toolbar = findViewById(R.id.toolbar);
        tbTitle = findViewById(R.id.tbTitle);
        setSupportActionBar(toolbar);
        tbTitle.setText("ADMIN PANEL");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }


    public void loginUser(View view) {
        
        String email,password;
        email = userEmail.getText().toString();
        password = userPassword.getText().toString();
        if(validateEmail(email) && validatePassword(password)) {
            proceedWithLogin(email, password);
            pBar.setVisibility(View.VISIBLE);
        }
        
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.newEntry:
                startActivity(new Intent(getApplicationContext(), NewEntry.class));
                finish();
                return true;
        }
        return false;
    }

    private void proceedWithLogin(String email, String password) {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //startActivity(new Intent(getApplicationContext(),));
                    //TODO for adding new Loco or new entry
                    startActivity(new Intent(getApplicationContext(), NewEntry.class));

                    editor.putBoolean(ISLOGGEDIN,true);
                    editor.commit();
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Invalid Creds", Toast.LENGTH_SHORT).show();
                }
                pBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private boolean validatePassword(String password) {
        if(password.length() < 4){
            userPassword.setError("Password is too short");
        return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {
        
        if(email.isEmpty()||email.charAt(email.length()-1)=='@'||email.charAt(email.length()-1)=='.'||email.indexOf('@')==-1 || email.indexOf('.')==-1 ||
                email.charAt(email.indexOf('@')+1)=='.' ||
            email.lastIndexOf('.') < email.lastIndexOf('@'))

        {
            userEmail.setError("Invalid Email");
            return false;
        }
        return true;
    }


}

