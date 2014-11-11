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
import com.raulh.miempleo_final.dao.NivelEstudioDataSource;
import com.raulh.miempleo_final.models.NivelEstudio_c;

import java.util.List;

/**
 * Created by raulh on 05/11/2014.
 */
public class NivelEstudio extends Fragment {

    public long idnivel;
    public String nombre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.lay_nivelestudio, container, false);

        Actualizar(rootView);

        Button but = (Button) rootView.findViewById(R.id.btn_Agregar);
        Button but2 = (Button) rootView.findViewById(R.id.btn_Actualizar);
        final ListView lvDatos = (ListView) rootView.findViewById(R.id.listView);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), NivelEstudio.create.class);
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


                idnivel = ObtenerId(position);
                nombre = ObtenerName(position);

                try {

                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(getActivity());

                    builder.setMessage("Codigo: " + idnivel + "\n Nombre: " + nombre)
                            .setTitle("Mi Empleo - Nivel Empleo")
                            .setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent i = new Intent(getActivity().getApplicationContext(), NivelEstudio.update.class);


                                    i.putExtra("param1", String.valueOf(idnivel));
                                    i.putExtra("param2", nombre);

                                    startActivity(i);
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    NivelEstudioDataSource nivels = new NivelEstudioDataSource(getActivity().getApplicationContext());
                                    nivels.open();
                                    nivels.deleteNivelEstudio(idnivel);
                                    nivels.close();
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


            NivelEstudioDataSource nivels = new NivelEstudioDataSource(getActivity().getApplicationContext());
            nivels.open();

            List<NivelEstudio_c> listNivel = nivels.getAllNivelEstudio();

            a = listNivel.get(position).getNombrenivel();
            nivels.close();

        } catch (Throwable e) {
            e.printStackTrace();
        }


        return a;

    }

    public long ObtenerId(int position) {

        long a = 0;
        try {


            NivelEstudioDataSource nivels = new NivelEstudioDataSource(getActivity().getApplicationContext());
            nivels.open();

            List<NivelEstudio_c> listNivel = nivels.getAllNivelEstudio();

            a = listNivel.get(position).getIdnivel();
            nivels.close();

        } catch (Throwable e) {
            e.printStackTrace();
        }


        return a;

    }

    public void Actualizar(View rootView) {

        //        Get listview
        ListView niveles = (ListView) rootView.findViewById(R.id.listView);
        //        get all deptos
        NivelEstudioDataSource nivel = new NivelEstudioDataSource(getActivity().getApplicationContext());

        nivel.open();


        ArrayAdapter<NivelEstudio_c> adapter = new ArrayAdapter<NivelEstudio_c>(
                getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,
                nivel.getAllNivelEstudio());

//        Set adapter to show populated items
        niveles.setAdapter(adapter);

        nivel.close();

    }

    public static class update extends Activity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.lay_nivelestudio_edit);

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
                    NivelEstudioDataSource nivels = new NivelEstudioDataSource(getApplicationContext());
                    nivels.open();

                    TextView codigo = (TextView) findViewById(R.id.txt_codigo);
                    TextView name = (TextView) findViewById(R.id.txt_name);

                    NivelEstudio_c nivelestudio_c = new NivelEstudio_c();


                    nivelestudio_c.setIdnivel(Long.parseLong(codigo.getText().toString()));
                    nivelestudio_c.setNombrenivel(name.getText().toString());

                    nivels.updateNivelEstudio(nivelestudio_c);

                    nivels.close();

                    Toast.makeText(
                            getApplicationContext(),
                            "Modificado: " + nivelestudio_c.getIdnivel() + " " +
                                    nivelestudio_c.getNombrenivel(),
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

            setContentView(R.layout.lay_nivelestudio_add);

            findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NivelEstudioDataSource nivels = new NivelEstudioDataSource(getApplicationContext());
                    nivels.open();

                    TextView name = (TextView) findViewById(R.id.txt_name);

                    NivelEstudio_c nivelesttudio_c = nivels.createNivelEstudio(name.getText().toString());

                    nivels.close();

                    Toast.makeText(
                            getApplicationContext(),
                            "Creado: " + nivelesttudio_c.getIdnivel() + " " +
                                    nivelesttudio_c.getNombrenivel(),
                            Toast.LENGTH_LONG).show();


                    finish();
                }
            });
        }
    }

}
