package com.example.alimentacao.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.alimentacao.R;
import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    public MapsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) mapFragment.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            showUserLocation();
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }

        addEstacioCampuses();
    }

    private void showUserLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        LatLng userLoc = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLoc, 16));
                        mMap.addMarker(new MarkerOptions().position(userLoc).title("Você está aqui"));
                    } else {
                        Toast.makeText(requireContext(), "Não foi possível obter sua localização", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(requireContext(), "Erro ao obter localização", Toast.LENGTH_SHORT).show());
    }

    private void addEstacioCampuses() {
        // Campus Prado
        LatLng prado = new LatLng(-19.93, -43.94);
        mMap.addMarker(new MarkerOptions()
                .position(prado)
                .title("Estácio - Campus Prado")
                .snippet("Rua Erê, 207, Prado, BH"));

        // Campus Floresta
        LatLng floresta = new LatLng(-19.92, -43.94);
        mMap.addMarker(new MarkerOptions()
                .position(floresta)
                .title("Estácio - Campus Floresta")
                .snippet("Av. Francisco Sales, 23, Floresta, BH"));

        // Campus Savassi
        LatLng savassi = new LatLng(-19.93, -43.93);
        mMap.addMarker(new MarkerOptions()
                .position(savassi)
                .title("Estácio - Campus Savassi")
                .snippet("Rua Sergipe, Savassi, BH"));

        // Campus Venda Nova
        LatLng vendaNova = new LatLng(-19.812244, -43.973556);
        mMap.addMarker(new MarkerOptions()
                .position(vendaNova)
                .title("Estácio - Campus Venda Nova")
                .snippet("Rua Padre Pedro Pinto, 1055, Venda Nova, BH"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                showUserLocation();
            }
        } else {
            Toast.makeText(requireContext(), "Permissão de localização negada", Toast.LENGTH_SHORT).show();
        }
    }
}
