package com.example.project.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.project.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityEditProfileBinding implements ViewBinding {
    @NonNull
    private final RelativeLayout rootView;

    @NonNull
    public final Button btnUpdateProfile;

    @NonNull
    public final EditText etFirstName;

    @NonNull
    public final EditText etLastName;

    private ActivityEditProfileBinding(@NonNull RelativeLayout rootView,
                                       @NonNull Button btnUpdateProfile, @NonNull EditText etFirstName,
                                       @NonNull EditText etLastName) {
        this.rootView = rootView;
        this.btnUpdateProfile = btnUpdateProfile;
        this.etFirstName = etFirstName;
        this.etLastName = etLastName;
    }

    @Override
    @NonNull
    public RelativeLayout getRoot() {
        return rootView;
    }

    @NonNull
    public static ActivityEditProfileBinding inflate(@NonNull LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    @NonNull
    public static ActivityEditProfileBinding inflate(@NonNull LayoutInflater inflater,
                                                     @Nullable ViewGroup parent, boolean attachToParent) {
        View root = inflater.inflate(R.layout.activity_edit_profile, parent, false);
        if (attachToParent) {
            parent.addView(root);
        }
        return bind(root);
    }

    @NonNull
    public static ActivityEditProfileBinding bind(@NonNull View rootView) {
        // The body of this method is generated in a way you would not otherwise write.
        // This is done to optimize the compiled bytecode for size and performance.
        int id;
        missingId: {
            id = R.id.btnUpdateProfile;
            Button btnUpdateProfile = rootView.findViewById(id);
            if (btnUpdateProfile == null) {
                break missingId;
            }

            id = R.id.etFirstName;
            EditText etFirstName = rootView.findViewById(id);
            if (etFirstName == null) {
                break missingId;
            }

            id = R.id.etLastName;
            EditText etLastName = rootView.findViewById(id);
            if (etLastName == null) {
                break missingId;
            }

            return new ActivityEditProfileBinding((RelativeLayout) rootView, btnUpdateProfile,
                    etFirstName, etLastName);
        }
        String missingId = rootView.getResources().getResourceName(id);
        throw new NullPointerException("Missing required view with ID: ".concat(missingId));
    }
}

