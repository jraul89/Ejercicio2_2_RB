package com.example.ejercicio2_2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ejercicio2_2.Interfaces.Interfaces;
import com.example.ejercicio2_2.Models.Usuario;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityRetrofit extends AppCompatActivity {

    ListView listView;
    ArrayList<String> titles = new ArrayList<>();
    ArrayAdapter adp;

    Button btnBuscar, btnTodo;
    EditText txtBuscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        init();

        setListeners();
    }

    private void init(){
        listView = (ListView) findViewById(R.id.listViewRetrofit);

        txtBuscar = (EditText) findViewById(R.id.textViewBuscarRetrofit);

        btnBuscar = (Button) findViewById(R.id.btnBuscarRetrofit);
        btnTodo = (Button) findViewById(R.id.btnTodoRetrofit);
    }

    private void setListeners(){
        btnTodo.setOnClickListener(v -> obtenerUsuarios());
        btnBuscar.setOnClickListener(v -> obtenerUsuario());
    }


    private void obtenerUsuario(){

        if(txtBuscar.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Por favor ingresa un id de usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Interfaces interfacesUsers = retrofit.create(Interfaces.class);

        titles = new ArrayList<>();

        Call<Usuario> request = interfacesUsers.getUsuario(txtBuscar.getText().toString());

        request.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if(response.body() != null){
                    Usuario usuario = response.body();

                    titles.add(usuario.title);

                    adp = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, titles);

                    listView.setAdapter(adp);
                }else {
                    Toast.makeText(getApplicationContext(), "No existe el registro", Toast.LENGTH_SHORT).show();
                    adp = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1);
                    listView.setAdapter(adp);
                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: No se pudieron leer los datos", Toast.LENGTH_SHORT).show();
            }
        });





    }

    private void obtenerUsuarios() {

        txtBuscar.setText(null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Interfaces interfacesUsers = retrofit.create(Interfaces.class);

        Call<List<Usuario>> request = interfacesUsers.getUsuarios();

        titles = new ArrayList<>();

        request.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {

                for(Usuario usuario: response.body()){
                    titles.add(usuario.getTitle());
                }

                adp = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, titles);

                listView.setAdapter(adp);
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {

            }
        });


    }

}