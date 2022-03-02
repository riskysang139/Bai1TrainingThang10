package com.example.moviefilm.film.user.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.moviefilm.R;
import com.example.moviefilm.databinding.ActivityLoginBinding;
import com.example.moviefilm.film.user.login.model.User;
import com.example.moviefilm.film.user.login.viewmodels.LoginViewModels;
import com.example.moviefilm.film.view.MainActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private GoogleSignInClient googleSignInClient;

    private FirebaseAuth firebaseAuth;

    private ActivityLoginBinding binding;

    private FirebaseUser firebaseUser;

    private static final String GOOGLE_SIGN_IN_TAG = "GOOGLE_SIGN_IN_TAG";

    private static final int RC_SIGN_IN = 100;

    private FirebaseAuth.AuthStateListener authStateListener;

    private CallbackManager callbackManager;

    private AccessTokenTracker accessTokenTracker;

    private LoginViewModels loginViewModels;

    public static final String KEY_FROM = "KEY_FROM";

    public static final String FROM_LOGIN = "FROM_LOGIN";

    public static final String FROM_REGISTER = "FROM_REGISTER";

    private String fromScreen = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        getData();
        observedLogin();

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
            startActivityForResult(intent, RC_SIGN_IN); // handler this result
        });

        //Login Facebook Button : Click to begin SignIn

        binding.btnLoginFace.setOnClickListener(this);
        binding.viewBtnLoginFace.setOnClickListener(this);

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        checkLogOut();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fromScreen = bundle.getString(KEY_FROM, "");
            if (fromScreen.equals(FROM_LOGIN))
                setUpViewLogin();
            else
                setUpViewRegister();
        }
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

        // Pass the activity result back to the Facebook SDK
        if (callbackManager != null)
            callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(GOOGLE_SIGN_IN_TAG, "fire base Auth With Google  :begin firebase auth with google");

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        onSuccessLogin(authResult);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void firebaseWithEmailAndPass() {
        firebaseAuth.signInWithEmailAndPassword(binding.txtEmail.getText().toString().trim(), binding.txtPassword.getText().toString().trim())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        onSuccessLogin(authResult);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void firebaseWithEmailAndPassSignUp() {
        firebaseAuth.createUserWithEmailAndPassword(binding.txtEmail.getText().toString().trim(), binding.txtPassword.getText().toString().trim())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        onSuccessLogin(authResult);
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(), "Register Failed " + e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void firebaseWithFacebook() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("tag", "login success");
                handlerFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("tag", "login cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("tag", "login error");
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null)
                    firebaseAuth.signOut();
            }
        };
    }

    private void handlerFacebookToken(AccessToken accessToken) {
        Log.d("tag", "handlerFacebookToken " + accessToken);
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("tag", "sign in with credential : success");
                    firebaseUser = firebaseAuth.getCurrentUser();
                    //start main activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                    Toast.makeText(getBaseContext(), "Welcome \n" + (firebaseUser.getDisplayName() == null ? "" : firebaseUser.getDisplayName()), Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("tag", "sign in with credential : failed");
                }
            }
        });
    }

    private void onSuccessLogin(AuthResult authResult) {
        Log.d(GOOGLE_SIGN_IN_TAG, "onSuccess  :Logged in");

        //get logged user
        firebaseUser = firebaseAuth.getCurrentUser();
        String uid = firebaseUser.getUid();
        String email = firebaseUser.getEmail();
        //check if user is new or existing

        if (authResult.getAdditionalUserInfo().isNewUser()) {
            // user is new - Account Created
            Toast.makeText(getBaseContext(), "Welcome \n" + email, Toast.LENGTH_SHORT).show();
        } else {
            // user is existing - Logging in
            Toast.makeText(getBaseContext(), "Welcome back \n" + email, Toast.LENGTH_SHORT).show();
        }

        //start main activity
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    private void checkLogOut() {
        if (firebaseUser != null) {
            firebaseAuth.signOut();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_face:
            case R.id.view_btn_login_face:
                firebaseWithFacebook();
                break;
        }
    }

    private void observedLogin() {
        binding.setLifecycleOwner(this);
        loginViewModels = ViewModelProviders.of(LoginActivity.this).get(LoginViewModels.class);
        binding.setLoginViewModel(loginViewModels);

        loginViewModels.getUserMutableLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

                if (TextUtils.isEmpty(Objects.requireNonNull(user).getEmail())) {
                    binding.txtEmail.setError("Enter an E-Mail Address");
                    binding.txtEmail.requestFocus();
                } else if (!user.isEmailValid()) {
                    binding.txtEmail.setError("Enter a Valid E-mail Address");
                    binding.txtEmail.requestFocus();
                } else if (TextUtils.isEmpty(Objects.requireNonNull(user).getPassWord())) {
                    binding.txtPassword.setError("Enter a Password");
                    binding.txtPassword.requestFocus();
                } else if (!user.isPasswordLengthGreaterThan5()) {
                    binding.txtPassword.setError("Enter at least 6 Digit password");
                    binding.txtPassword.requestFocus();
                } else if (fromScreen.equals(FROM_REGISTER) && !user.isPasswordAndConfirm()) {
                    binding.txtPassword.setError("Your password and password confirm not match");
                    binding.txtPassword.requestFocus();
                } else {
                    binding.txtEmail.setText(user.getEmail());
                    binding.txtPassword.setText(user.getPassWord());
                    if (fromScreen.equals(FROM_REGISTER)) {
                        binding.txtPasswordConfirm.setText(user.getPassWordConfirm());
                        //Register Button : Click to begin Email and Password SignUp
                        binding.btnLogin.setOnClickListener(view -> {
                            firebaseWithEmailAndPassSignUp();
                        });
                    } else {
                        //Login Button : Click to begin Email and Password SignIn
                        binding.btnLogin.setOnClickListener(view -> {
                            firebaseWithEmailAndPass();
                        });
                    }

                }
            }
        });
    }

    private void setUpViewRegister() {
        binding.btnLogin.setText("Register");
        binding.lnSignUp.setVisibility(View.GONE);
        binding.layoutTxtPassConfirm.setVisibility(View.VISIBLE);
        binding.txtQuenMatKhau.setVisibility(View.GONE);
    }

    private void setUpViewLogin() {
        binding.btnLogin.setText("Login");
        binding.lnSignUp.setVisibility(View.VISIBLE);
        binding.layoutTxtPassConfirm.setVisibility(View.GONE);
        binding.txtQuenMatKhau.setVisibility(View.VISIBLE);
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpViewRegister();
            }
        });
    }
}
