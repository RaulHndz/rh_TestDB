package com.raulh.miempleo_final.testdb;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.raulh.miempleo_final.R;
import com.raulh.miempleo_final.dao.SolicitanteDataSource;
import com.raulh.miempleo_final.models.Solicitante_c;

import java.util.Calendar;
import java.util.List;

/**
 * Created by raulh on 05/11/2014.
 */
public class Solicitante extends Fragment {


    private static TextView txtBirthdate;
    private static DatePickerDialog datePickerDialog;
    private static int day, month, year;
    private static RadioGroup rdoGrpGender;
    private static String gender = "Masculino";


    public long idsolic;
    public String nombre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.lay_solicitante, container, false);

        Button but = (Button) rootView.findViewById(R.id.btn_Agregar);
        Button but2 = (Button) rootView.findViewById(R.id.btn_Actualizar);
        final ListView lvDatos = (ListView) rootView.findViewById(R.id.listView);

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Actualizar(rootView);
            }
        });

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), Solicitante.create.class);
                startActivity(i);

            }
        });

        lvDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                idsolic = ObtenerId(position);
                nombre = ObtenerName(position);

                try {

                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(getActivity());

                    builder.setMessage("Codigo: " + idsolic + "\n Nombre: " + nombre)
                            .setTitle("Mi Empleo - Solicitantes")
                            .setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    Toast.makeText(
                                            getActivity().getApplicationContext(),
                                            "::: Opcion en Desarrollo :::",
                                            Toast.LENGTH_LONG).show();
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    SolicitanteDataSource solic = new SolicitanteDataSource(getActivity().getApplicationContext());
                                    solic.open();
                                    solic.deleteSolicitante(idsolic);
                                    solic.close();
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

    public String ObtenerName(int position) {

        String a = "";
        try {


            SolicitanteDataSource solic = new SolicitanteDataSource(getActivity().getApplicationContext());
            solic.open();

            List<Solicitante_c> listSolic = solic.getAllSolicitante();

            a = listSolic.get(position).getNombresolic();
            solic.close();

        } catch (Throwable e) {
            e.printStackTrace();
        }


        return a;

    }

    public long ObtenerId(int position) {

        long a = 0;
        try {


            SolicitanteDataSource solic = new SolicitanteDataSource(getActivity().getApplicationContext());
            solic.open();

            List<Solicitante_c> listSolic = solic.getAllSolicitante();

            a = listSolic.get(position).getIdsoli();
            solic.close();

        } catch (Throwable e) {
            e.printStackTrace();
        }


        return a;

    }

    @Override
    public void onResume() {


        Actualizar(getActivity().findViewById(R.id.listView));

        super.onResume();
    }

    public void Actualizar(View rootView) {

        //        Get listview
        ListView solic = (ListView) rootView.findViewById(R.id.listView);
        //        get all deptos
        SolicitanteDataSource solicitante = new SolicitanteDataSource(getActivity().getApplicationContext());

        solicitante.open();


        ArrayAdapter<Solicitante_c> adapter = new ArrayAdapter<Solicitante_c>(
                getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,
                solicitante.getAllSolicitante());


//        Set adapter to show populated items
        solic.setAdapter(adapter);

        solicitante.close();

    }

    public static class create extends Activity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.lay_solicitante_add);


            Button btnSetBirthDate = (Button) findViewById(R.id.btnSetBirthdate);
            rdoGrpGender = (RadioGroup) findViewById(R.id.radioGrpGender);
            txtBirthdate = (TextView) findViewById(R.id.txtBirthdate);

            rdoGrpGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i) {
                        case R.id.rbt_masculino:
                            gender = "Masculino";
                            break;
                        case R.id.rbt_femenino:
                            gender = "Femenino";
                            break;
                        default:
                            gender = "Masculino";
                            break;
                    }
                }
            });

            btnSetBirthDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    datePickerDialog.show();
                }
            });


            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            txtBirthdate.setText(dayOfMonth + "-"
                                    + (monthOfYear + 1) + "-" + year);

                        }
                    }, year, month, day);


            findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {



                        TextView nombre = (TextView) findViewById(R.id.txt_name);
                        TextView apellido = (TextView) findViewById(R.id.txt_apellido);
                        TextView telefono = (TextView) findViewById(R.id.txt_telefono);
                        TextView nivel = (TextView) findViewById(R.id.txtnivelacademico);
                        TextView tipo = (TextView) findViewById(R.id.txttipodoc);
                        TextView municipio = (TextView) findViewById(R.id.txtmunicipio);



                        SolicitanteDataSource solic = new SolicitanteDataSource(getApplicationContext());
                        solic.open();

                        Solicitante_c solicitante_c = solic.createSolicitante(  nombre.getText().toString(),
                                                                                apellido.getText().toString(),
                                                                                txtBirthdate.getText().toString(),
                                                                                telefono.getText().toString(),
                                                                                gender,
                                                                                tipo.getText().toString(),
                                                                                municipio.getText().toString(),
                                                                                nivel.getText().toString()
                        );

                        solic.close();

                        Toast.makeText(
                                getApplicationContext(),
                                "Creado: " + solicitante_c.getIdsoli() + " " +
                                        solicitante_c.getNombresolic(),
                                Toast.LENGTH_LONG).show();


                        finish();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }



                }
            });


        }
    }

}
