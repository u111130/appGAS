package com.dpm2020.appgas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dpm2020.appgas.network.service.PedidoService;
import com.dpm2020.appgas.network.service.RegistroService;
import com.dpm2020.appgas.network.service.TarjetaService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class MetodoPagoActivity extends BaseActivity {

    MetodoPagoActivity activity;
    TarjetaService service;

    Button btnPagarMetodo;
    EditText txtNumeroTarjeta, txtTitular, txtVencimMM, txtVencimAA, txtCodigoTarjeta;
    ImageView imgTarjeta;

    TextView tvPagoEfectivo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_metodo_pago);

        activity = this;
        service = new TarjetaService(activity);


        btnPagarMetodo = findViewById(R.id.btnPagarMetodo);
        txtNumeroTarjeta = findViewById(R.id.txtNumeroTarjeta);
        txtTitular = findViewById(R.id.txtTitular);
        txtVencimMM = findViewById(R.id.txtVencimMM);
        txtVencimAA = findViewById(R.id.txtVencimAA);
        txtCodigoTarjeta = (EditText) findViewById(R.id.txtCodigoTarjeta);
        imgTarjeta = findViewById(R.id.imageView11);
        tvPagoEfectivo = findViewById(R.id.tvPagoEfectivo);

        final TextView title = findViewById(R.id.tvUsuario);
        title.setText(getSaludo());


        Intent intentFrom = getIntent();
        Double stotal = intentFrom.getDoubleExtra("total",0 );
        btnPagarMetodo.setText("Pagar S/. " + String.valueOf(stotal));


        btnPagarMetodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PagarTC();
            }
        });

        tvPagoEfectivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PagarEFE();
            }
        });



        txtCodigoTarjeta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View strScoreView, boolean hasFocus) {
                if (hasFocus){
                    imgTarjeta.setImageDrawable(getResources().getDrawable(R.drawable.tarjeta2));
                } else {
                    imgTarjeta.setImageDrawable(getResources().getDrawable(R.drawable.tarjetacredito));
                }
            }
        });

        txtVencimMM.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String mes = txtVencimMM.getText().toString();
                if (!mes.equalsIgnoreCase("") && mes.length()>1)
                {
                    int m = Integer.valueOf(mes);
                    if (!(m > 0 && m<13))
                    {
                       txtVencimMM.setText("");
                    }
                }
            }
        });


        txtNumeroTarjeta.requestFocus();
    }

    private void PagarTC(){

        String msj = validaDatos();
        if (msj.equals("")) {
            String numero, titular, vctoMM, vctoAA, codigo;

            numero = txtNumeroTarjeta.getText().toString();
            titular = txtTitular.getText().toString();
            vctoMM = txtVencimMM.getText().toString();
            vctoAA = txtVencimAA.getText().toString();
            codigo = txtCodigoTarjeta.getText().toString();

            int resultTC = 1;
            //TODO: Enviar pago a VISA


            if (resultTC == 1) {
                // Retornó pago exitoso.
                String token2="2883599064508077J";
                String sCardID = "";
                //sCardID = service.registrar(numero, token2);

                //retorna a la actividad de Pedido: "metodo" y "cardId"
                Intent intentWithResult = new Intent();
                intentWithResult.putExtra("metodo", "TAR");
                intentWithResult.putExtra("cardId", sCardID);
                setResult(RESULT_OK, intentWithResult);
                finish();

            } else {
                muestraMensaje("Pago", "Error en el pago con tarjeta.");
            }

        }else{
            muestraMensaje("Validación", msj);
        }


    }


    private void PagarEFE(){

        String sCardID = "";

        //retorna a la actividad de Pedido: "metodo" y "cardId"
        Intent intentWithResult = new Intent();
        intentWithResult.putExtra("metodo", "EFE");
        intentWithResult.putExtra("cardId", sCardID);
        setResult(RESULT_OK, intentWithResult);
        finish();

    }

    private String validaDatos(){
        String msj = "";
        DateFormat df = new SimpleDateFormat("yyMM");
        String mesActual = df.format(new Date());

        if (txtNumeroTarjeta.getText().toString().equals("")){
            msj += "Ingrese el número de tarjeta de crédito \n";
        }

        if (txtTitular.getText().toString().equals("")){
            msj += "Ingrese el nombre del Titular de la tarjeta \n";
        }

        if (txtVencimMM.getText().toString().equals("")){
            msj += "Ingrese el mes de vencimiento de la tarjeta \n";
        }

        if (txtVencimAA.getText().toString().equals("")){
            msj += "Ingrese el año de vencimiento de la tarjeta \n";
        }

        String mestar = txtVencimAA.getText().toString() + txtVencimMM.getText().toString();
        if ( Integer.valueOf(mestar) < Integer.valueOf(mesActual) ){
            msj += "La fecha de vencimiento debe ser mayor a la fecha actual. \n";
        }

        if (txtCodigoTarjeta.getText().toString().equals("")){
            msj += "Ingrese el código de seguridad de la tarjeta \n";
        }

        return msj;
    }

    public void muestraMensaje(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(mensaje)
                .setTitle(titulo)
                .setCancelable(false)
                .setNeutralButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }




/*
    Integer[] imageArray = { R.drawable.visa, R.drawable.mastercard, R.drawable.amex };

    final String[] cardtype = new String[1];

    public static ArrayList<String> listOfPattern()
    {
        ArrayList<String> listOfPattern=new ArrayList<String>();

        String ptVisa = "^4[0-9]$";

        listOfPattern.add(ptVisa);

        String ptMasterCard = "^5[1-5]$";

        listOfPattern.add(ptMasterCard);

        String ptAmeExp = "^3[47]$";

        listOfPattern.add(ptAmeExp);

        return listOfPattern;
    }

        txtNumeroTarjeta.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                String ccNum = s.toString();

                if(ccNum.length()>=2)
                {
                    for (int i = 0; i < listOfPattern().size(); i++)
                    {
                        if (ccNum.substring(0, 2).matches(listOfPattern().get(i)))
                        {
                            txtNumeroTarjeta.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageArray[i], 0);

                            cardtype[0] = String.valueOf(i);
                        }
                    }
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s)
            {

                if (!txtNumeroTarjeta.getText().toString().equalsIgnoreCase(""))
                {
                    for (int i = 0; i < listOfPattern().size(); i++)
                    {
                        if (txtNumeroTarjeta.getText().toString().matches(listOfPattern().get(i)))
                        {
                            txtNumeroTarjeta.setCompoundDrawablesWithIntrinsicBounds(0, 0, imageArray[i], 0);

                            cardtype[0] = String.valueOf(i);
                        }
                    }
                }
                else
                {
                    txtNumeroTarjeta.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.visa, 0);
                }
            }
        });
    */
}