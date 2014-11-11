package com.raulh.miempleo_final.testdb;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.raulh.miempleo_final.R;
import com.raulh.miempleo_final.dao.MunicipioDataSource;
import com.raulh.miempleo_final.models.Municipio_c;

import java.util.List;

/**
 * Created by raulh on 05/11/2014.
 */
public class Municipio extends Fragment {

    public long idmunicipio;
    public String nombre;
    public String iddepto;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.lay_municipio, container, false);

        Actualizar(rootView);

        Button but = (Button) rootView.findViewById(R.id.btn_Agregar);
        Button but2 = (Button) rootView.findViewById(R.id.btn_Actualizar);
        final ListView lvDatos = (ListView) rootView.findViewById(R.id.listView);


        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), Municipio.create.class);
                startActivity(i);

            }
        });

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Actualizar(rootView);
            }
        });

        lvDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                idmunicipio = ObtenerId(position);
                nombre = ObtenerName(position);
                iddepto = ObtenerDepartamento(position);

                try {

                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(getActivity());

                    builder.setMessage("Codigo: " + idmunicipio + "\n Nombre: " + nombre)
                            .setTitle("Mi Empleo - Municipio")
                            .setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent i = new Intent(getActivity().getApplicationContext(), Municipio.update.class);


                                    i.putExtra("param1", String.valueOf(idmunicipio));
                                    i.putExtra("param2", nombre);
                                    i.putExtra("param3", iddepto);

                                    startActivity(i);
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    MunicipioDataSource municipio = new MunicipioDataSource(getActivity().getApplicationContext());
                                    municipio.open();
                                    municipio.deleteMunicipio(idmunicipio);
                                    municipio.close();


                                    Toast.makeText(
                                            getActivity().getApplicationContext(),
                                            "Registro Eliminado Con Exito",
                                            Toast.LENGTH_LONG).show();
                                    Actualizar(rootView);
                                    dialog.cancel();
                                }
                            });


                    builder.create();
                    builder.show();


                } catch (Throwable e) {
                    e.printStackTrace();
                }


            }
        });


        return rootView;
    }

    @Override
    public void onResume() {

        Actualizar(getActivity().findViewById(R.id.listView));
        super.onResume();
    }

    public String ObtenerDepartamento(int position) {

        String a = "";
        try {


            MunicipioDataSource munic = new MunicipioDataSource(getActivity().getApplicationContext());
            munic.open();

            List<Municipio_c> listMunic = munic.getAllMunicipios();

            a = listMunic.get(position).getIddep();
            munic.close();

        } catch (Throwable e) {
            e.printStackTrace();
        }


        return a;

    }

    public String ObtenerName(int position) {

        String a = "";
        try {


            MunicipioDataSource munic = new MunicipioDataSource(getActivity().getApplicationContext());
            munic.open();

            List<Municipio_c> listMunic = munic.getAllMunicipios();

            a = listMunic.get(position).getNombremun();
            munic.close();

        } catch (Throwable e) {
            e.printStackTrace();
        }


        return a;

    }

    public long ObtenerId(int position) {

        long a = 0;
        try {


            MunicipioDataSource munic = new MunicipioDataSource(getActivity().getApplicationContext());
            munic.open();

            List<Municipio_c> listMunic = munic.getAllMunicipios();

            a = listMunic.get(position).getIdmun();
            munic.close();

        } catch (Throwable e) {
            e.printStackTrace();
        }


        return a;

    }

    public void Actualizar(View rootView) {

        //        Get listview
        ListView municList = (ListView) rootView.findViewById(R.id.listView);
        //        get all deptos
        MunicipioDataSource munic = new MunicipioDataSource(getActivity().getApplicationContext());

        munic.open();


        ArrayAdapter<Municipio_c> adapter = new ArrayAdapter<Municipio_c>(
                getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,
                munic.getAllMunicipios());

//        Set adapter to show populated items
        municList.setAdapter(adapter);

        munic.close();

    }

    public static class update extends Activity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.lay_municipio_edit);

            TextView codigo1 = (TextView) findViewById(R.id.txt_codigo);
            TextView name1 = (TextView) findViewById(R.id.txt_name);
            TextView depto1 = (TextView) findViewById(R.id.txt_departamento);

            // Mostramos los parámetros recibidos de la actividad mainActivity
            Bundle reicieveParams = getIntent().getExtras();

            long cod = Long.parseLong(reicieveParams.getString("param1"));
            String nom = reicieveParams.getString("param2");
            String dep = reicieveParams.getString("param3");

            codigo1.setText(String.valueOf(cod));
            name1.setText(nom);
            depto1.setText(dep);

            findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MunicipioDataSource munic = new MunicipioDataSource(getApplicationContext());
                    munic.open();

                    TextView codigo = (TextView) findViewById(R.id.txt_codigo);
                    TextView name = (TextView) findViewById(R.id.txt_name);
                    TextView departamento = (TextView) findViewById(R.id.txt_departamento);


                    Municipio_c municipio_c = new Municipio_c();


                    municipio_c.setIdmun(Long.parseLong(codigo.getText().toString()));
                    municipio_c.setNombremun(name.getText().toString());
                    municipio_c.setIddep(departamento.getText().toString());

                    munic.updateMunicipio(municipio_c);

                    munic.close();

                    Toast.makeText(
                            getApplicationContext(),
                            "Modificado: " + municipio_c.getIddep() + " " +
                                    municipio_c.getNombremun(),
                            Toast.LENGTH_LONG).show();


                    finish();
                }
            });
        }
    }

    public static class create extends Activity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.lay_municipio_add);

            findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MunicipioDataSource munic = new MunicipioDataSource(getApplicationContext());
                    munic.open();

                    TextView name = (TextView) findViewById(R.id.txt_name);
                    TextView departamento = (TextView) findViewById(R.id.txt_departamento);


                    Municipio_c municipio_c = munic.createMunicipio(name.getText().toString(), departamento.getText().toString());

                    munic.close();

                    Toast.makeText(
                            getApplicationContext(),
                            "Creado: " + municipio_c.getIdmun() + " " +
                                    municipio_c.getNombremun() + " " + municipio_c.getIddep(),
                            Toast.LENGTH_LONG
                    ).show();


                    finish();
                }
            });
        }
    }

}

