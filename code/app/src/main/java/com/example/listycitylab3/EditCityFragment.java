package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {
    private City city;
    private EditText cityName;
    private EditText provinceName;
    private EditCityDialogListener listener;

    interface EditCityDialogListener {
        void onCityEdited(City city, String newCityName, String newProvinceName);
    }

    // Constructor with the city to edit
    public EditCityFragment(City city) {
        this.city = city;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_city, null);
        EditText cityName = view.findViewById(R.id.edit_text_city_text);
        EditText provinceName = view.findViewById(R.id.edit_text_province_text);

        // Pre-fill the fields with current city info
        cityName.setText(city.getName());
        provinceName.setText(city.getProvince());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    String newCityName = cityName.getText().toString();
                    String newProvinceName = provinceName.getText().toString();
                    listener.onCityEdited(city, newCityName, newProvinceName);
                })
                .create();
    }
}