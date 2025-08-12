//package com.example.project1.Activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.project1.R;
//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.GoogleAuthProvider;
//
//import org.jetbrains.annotations.Nullable;
//
//
//public class LoginActivity extends AppCompatActivity {
//
//    public void skip (View view) {
//        startActivity(new Intent(getApplicationContext(),  MainActivity.class));
//        finish();
//    }
//    public void signup (View view) {
//        startActivity(new Intent(getApplicationContext(),  RegisterActivity.class));
//    }
//    ///  //  for using email
//    private EditText edEmail, edPassword;
//    private Button btnLogin;
//    private TextView tvForgotPassword;
//    private FirebaseAuth auth;
//
//    /// // for using google
//    ImageView google_sign_in_btn;
//    //    FirebaseAuth firebaseAuth;
//    GoogleSignInClient gsc;
//    int RC_SIGN_IN = 100;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_login);
//
//
//        google_sign_in_btn = findViewById(R.id.btnGoogle);
//        edEmail = findViewById(R.id.loginemail);
//        edPassword = findViewById(R.id.loginPassword);
//        btnLogin = findViewById(R.id.btnLogin);
//        tvForgotPassword = findViewById(R.id.tvForgotPassword);
//        auth = FirebaseAuth.getInstance();
//
//
//        ///   // for using email
//        //   Forgot Password click event
//        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Go to ForgotPasswordActivity
//                Intent intent = new Intent(LoginActivity.this, changepassActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        //  Login button click
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = edEmail.getText().toString().trim();
//                String password = edPassword.getText().toString().trim();
//
//                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
//                    Toast.makeText(LoginActivity.this, "Enter email and password both", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                loginUser(email, password);
//            }
//        });
//
//
//        /// //           for using google
//
//        //  If already signed in, go to HomeActivity directly
//        if (auth.getCurrentUser() != null) {
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
//            return;
//        }
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id)) // get from google-services.json
//                .requestEmail()
//                .build();
//
//        gsc = GoogleSignIn.getClient(this, gso);
//
//        google_sign_in_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signIn();
//            }
//        });
//    }
//
//    private void signIn() {
//        Intent signInIntent = gsc.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account.getIdToken());
//            } catch (ApiException e) {
//                Toast.makeText(this, "Google sign in failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//    private void firebaseAuthWithGoogle(String idToken) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
//        auth.signInWithCredential(credential)
//                .addOnCompleteListener(this, task -> {
//                    if (task.isSuccessful()) {
//                        FirebaseUser user = auth.getCurrentUser();
//                        Toast.makeText(LoginActivity.this, "Signed in as: " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                        finish();
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//
//    ///   //  for using email
//    private void loginUser(String email, String password) {
//        auth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(),  MainActivity.class));
//                            finish();
//                            // TODO: Navigate to another screen if needed
//                        } else {
//                            String error = task.getException() != null ? task.getException().getMessage() : "Login failed";
//                            Toast.makeText(LoginActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//    }
//
//
//}


package com.example.project1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project1.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.jetbrains.annotations.Nullable;


public class LoginActivity extends AppCompatActivity {

    public void skip (View view) {
        startActivity(new Intent(getApplicationContext(),  MainActivity.class));
        finish();
    }
    public void signup (View view) {
        startActivity(new Intent(getApplicationContext(),  RegisterActivity.class));
    }
    ///  //  for using email
    private EditText edEmail, edPassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private FirebaseAuth auth;

    /// // for using google
    ImageView google_sign_in_btn;
    //    FirebaseAuth firebaseAuth;
    GoogleSignInClient gsc;
    int RC_SIGN_IN = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        google_sign_in_btn = findViewById(R.id.btnGoogle);
        edEmail = findViewById(R.id.loginemail);
        edPassword = findViewById(R.id.loginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        auth = FirebaseAuth.getInstance();


        ///   // for using email
        //   Forgot Password click event
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to ForgotPasswordActivity
                Intent intent = new Intent(LoginActivity.this, changepassActivity.class);
                startActivity(intent);
            }
        });

        //  Login button click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Enter email and password both", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginUser(email, password);
            }
        });


        /// //           for using google

        //  If already signed in, go to HomeActivity directly
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // get from google-services.json
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(this, gso);

        google_sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, "Google sign in failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        Toast.makeText(LoginActivity.this, "Signed in as: " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    ///   //  for using email
    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),  MainActivity.class));
                            finish();
                            // TODO: Navigate to another screen if needed
                        } else {
                            String error = task.getException() != null ? task.getException().getMessage() : "Login failed";
                            Toast.makeText(LoginActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}