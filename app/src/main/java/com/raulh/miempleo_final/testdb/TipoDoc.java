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
import com.raulh.miempleo_final.dao.TipoDocDataSource;
import com.raulh.miempleo_final.models.TipoDoc_c;

import java.util.List;

/**
 * Created by raulh on 05/11/2014.
 */
public class TipoDoc extends Fragment {

    public long idtipo;
    public String nombre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.lay_tipodoc, container, false);

        Actualizar(rootView);

        Button but = (Button) rootView.findViewById(R.id.btn_Agregar);
        Button but2 = (Button) rootView.findViewById(R.id.btn_Actualizar);
        final ListView lvDatos = (ListView) rootView.findViewById(R.id.listView);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), TipoDoc.create.class);
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


                idtipo = ObtenerId(position);
                nombre = ObtenerName(position);

                try {

                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(getActivity());

                    builder.setMessage("Codigo: " + idtipo + "\n Nombre: " + nombre)
                            .setTitle("Mi Empleo - Tipo Doc")
                            .setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent i = new Intent(getActivity().getApplicationContext(), TipoDoc.update.class);


                                    i.putExtra("param1", String.valueOf(idtipo));
                                    i.putExtra("param2", nombre);

                                    startActivity(i);
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    TipoDocDataSource tipod = new TipoDocDataSource(getActivity().getApplicationContext());
                                    tipod.open();
                                    tipod.deleteTipoDoc(idtipo);
                                    tipod.close();
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

        /*Toast.makeText(
                getActivity().getApplicationContext(),
                "onResume",
                Toast.LENGTH_LONG ).show();*/

        Actualizar(getActivity().findViewById(R.id.listView));

        super.onResume();
    }

    public String ObtenerName(int position) {

        String a = "";
        try {


            TipoDocDataSource tipod = new TipoDocDataSource(getActivity().getApplicationContext());
            tipod.open();

            List<TipoDoc_c> listTipo = tipod.getAllTipoDoc();

            a = listTipo.get(position).getNombretipo();
            tipod.close();

        } catch (Throwable e) {
            e.printStackTrace();
        }


        return a;

    }

    public long ObtenerId(int position) {

        long a = 0;
        try {


            TipoDocDataSource tipod = new TipoDocDataSource(getActivity().getApplicationContext());
            tipod.open();

            List<TipoDoc_c> listTipo = tipod.getAllTipoDoc();

            a = listTipo.get(position).getIdtipo();
            tipod.close();

        } catch (Throwable e) {
            e.printStackTrace();
        }


        return a;

    }

    public void Actualizar(View rootView) {

        //        Get listview
        ListView deptos = (ListView) rootView.findViewById(R.id.listView);
        //        get all deptos
        TipoDocDataSource depto = new TipoDocDataSource(getActivity().getApplicationContext());

        depto.open();


        ArrayAdapter<TipoDoc_c> adapter = new ArrayAdapter<TipoDoc_c>(
                getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,
                depto.getAllTipoDoc());

//        Set adapter to show populated items
        deptos.setAdapter(adapter);

        depto.close();

    }

    public static class update extends Activity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.lay_tipodoc_edit);

            TextView codigo1 = (TextView) findViewById(R.id.txt_codigo);
            TextView name1 = (TextView) findViewById(R.id.txt_name);

            // Mostramos los parámetros recibidos de la actividad mainActivity
            Bundle reicieveParams = getIntent().getExtras();

            long cod = Long.parseLong(reicieveParams.getString("param1"));
            String nom = reicieveParams.getString("param2");

            codigo1.setText(String.valueOf(cod));
            name1.setText(nom);

            findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TipoDocDataSource tipod = new TipoDocDataSource(getApplicationContext());
                    tipod.open();

                    TextView codigo = (TextView) findViewById(R.id.txt_codigo);
                    TextView name = (TextView) findViewById(R.id.txt_name);

                    TipoDoc_c tipodoc_c = new TipoDoc_c();


                    tipodoc_c.setIdtipo(Long.parseLong(codigo.getText().toString()));
                    tipodoc_c.setNombretipo(name.getText().toString());

                    tipod.updatTipoDoc(tipodoc_c);

                    tipod.close();

                    Toast.makeText(
                            getApplicationContext(),
                            "Modificado: " + tipodoc_c.getIdtipo() + " " +
                                    tipodoc_c.getNombretipo(),
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

            setContentView(R.layout.lay_tipodoc_add);

            findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TipoDocDataSource depto = new TipoDocDataSource(getApplicationContext());
                    depto.open();

                    TextView name = (TextView) findViewById(R.id.txt_name);

                    TipoDoc_c departamento_c = depto.createTipoDoc(name.getText().toString());

                    depto.close();

                    Toast.makeText(
                            getApplicationContext(),
                            "Creado: " + departamento_c.getIdtipo() + " " +
                                    departamento_c.getNombretipo(),
                            Toast.LENGTH_LONG
                    ).show();


                    finish();
                }
            });
        }
    }

}