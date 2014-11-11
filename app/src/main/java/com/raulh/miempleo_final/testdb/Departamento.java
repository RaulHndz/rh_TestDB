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
import com.raulh.miempleo_final.dao.DepartamentoDataSource;
import com.raulh.miempleo_final.models.Departamento_c;

import java.util.List;

/**
 * Created by raulh on 05/11/2014.
 */
public class Departamento extends Fragment {

    public long iddepto;
    public String nombre;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.lay_departamento, container, false);

        Actualizar(rootView);

        Button but = (Button) rootView.findViewById(R.id.btn_Agregar);
        Button but2 = (Button) rootView.findViewById(R.id.btn_Actualizar);
        final ListView lvDatos = (ListView) rootView.findViewById(R.id.listView);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), Departamento.create.class);
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

            /*

               Toast.makeText(
                       getActivity().getApplicationContext(),
                       "Codigo: " + ObtenerId(position) + "\n Nombre: "+ObtenerName(position),
                       Toast.LENGTH_LONG ).show();*/

                iddepto = ObtenerId(position);
                nombre = ObtenerName(position);

                try {

                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(getActivity());

                    builder.setMessage("Codigo: " + iddepto + "\n Nombre: " + nombre)
                            .setTitle("Mi Empleo - Departamento")
                            .setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Intent i = new Intent(getActivity().getApplicationContext(), Departamento.update.class);


                                    i.putExtra("param1", String.valueOf(iddepto));
                                    i.putExtra("param2", nombre);

                                    startActivity(i);
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    DepartamentoDataSource depto = new DepartamentoDataSource(getActivity().getApplicationContext());
                                    depto.open();
                                    depto.deleteDepartament(iddepto);
                                    depto.close();
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


            DepartamentoDataSource depto = new DepartamentoDataSource(getActivity().getApplicationContext());
            depto.open();

            List<Departamento_c> listdepto = depto.getAllDepartamentos();
            //ArrayList<Departamento_c> listdepto = depto.getAllDepartamentos();

            a = listdepto.get(position).getNombredep();
            depto.close();

        } catch (Throwable e) {
            e.printStackTrace();
        }


        return a;

    }

    public long ObtenerId(int position) {

        long a = 0;
        try {


            DepartamentoDataSource depto = new DepartamentoDataSource(getActivity().getApplicationContext());
            depto.open();

            List<Departamento_c> listdepto = depto.getAllDepartamentos();
            //ArrayList<Departamento_c> listdepto = depto.getAllDepartamentos();

            a = listdepto.get(position).getIddep();
            depto.close();

        } catch (Throwable e) {
            e.printStackTrace();
        }


        return a;

    }

    public void Actualizar(View rootView) {

        //        Get listview
        ListView deptos = (ListView) rootView.findViewById(R.id.listView);
        //        get all deptos
        DepartamentoDataSource depto = new DepartamentoDataSource(getActivity().getApplicationContext());

        depto.open();


        ArrayAdapter<Departamento_c> adapter = new ArrayAdapter<Departamento_c>(
                getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,
                depto.getAllDepartamentos());


//        Set adapter to show populated items
        deptos.setAdapter(adapter);

        depto.close();

    }

    public static class update extends Activity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.lay_departamento_edit);

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
                    DepartamentoDataSource depto = new DepartamentoDataSource(getApplicationContext());
                    depto.open();

                    TextView codigo = (TextView) findViewById(R.id.txt_codigo);
                    TextView name = (TextView) findViewById(R.id.txt_name);

                    Departamento_c departamento_c = new Departamento_c();


                    departamento_c.setIddep(Long.parseLong(codigo.getText().toString()));
                    departamento_c.setNombredep(name.getText().toString());

                    depto.updateDepartament(departamento_c);

                    depto.close();

                    Toast.makeText(
                            getApplicationContext(),
                            "Modificado: " + departamento_c.getIddep() + " " +
                                    departamento_c.getNombredep(),
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

            setContentView(R.layout.lay_departamento_add);

            findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DepartamentoDataSource depto = new DepartamentoDataSource(getApplicationContext());
                    depto.open();

                    TextView name = (TextView) findViewById(R.id.txt_name);

                    Departamento_c departamento_c = depto.createDepartamento(name.getText().toString());

                    depto.close();

                    Toast.makeText(
                            getApplicationContext(),
                            "Creado: " + departamento_c.getIddep() + " " +
                                    departamento_c.getNombredep(),
                            Toast.LENGTH_LONG).show();


                    finish();
                }
            });
        }
    }

}
