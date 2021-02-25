package com.example.vetaestancia30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;


public class VerDatosProductosActivity extends AppCompatActivity {
    TextView codigo,desc,stock,precioVen,ventas,fecha;
    ImageView imageView;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_datos_productos);

        codigo=(TextView)findViewById(R.id.textViewCodigo);
        desc=(TextView)findViewById(R.id.textViewDescripcion);
        stock=(TextView)findViewById(R.id.textViewStock);
        precioVen=(TextView)findViewById(R.id.textViewPrecioVenta);
        ventas=(TextView)findViewById(R.id.textViewVentas);
        fecha=(TextView)findViewById(R.id.textViewFecha);
        imageView=(ImageView)findViewById(R.id.imageViewVerFoto);

        Intent intent = getIntent();
        position= intent.getExtras().getInt("position");
        codigo.setText("CODIGO: "+PaginaInicioActivity.products.get(position).getCodigo());
        desc.setText("DESCRIPCION: "+PaginaInicioActivity.products.get(position).getDescripcion());
        stock.setText("STOCK: "+PaginaInicioActivity.products.get(position).getStock());
        precioVen.setText("PRECIO DE VENTA: "+PaginaInicioActivity.products.get(position).getPrecio_venta());
        ventas.setText("VENTAS: "+PaginaInicioActivity.products.get(position).getVentas());
        fecha.setText("FECHA DE INGRESO: "+PaginaInicioActivity.products.get(position).getFecha());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();



       // imageView.setImageBitmap(BitmapFactory.decodeFile(PaginaInicioActivity.products.get(position).getImagen()));
        System.out.println("______");
        System.out.println(PaginaInicioActivity.products.get(position).getImagen());
        System.out.println("______");
        imageView.setImageURI(Uri.parse(PaginaInicioActivity.products.get(position).getImagen()));

    //decadficar();

    }

    public void decadficar(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageBytes = baos.toByteArray();
        //decode base64 string to image}
        //options.inJustDecodeBounds = false;
        imageBytes = Base64.decode(PaginaInicioActivity.products.get(position).getImagen(), Base64.URL_SAFE);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        System.out.println(decodedImage);
        imageView.setImageBitmap(decodedImage);
    }

}