package ru.mirea.smirnovmv.mireaproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;

import ru.mirea.smirnovmv.mireaproject.databinding.FragmentCameraBinding;

public class CameraFragment extends Fragment implements ActivityResultCallback<ActivityResult> {
    private ActivityResultLauncher<Intent> cameraActivityResultLauncher = null;
    private FragmentCameraBinding binding = null;
    private Uri store_profile_photo_uri;
    public CameraFragment() { }

    @NonNull
    public static CameraFragment newInstance() {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i("Work", "onCreateView");
        binding = FragmentCameraBinding.inflate(inflater, container, false);
        cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), this);
        binding.button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        OnUpdatePhotoButtonClicked(view);
                    }
                }
        );

        return binding.getRoot();
    }
    private Uri getStorageFileUri() throws IOException {
        File storageDirectory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = File.createTempFile("temp_user_profile_photo", ".jpg", storageDirectory);
        String authorities = requireContext().getPackageName() + ".fileprovider";
        return FileProvider.getUriForFile(getActivity(), authorities, file);
    }


    public void OnUpdatePhotoButtonClicked(View v) {
        try {
            store_profile_photo_uri = getStorageFileUri();
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, store_profile_photo_uri);
            cameraActivityResultLauncher.launch(cameraIntent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void onActivityResult(ActivityResult o) {
        if(o.getResultCode() == Activity.RESULT_OK)
            binding.profilePhotoView.setImageURI(store_profile_photo_uri);
    }
}