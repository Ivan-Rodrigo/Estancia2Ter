package com.example.vetaestancia30;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class EditarClientesActivity extends AppCompatActivity {
    EditText nombre,documento,email,telefono,direccion,fechaNa;
    Button editar;
    int position;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_clientes);
        nombre=(EditText)findViewById(R.id.edtNombreCLiedit);
        documento=(EditText)findViewById(R.id.edtDocumentoedit);
        email=(EditText)findViewById(R.id.edtEmailEdit);
        telefono=(EditText)findViewById(R.id.edtTelefonoEdit);
        direccion=(EditText)findViewById(R.id.edtDireccionEdit);
        fechaNa=(EditText)findViewById(R.id.edtFechaNacEdit);
        editar=(Button) findViewById(R.id.btnEditarCliente);


        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        nombre.setText(PaginaInicioActivity.clients.get(position).getNombre());
        documento.setText(PaginaInicioActivity.clients.get(position).getDocumento());
        email.setText(PaginaInicioActivity.clients.get(position).getEmail());
        telefono.setText(PaginaInicioActivity.clients.get(position).getTelefono());
        direccion.setText(PaginaInicioActivity.clients.get(position).getDireccion());
        fechaNa.setText(PaginaInicioActivity.clients.get(position).getFecha_nacimiento());
        id=PaginaInicioActivity.clients.get(position).getId();

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditCliente("http://192.168.8.3/Android/updateClientes.php");
            }
        });


    }

    private void EditCliente(String URL){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);

                if(!response.isEmpty()){
                    Toast.makeText(EditarClientesActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),PaginaInicioActivity.class));
                    finish();
                    progressDialog.dismiss();
                }else {
                    Toast.makeText(EditarClientesActivity.this,"Algo Salio mal",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("--------------------------");
                System.out.println("cai aki");
                System.out.println(error.toString());
                System.out.println("__________________________");
                Toast.makeText(EditarClientesActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("nombre",nombre.getText().toString());
                parametros.put("documento",documento.getText().toString());
                parametros.put("email",email.getText().toString());
                parametros.put("telefono",telefono.getText().toString());
                parametros.put("direccion",direccion.getText().toString());
                parametros.put("fecha_nacimiento",fechaNa.getText().toString());
                parametros.put("id",id);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}