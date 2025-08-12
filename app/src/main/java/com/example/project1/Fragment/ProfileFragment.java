package com.example.project1.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project1.Activity.LoginActivity;
import com.example.project1.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

//
public class ProfileFragment extends Fragment {

    Button logoutBtn;
    FirebaseAuth firebaseAuth;
    GoogleSignInClient gsc;
    private EditText etName, etEmail, etNumber, etAbout;
    private Button btnLogout, btnSave;
    private DatabaseReference reference;


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logoutBtn = view.findViewById(R.id.BtnlogOut);
        firebaseAuth = FirebaseAuth.getInstance();
        etName = view.findViewById(R.id.etProfileName);
        etEmail = view.findViewById(R.id.etProfileEmail);
        etNumber = view.findViewById(R.id.etProfileNumber);
        btnSave = view.findViewById(R.id.btnSaveProfile);
        etAbout = view.findViewById(R.id.etProfileAbout);


        reference = FirebaseDatabase.getInstance().getReference("vendor").child(firebaseAuth.getCurrentUser().getUid());


        // Fetch data
        reference.get().addOnSuccessListener(dataSnapshot -> {
            if (dataSnapshot.exists()) {
                etName.setText(dataSnapshot.child("name").getValue(String.class));
                etEmail.setText(dataSnapshot.child("email").getValue(String.class));
                etNumber.setText(dataSnapshot.child("number").getValue(String.class));
                etAbout.setText(dataSnapshot.child("about").getValue(String.class));
            }
        });

        // Save updated data
        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String number = etNumber.getText().toString().trim();
            String about = etAbout.getText().toString().trim();

            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("name", name);
            updatedData.put("email", email);
            updatedData.put("number", number);
            updatedData.put("about", about);

            reference.updateChildren(updatedData)
                    .addOnSuccessListener(aVoid -> Toast.makeText(getActivity(), "Profile updated", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(getActivity(), "Update failed", Toast.LENGTH_SHORT).show());
        });


        ///  for use logout btn

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        gsc = GoogleSignIn.getClient(requireActivity(), gso); //  FIXED CONTEXT

        logoutBtn.setOnClickListener(v -> {
            firebaseAuth.signOut();
            gsc.signOut().addOnCompleteListener(task -> {
                Intent intent = new Intent(getActivity(), LoginActivity.class); //  FIXED INTENT
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                requireActivity().finish(); //  FIXED FINISH
            });
        });
    }
}








