package com.amadurell.projectesocialuf2m08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.amadurell.projectesocialuf2m08.databinding.FragmentRegisterBinding;
import com.google.firebase.auth.GoogleAuthProvider;


public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;


    NavController navController;   // <-----------------

    private EditText emailEditText, passwordEditText;

    private Button registerButton;
    private FirebaseAuth mAuth;

    private LinearLayout signInForm;
    private ProgressBar signInProgressBar;

    private SignInButton googleSignInButton;
    private ActivityResultLauncher<Intent> activityResultLauncher;




    public RegisterFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentRegisterBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.equis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_registerFragment_to_signInFragment);
            }

        });

        binding.atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_registerFragment_to_usuaris);
            }

        });

        navController = Navigation.findNavController(view); // <-----------------
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);

        signInForm = view.findViewById(R.id.signInForm);
        signInProgressBar = view.findViewById(R.id.signInProgressBar);

        registerButton = view.findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearCuenta();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        ////7. SignIn con Google
        googleSignInButton = view.findViewById(R.id.googleSignInButton);

        //7. SignIn con Google
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accederConGoogle();
            }
        });

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            try {
                                firebaseAuthWithGoogle(GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException.class));
                            } catch (ApiException e) {
                                Log.e("ABCD", "signInResult:failed code=" +
                                        e.getStatusCode());
                            }
                        }
                    }
                });
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accederConGoogle();
            }
        });
    }

    private void crearCuenta() {
        if (!validarFormulario()) {
            return;
        }

        registerButton.setEnabled(false);

        mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            actualizarUI(mAuth.getCurrentUser());
                        } else {
                            Snackbar.make(requireView(), "Error: " + task.getException(), Snackbar.LENGTH_LONG).show();

                        }
                        registerButton.setEnabled(true);
                    }
                });

    }

    private void actualizarUI(FirebaseUser currentUser) {
        if(currentUser != null){
            navController.navigate(R.id.homeFragment);
        }
    }

    //7. SignIn con Google
    private void accederConGoogle() {
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(requireActivity(), new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build());

        activityResultLauncher.launch(googleSignInClient.getSignInIntent());
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        if(acct == null) return;
        signInProgressBar.setVisibility(View.VISIBLE);
        signInForm.setVisibility(View.GONE);
        mAuth.signInWithCredential(GoogleAuthProvider.getCredential(acct.getIdToken(
        ), null))
                .addOnCompleteListener(requireActivity(), new
                        OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.e("ABCD", "signInWithCredential:success");
                                    actualizarUI(mAuth.getCurrentUser());
                                } else {
                                    Log.e("ABCD", "signInWithCredential:failure",
                                            task.getException());
                                    signInProgressBar.setVisibility(View.GONE);
                                    signInForm.setVisibility(View.VISIBLE);
                                }
                            }
                        });
    }

    private boolean validarFormulario() {
        boolean valid = true;

        if (TextUtils.isEmpty(emailEditText.getText().toString())) {
            emailEditText.setError("Required.");
            valid = false;
        } else {
            emailEditText.setError(null);
        }

        if (TextUtils.isEmpty(passwordEditText.getText().toString())) {
            passwordEditText.setError("Required.");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        return valid;
    }

}