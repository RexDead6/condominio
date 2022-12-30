package com.rex.condominio.fragments.activarUser;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.transition.MaterialContainerTransform;
import com.rex.condominio.R;
import com.rex.condominio.activities.MainActivity;
import com.rex.condominio.activities.register.RegisterActivity;
import com.rex.condominio.retrofit.RetrofitClient;
import com.rex.condominio.retrofit.request.RelacionFamiliarRequest;
import com.rex.condominio.retrofit.response.ResponseClient;
import com.rex.condominio.retrofit.response.TokenResponse;
import com.rex.condominio.utils.SupportPreferences;
import com.rex.condominio.utils.TokenSupport;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class QrScannerFragment extends Fragment {

    private CameraSource cameraSource;
    private SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private final int PERMISSION_REQUEST_CAMERA = 1;
    private String ACTUAL_TOKEN = "";
    private String ANTERIOR_TOKEN = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_qr_scanner, container, false);
        setSharedElementEnterTransition(new MaterialContainerTransform());

        surfaceView = v.findViewById(R.id.surfaceView);

        barcodeDetector = new BarcodeDetector.Builder(getContext())
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource.Builder(getContext(), barcodeDetector)
                .setRequestedPreviewSize(1920, 1024)
                .setAutoFocusEnabled(true)
                .build();
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                cameraFunction();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {}

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() > 0){
                    ACTUAL_TOKEN  = barcodes.valueAt(0).displayValue;

                    if (!ACTUAL_TOKEN.equals(ANTERIOR_TOKEN)){
                        ANTERIOR_TOKEN = ACTUAL_TOKEN;

                        RelacionFamiliarRequest relacionFamiliarRequest = new RelacionFamiliarRequest(
                                ACTUAL_TOKEN
                        );
                        Call<ResponseClient<TokenResponse>> call = RetrofitClient.getInstance().getRequestInterface().relacionFamiliar(
                                SupportPreferences.getInstance(getContext()).getPreference(SupportPreferences.TOKEN_PREFERENCE),
                                relacionFamiliarRequest
                        );
                        call.enqueue(new Callback<ResponseClient<TokenResponse>>() {
                            @Override
                            public void onResponse(Call<ResponseClient<TokenResponse>> call, Response<ResponseClient<TokenResponse>> response) {
                                if (response.code() == 201){
                                    SupportPreferences.getInstance(getContext()).savePreference(SupportPreferences.TOKEN_PREFERENCE, response.body().getData().getToken());
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                } else {
                                    new MaterialAlertDialogBuilder(getContext())
                                            .setMessage(response.body().getMessage())
                                            .setPositiveButton("Aceptar", (d, w) -> d.dismiss())
                                            .create().show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseClient<TokenResponse>> call, Throwable t) {
                                Log.e("RelacionFamiliar", t.toString());
                            }
                        });
                    }
                }
            }
        });
    }

    private void cameraFunction(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        } else {
            try {
                cameraSource.start(surfaceView.getHolder());
            } catch (IOException e){
                Log.e("CAMERA SOURCE", e.toString());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CAMERA){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                try {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    cameraSource.start(surfaceView.getHolder());
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }
        }
    }
}