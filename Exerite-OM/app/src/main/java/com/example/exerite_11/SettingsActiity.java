package com.example.exerite_11;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SettingsActiity extends Fragment {

    private static final String TAG = "SettingsActivity";
    private static final int CAMERA_PERMISSION_CODE = 102;

    private Button signOut;
    private static TextView name, emailAddr;
    private ImageView imgUser;
    private PreviewView cameraPreviewView;
    private ImageCapture imageCapture;
    private ExecutorService cameraExecutor;

    public SettingsActiity() {
        // Required empty public constructor
    }

    public static SettingsActiity newInstance() {
        return new SettingsActiity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraExecutor = Executors.newSingleThreadExecutor(); // Initialize camera executor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings_actiity, container, false);

        emailAddr = rootView.findViewById(R.id.textView4);
        emailAddr.setText(Login_activity.getUserEmail(getContext()));

        name = rootView.findViewById(R.id.greetingTextView);
        name.setText(trimIfNeeded());

        imgUser = rootView.findViewById(R.id.profile_imgview);
        cameraPreviewView = rootView.findViewById(R.id.cameraPreviewView);

        imgUser.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestCameraPermission.launch(Manifest.permission.CAMERA);
            } else {
                startCamera();
            }
        });

        // Set up sign out button
        signOut = rootView.findViewById(R.id.logOutButton);
        signOut.setOnClickListener(v -> signOutUser());

        return rootView;
    }

    private final ActivityResultLauncher<String> requestCameraPermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    startCamera();
                } else {
                    Toast.makeText(requireContext(), "Camera permission is required", Toast.LENGTH_SHORT).show();
                }
            });

    private void signOutUser() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private String trimIfNeeded() {
        String temp = Signup_activity.getUserNameFromPreferences(getContext());
        if (temp.length() >= 7) {
            temp = "Hey, " + temp.substring(0, 4) + "..." + "!";
            return temp;
        } else {
            return "Hey, " + temp + "!";
        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                // Bind the camera to the lifecycle
                bindCameraPreview(cameraProvider);

            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, "Error starting camera: ", e);
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    private void bindCameraPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                .build();

        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        preview.setSurfaceProvider(cameraPreviewView.getSurfaceProvider());

        try {
            // Unbind use cases before rebinding
            cameraProvider.unbindAll();

            // Bind use cases to camera
            cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageCapture);

            // Set the camera preview to be visible
            cameraPreviewView.setVisibility(View.VISIBLE);

            // Capture image when user clicks on the preview
            cameraPreviewView.setOnClickListener(v -> takePhoto());

        } catch (Exception e) {
            Log.e(TAG, "Use case binding failed", e);
        }
    }

    private void takePhoto() {
        File imageFile = createImageFile();

        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(imageFile).build();

        imageCapture.takePicture(outputOptions, cameraExecutor, new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                Uri savedUri = Uri.fromFile(imageFile);
                if (savedUri != null) {
                    getActivity().runOnUiThread(() -> setImageToView(savedUri));
                }
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Log.e(TAG, "Image capture failed", exception);
            }
        });
    }

    private File createImageFile() {
        File storageDir = new File(requireContext().getExternalFilesDir(null), "CameraX");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        String fileName = "IMG_" + System.currentTimeMillis() + ".jpg";
        return new File(storageDir, fileName);
    }

    private void setImageToView(Uri uri) {
        try {
            Bitmap bitmap;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.Source source = ImageDecoder.createSource(requireContext().getContentResolver(), uri);
                bitmap = ImageDecoder.decodeBitmap(source);
            } else {
                bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
            }
            imgUser.setImageBitmap(bitmap);

            // Assuming you have the current user's email stored somewhere
            String currentUserEmail = Login_activity.getUserEmail(getContext());

            // Update the profile image in the SQLite database
            updateProfileImage(currentUserEmail, bitmap);

            // Hide the camera preview after capturing
            cameraPreviewView.setVisibility(View.GONE);

        } catch (IOException e) {
            Log.e(TAG, "Error setting image: ", e);
        }
    }

    private void updateProfileImage(String email, Bitmap profileImage) {
        // Open the database for writing
        SQLiteDatabase db = null;
        try {
            db = new DBHelper(getContext()).getWritableDatabase();

            // Convert the Bitmap to a byte array
            byte[] imageBytes = getBitmapAsByteArray(profileImage);

            // Create a ContentValues object to hold the new profile image
            ContentValues values = new ContentValues();
            values.put("profile_image", imageBytes);

            // Update the user's profile image using their email as the identifier
            int rowsUpdated = db.update("users", values, "email = ?", new String[]{email});

            if (rowsUpdated > 0) {
                Toast.makeText(getContext(), "Profile image updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed to update profile image", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Database update failed", e);
        } finally {
            if (db != null) {
                db.close();  // Ensure the database is closed to free resources
            }
        }
    }

    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown(); // Shutdown the executor when the fragment is destroyed
    }
}
