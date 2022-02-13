package com.example.bai1training.loginandregist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.bai1training.R;
import com.example.bai1training.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {
    private GoogleSignInClient googleSignInClient;

    private FirebaseAuth firebaseAuth;

    private ActivityLoginBinding binding;

    private  FirebaseUser firebaseUser;

    private static final String GOOGLE_SIGN_IN_TAG = "GOOGLE_SIGN_IN_TAG";

    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        //configure for Google Sign in
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        //init firebase auth

        firebaseAuth = FirebaseAuth.getInstance();

        //Google SignInButton : Click to begin Google SignIn

        binding.btnLoginGoogle.setOnClickListener(v -> {
            //begin google sign in
            Log.d(GOOGLE_SIGN_IN_TAG, "on click : begin Google Sign in");
            Intent intent = googleSignInClient.getSignInIntent();
            checkLogOut();
            startActivityForResult(intent, RC_SIGN_IN); // handler this result
        });

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Log.d(GOOGLE_SIGN_IN_TAG, "on activity for result  : Google Sign in result intent");
            Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //google sign in success, now auth with firebase

                GoogleSignInAccount account = accountTask.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.d(GOOGLE_SIGN_IN_TAG, "on activity for result  : " + e.getMessage());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(GOOGLE_SIGN_IN_TAG, "fire base Auth With Google  :begin firebase auth with google");

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(GOOGLE_SIGN_IN_TAG, "onSuccess  :Logged in");

                        //get logged user
                        firebaseUser = firebaseAuth.getCurrentUser();
                        String uid = firebaseUser.getUid();

                        //check if user is new or existing

                        if (authResult.getAdditionalUserInfo().isNewUser()) {

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    private void checkLogOut() {
        if (firebaseUser !=null) {
            firebaseAuth.signOut();
        }
    }
}
