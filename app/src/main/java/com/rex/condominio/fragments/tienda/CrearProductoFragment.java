package com.rex.condominio.fragments.tienda;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.rex.condominio.R;
import com.rex.condominio.retrofit.ResponseCallback;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.utils.SupportPreferences;

import java.io.File;

import at.markushi.ui.CircleButton;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CrearProductoFragment extends Fragment {

    private LinearLayout layout_images;
    private ConstraintLayout layout_preview;
    private TextInputEditText et_nombre;
    private CircleButton btn_galery, btn_camera;
    private ImageView img_preview;
    private MaterialButton btn_enviar;
    private File fileImg;
    private final int SELECT_IMG = 1;
    private final int TAKE_IMG = 3;
    private final int CAMERA_PREMISSION = 2;
    private final int READ_PERMISION = 10;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crear_producto, container, false);

        et_nombre = v.findViewById(R.id.et_nombre);
        btn_galery = v.findViewById(R.id.btn_galery);
        btn_camera = v.findViewById(R.id.btn_camera);
        btn_enviar = v.findViewById(R.id.btn_enviar);
        img_preview = v.findViewById(R.id.img_preview);
        layout_images = v.findViewById(R.id.layout_images);
        layout_preview = v.findViewById(R.id.layout_preview);

        btn_galery.setOnClickListener(V -> {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_PERMISION);
            }else {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMG);
            }
        });

        btn_camera.setOnClickListener(V -> {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);
            }else{
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TAKE_IMG);
            }
        });

        btn_enviar.setOnClickListener(this::insert);
        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PREMISSION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, TAKE_IMG);
            }
        }

        if (requestCode == READ_PERMISION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMG);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == SELECT_IMG){
                Uri selectUri = data.getData();
                if (selectUri != null){
                    img_preview.setImageURI(selectUri);
                    fileImg = new File(SupportPreferences.getRealPathFromURI(getContext(), selectUri));
                }
            }

            if (requestCode == TAKE_IMG){
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                img_preview.setImageBitmap(thumbnail);
                fileImg = SupportPreferences.saveImage(getContext(), thumbnail);
            }

            layout_images.setVisibility(View.GONE);
            layout_preview.setVisibility(View.VISIBLE);
        }
    }

    public void insert(View view){
        if (et_nombre.getText().toString().isEmpty()){
            et_nombre.setError("Ingrese el nombre del producto");
        }

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("nomPro", et_nombre.getText().toString());
        builder.addFormDataPart("img", fileImg.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), fileImg));

        Call<ResponseClient<Object>> call = RetrofitClient.getInstance().getRequestInterface().insertProducto(
                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                builder.build().parts()
        );
        call.enqueue(new ResponseCallback<ResponseClient<Object>>() {
            @Override
            public Context returnContext() {
                return getContext();
            }

            @Override
            public void doCallBackResponse(ResponseClient<Object> response) {
                Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
            }
        });
    }
}