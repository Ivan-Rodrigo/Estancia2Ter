package com.example.vetaestancia30;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {

    Button image;
    private int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;

    EditText edtcodigo,edtdescripcion,edtstock,edtprecioCom,edtprecioVen,edtfecha;
    Spinner spinner;
    Button btnCargarImage,agregar;
    ImageView imageView;
    VerDatosClientesActivity ver;
    Uri imagen;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);


        spinner=findViewById(R.id.categoria);
        edtcodigo=(EditText)findViewById(R.id.codigo);
        edtdescripcion=(EditText)findViewById(R.id.descripcion);
        edtstock=(EditText)findViewById(R.id.stock);
        edtprecioCom=(EditText)findViewById(R.id.precioc);
        edtprecioVen=(EditText)findViewById(R.id.preciov);
        edtfecha=(EditText)findViewById(R.id.fechar);
        agregar=(Button)findViewById(R.id.btnAgregarProduc);
        imageView=(ImageView)findViewById(R.id.imageView3);

        btnCargarImage=(Button)findViewById(R.id.btnImagen);
        LocalDate fecha =  LocalDate.now();
        LocalTime hora = LocalTime.now();
        String fe= fecha.toString();
        String ho= hora.toString();
        edtfecha.setText(fe+"/"+ho);
        edtfecha.setEnabled(false);

        String [] opc = {"Kit de Videovigilancia","ACCESORIOS VIDEOVIGILANCIA",
                "Cables","Activos Redes","Pasivo Redes","Telefonia","SERVICIO","CAMARAS ANALOGAS Y DIGITALES","ENERGIA SEGURIDAD"
        ,"INTERFONOS Y VIDEOPORTEROS","SOFTWARE","ACCESORIOS DE COMPUTO","CAMARAS IP"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opc);
        spinner.setAdapter(adapter);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProducto("http://192.168.8.2/Android/addProducto.php");

            }
        });
        btnCargarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
                //showFileChooser();
            }
        });

    }





    private void AddProducto(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("--------------------------");
                System.out.println(response);
                System.out.println("__________________________");
                if(!response.isEmpty()){
                    Intent intent = new Intent(getApplicationContext(),PaginaInicioActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(AddProductActivity.this,"Algo Salio mal",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("--------------------------");
                System.out.println("cai aki");
                System.out.println(error.toString());
                System.out.println("__________________________");
                Toast.makeText(AddProductActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //String ima=getStringImagen(bitmap);
                String ventas="0";
                String cate=String.valueOf(spinner.getSelectedItemPosition()+1);
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("id_categoria",cate);
                parametros.put("codigo",edtcodigo.getText().toString());
                parametros.put("descripcion",edtdescripcion.getText().toString());
                parametros.put("imagen",imagen.toString());
                parametros.put("stock",edtstock.getText().toString());
                parametros.put("precio_compra",edtprecioCom.getText().toString());
                parametros.put("precio_venta",edtprecioVen.getText().toString());
                parametros.put("ventas",ventas);
                parametros.put("fecha",edtfecha.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    //////

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Imagen"), PICK_IMAGE_REQUEST);
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Configuración del mapa de bits en ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    */

    //aqui extraigo la direccion de la imagen en un string para guardarlo
    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return encodedImage;
    }

    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione la Aplicacion"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            System.out.println("---------------------------------");
            System.out.println(path);
            imagen=path;
            System.out.println(imagen);
            System.out.println("---------------------------------");

            imageView.setImageURI(path);
        }


    }
}