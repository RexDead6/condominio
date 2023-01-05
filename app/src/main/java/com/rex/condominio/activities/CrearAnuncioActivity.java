package com.rex.condominio.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.google.gson.Gson;
import com.rex.condominio.R;
import com.rex.condominio.dialogs.DialogProgress;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.TokenResponse;
import com.rex.condominio.utils.SupportPreferences;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import at.markushi.ui.CircleButton;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearAnuncioActivity extends AppCompatActivity {

    private TextInputEditText tv_descripcion;
    private LinearLayout layout_images;
    private ConstraintLayout layout_preview;
    private ImageView img_preview;
    private Toolbar toolbar;
    private DialogProgress progress;

    private File fileImg;
    private final int SELECT_IMG = 200;
    private final int TAKE_IMG = 100;
    private final int CAMERA_PREMISSION = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        findViewById(android.R.id.content).setTransitionName("crearAnuncio");
        setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());

        MaterialContainerTransform eet = new MaterialContainerTransform();
        eet.addTarget(android.R.id.content);
        eet.setDuration(300L);
        getWindow().setSharedElementEnterTransition(eet);

        MaterialContainerTransform ert = new MaterialContainerTransform();
        ert.addTarget(android.R.id.content);
        ert.setDuration(250L);
        getWindow().setSharedElementReturnTransition(ert);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_anuncio);

        tv_descripcion = findViewById(R.id.tv_descripcion);
        layout_images = findViewById(R.id.layout_images);
        layout_preview = findViewById(R.id.layout_preview);
        img_preview = findViewById(R.id.img_preview);
        toolbar = findViewById(R.id.toolbar);

        progress = new DialogProgress(this);

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar_yes_no, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_cancel:{
                onBackPressed();
                break;
            }
            case R.id.menu_aceptar:{
                if (tv_descripcion.getText().toString().isEmpty()){
                    tv_descripcion.setError("Ingrese una descripcion");
                    return true;
                }
                RequestBody requestDesc = RequestBody.create(MediaType.parse("multipart/form-data"), tv_descripcion.getText().toString());
                progress.show();
                if (fileImg == null){
                    Call<ResponseClient<Object>> call = RetrofitClient.getInstance().getRequestInterface().insertAnuncio(
                            SupportPreferences.getInstance(this).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                            requestDesc
                    );
                    call.enqueue(callback);
                }else{
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileImg);
                    MultipartBody.Part requestImage = MultipartBody.Part.createFormData("image", fileImg.getName(), requestFile);
                    Call<ResponseClient<Object>> call = RetrofitClient.getInstance().getRequestInterface().insertAnuncio(
                            SupportPreferences.getInstance(this).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                            requestImage,
                            requestDesc
                    );
                    call.enqueue(callback);
                }
                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    Callback<ResponseClient<Object>> callback = new Callback<ResponseClient<Object>>() {
        @Override
        public void onResponse(Call<ResponseClient<Object>> call, Response<ResponseClient<Object>> response) {
            progress.dismiss();
            if (response.code() == 201){
                Toast.makeText(CrearAnuncioActivity.this, "Anuncio Publicado exitosamente", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return;
            }

            ResponseClient<TokenResponse> errorResponse = new Gson().fromJson(response.errorBody().charStream(), ResponseClient.class);

            new AlertDialog.Builder(CrearAnuncioActivity.this)
                    .setMessage(errorResponse.getMessage())
                    .setPositiveButton("Aceptar", (d, v) -> d.dismiss())
                    .create().show();
        }

        @Override
        public void onFailure(Call<ResponseClient<Object>> call, Throwable t) {
            progress.dismiss();
            Log.e("insertAnuncio", t.toString());
        }
    };

    public void btn_image(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent, SELECT_IMG);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void btn_camera(View v){
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PREMISSION);
        }else{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, TAKE_IMG);
        }
    }

    public void btn_delete_phono(View v){
        fileImg = null;
        layout_images.setVisibility(View.VISIBLE);
        layout_preview.setVisibility(View.GONE);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == SELECT_IMG){
                Uri selectUri = data.getData();
                if (selectUri != null){
                    img_preview.setImageURI(selectUri);
                    fileImg = new File(selectUri.getPath());
                }
            }

            if (requestCode == TAKE_IMG){
                Bitmap img = (Bitmap) data.getExtras().get("data");
                img_preview.setImageBitmap(img);
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(fileImg.getAbsolutePath(), bitmapOptions);

                String path = android.os.Environment.getExternalStorageDirectory()
                        +File.separator
                        +"Phoenix" + File.separator + "default";
                fileImg.delete();
                fileImg = new File(path, "temp_image.jpg");

                try {
                    OutputStream outFile = new FileOutputStream(fileImg);
                    outFile.flush();
                    outFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            layout_images.setVisibility(View.GONE);
            layout_preview.setVisibility(View.VISIBLE);
        }
    }
}