package com.flores.appfogata;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView registro= (TextView)findViewById(R.id.registroLogin);
        Button btnLogin=(Button)findViewById(R.id.btnLogin);
        final EditText usuarioT=(EditText)findViewById(R.id.usuarioLogin);
        final EditText claveT=(EditText)findViewById(R.id.claveLogin);
        registro.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent registro= new Intent(Login.this,Registro.class);
                Login.this.startActivity(registro);
                finish();
            }
        });
        btnLogin.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usuario= usuarioT.getText().toString();
                final String clave= claveT.getText().toString();
                Response.Listener<String> respuesta= new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonRespuesta= new JSONObject(response);
                            boolean ok=jsonRespuesta.getBoolean("success");
                            if(ok==true){
                                String nombre= jsonRespuesta.getString("nombre");
                                int edad= jsonRespuesta.getInt("edad");
                                Intent bienvenido=new Intent(Login.this, Bienvenido.class);
                                bienvenido.putExtra("nombre",nombre);
                                bienvenido.putExtra("edad",edad);
                                Login.this.startActivity(bienvenido);


                            }else {
                                AlertDialog.Builder alerta=new AlertDialog.Builder(Login.this);
                                alerta.setMessage("Falló en el Login").setNegativeButton("Reintentar",null).create().show();
                            }
                        }catch (JSONException e){
                            e.getMessage();
                        }
                    }

                };
                LoginRequest r= new LoginRequest(usuario,clave,respuesta);
                RequestQueue cola= Volley.newRequestQueue(Login.this);
                cola.add(r);
            }
        });
    }

}