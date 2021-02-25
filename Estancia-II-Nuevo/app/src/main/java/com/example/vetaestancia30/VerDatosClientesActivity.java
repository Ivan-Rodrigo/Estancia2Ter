package com.example.vetaestancia30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class VerDatosClientesActivity extends AppCompatActivity {
    TextView nombre,documento,email,telefo,direcc,fechaNa,Compras,ultCom;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_datos_clientes);

        nombre=(TextView)findViewById(R.id.textViewNombre);
        documento=(TextView)findViewById(R.id.textViewDocumento);
        email=(TextView)findViewById(R.id.textViewEmail);
        telefo=(TextView)findViewById(R.id.textViewTelefono);
        direcc=(TextView)findViewById(R.id.textViewDireccion);
        fechaNa=(TextView)findViewById(R.id.textViewFechaNa);
        Compras=(TextView)findViewById(R.id.textViewCompras);
        ultCom=(TextView)findViewById(R.id.textViewFechaUltimaCo);
        Intent intent = getIntent();
        position= intent.getExtras().getInt("position");

        nombre.setText("Nombre :"+PaginaInicioActivity.clients.get(position).getNombre());
        documento.setText("Documento :"+PaginaInicioActivity.clients.get(position).getDocumento());
        email.setText("Correo: "+PaginaInicioActivity.clients.get(position).getEmail());
        telefo.setText("Numero de Telefono : "+PaginaInicioActivity.clients.get(position).getTelefono());
        direcc.setText("Direccion : "+PaginaInicioActivity.clients.get(position).getDireccion());
        fechaNa.setText("Fecha de Nacimiento : "+PaginaInicioActivity.clients.get(position).getFecha_nacimiento());
        Compras.setText("Compras Realizadas : "+PaginaInicioActivity.clients.get(position).getCompras());
        ultCom.setText("Ultima Compra realizada : "+PaginaInicioActivity.clients.get(position).getUltima_compra());

    }
}