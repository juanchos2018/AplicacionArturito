package com.example.aplicacionarturito.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.aplicacionarturito.Interface.InterfaceDialog;
import com.example.aplicacionarturito.Model.Paciente;
import com.example.aplicacionarturito.Presenter.PresenterFamiliar;
import com.example.aplicacionarturito.Presenter.PresenterHorario;
import com.example.aplicacionarturito.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class ViewPagerActivity extends AppCompatActivity implements InterfaceDialog {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    String user_id;
    private FirebaseAuth mAuth;
    PresenterFamiliar presenter;
    private DatabaseReference reference;
    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    private static final String CERO = "0";
    private static final String BARRA = "-";
    Date tgl_daftar_date;

    int position_page=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        reference= FirebaseDatabase.getInstance().getReference();
        presenter= new PresenterFamiliar(this,reference,user_id,this);

        viewPager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layoutDots);
        btnSkip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);

        layouts = new int[]{
                R.layout.layout_solicitar,
                R.layout.layout_datos,
                R.layout.layout_registrar};

        addBottomDots(0);

        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

    }

    public  void btnNextClick(View v){
        String tipo = null;
        if (position_page==0){

            EditText tvttexto=(EditText) findViewById(R.id.tvttexto);
            RadioGroup radioGrupoTipoCaso = findViewById(R.id.radioGrupoTipoCaso);
            int checkgfatiga = radioGrupoTipoCaso.getCheckedRadioButtonId();
            if (checkgfatiga==-1 ){
                tipo="";
            }
            else {
                if (  checkgfatiga == R.id.rbdfamilia  ){
                    tipo="Propio";
                }
                if (checkgfatiga == R.id.rbdtio){
                    tipo="Familiar";
                }
                if (checkgfatiga == R.id.rbdapoderado){
                    tipo="Conocido";
                }
                if (checkgfatiga == R.id.rbdotro){
                    tipo="Conocido";
                }
            }
           // String text=tvttexto.getText().toString();
            if (TextUtils.isEmpty(tipo)){
                //tvttexto.setError("campo necesario");
                Toast.makeText(this, "Seleccione una opcion ", Toast.LENGTH_SHORT).show();
            }else{
                int current = getItem(1);
                position_page=current;
                viewPager.setCurrentItem(current);
            }
        }
        else if (position_page==1){
            EditText tvcontacto=findViewById(R.id.tvcontacto);
            EditText tvcelular=findViewById(R.id.tvcelular);
            EditText tvdni=findViewById(R.id.tvdni);
            String dni =tvdni.getText().toString();
            String con =tvcontacto.getText().toString();
            String cel =tvcelular.getText().toString();
            if (TextUtils.isEmpty(con)){
                tvcontacto.setError("campo necesario");
            }
            else if (TextUtils.isEmpty(cel)){
                tvcelular.setError("campo necesario");
            }
            else if (TextUtils.isEmpty(dni)){
                tvdni.setError("campo necesario");
            }
            else{
                int current = getItem(1);
                position_page=current;
                viewPager.setCurrentItem(current);
            }
        }
        else if (position_page==2){
            EditText tvnombrepaciente=findViewById(R.id.tvnombrepaciente);
            EditText tvapellidopaciente=findViewById(R.id.tvapellidopaciente);
            EditText tvedadpacinte=findViewById(R.id.tvedadpacinte);
            EditText tvfechapaciente=findViewById(R.id.tvfechapaciente);
            EditText tvlnacimientopaciente=findViewById(R.id.tvlnacimientopaciente);
            EditText tvgradopaciente=findViewById(R.id.tvgradopaciente);

            String campo1=tvnombrepaciente.getText().toString();
            String campo2=tvapellidopaciente.getText().toString();
            String campo3=tvedadpacinte.getText().toString();
            String campo4=tvfechapaciente.getText().toString();
            String campo5=tvlnacimientopaciente.getText().toString();
            String campo6=tvgradopaciente.getText().toString();



            ImageButton imgfecha = findViewById(R.id.imgfecha);
            imgfecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog recogerFecha = new DatePickerDialog(ViewPagerActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            final int mesActual = month + 1;
                            c.set(year, month, dayOfMonth);
                            String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                            String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);

                            tgl_daftar_date = c.getTime();
                            String fecha =diaFormateado + BARRA + mesFormateado + BARRA + year;
                            EditText tvfechapaciente=findViewById(R.id.tvfechapaciente);
                            tvfechapaciente.setText(fecha);
                            String curren_date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                            String []arracurer=curren_date.split("-");
                            int anioctual =Integer.parseInt(arracurer[0]);
                            int anioelegico=year;
                            if (anioctual>6){
                                Toast.makeText(ViewPagerActivity.this, "la edad no califica para este test", Toast.LENGTH_SHORT).show();
                            }
                            int edad =anioctual-anioelegico;
                            EditText tvedadpacinte=findViewById(R.id.tvedadpacinte);
                            tvedadpacinte.setText(edad+"");
                            // store(fecha,tgl_daftar_date.toString(),id);
                        }
                    },anio, mes, dia);
                    recogerFecha.show();
                }
            });


            if (TextUtils.isEmpty(campo1)){
                tvnombrepaciente.setError("campo necesario");
            }
            else if (TextUtils.isEmpty(campo2)){
                tvnombrepaciente.setError("campo necesario");
            }
            else if (TextUtils.isEmpty(campo3)){
                tvedadpacinte.setError("campo necesario");
            }
            else if (TextUtils.isEmpty(campo4)){
                tvfechapaciente.setError("campo necesario");
            }
            else if (TextUtils.isEmpty(campo5)){
                tvlnacimientopaciente.setError("campo necesario");
            }
            else if (TextUtils.isEmpty(campo6)){
                tvgradopaciente.setError("campo necesario");
            }else{
                int current = getItem(1);
                position_page=current;
                viewPager.setCurrentItem(current);
            }

        }else if (position_page==3){
            EditText tvedadpacinte=findViewById(R.id.tvedadpacinte);
            int edad =Integer.parseInt(tvedadpacinte.getText().toString());
            if (edad>6){
                Toast.makeText(this, "la edad sobrepasa ", Toast.LENGTH_SHORT).show();
            }else{
                launchHomeScreen(tipo);
            }
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

    };


    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.dot_inactive));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.dot_active));
       }

    private void launchHomeScreen(String tipo) {
        Paciente paciente = new Paciente();

        EditText tvnombrepaciente=findViewById(R.id.tvnombrepaciente);
        EditText tvapellidopaciente=findViewById(R.id.tvapellidopaciente);
        EditText tvedadpacinte=findViewById(R.id.tvedadpacinte);
        EditText tvfechapaciente=findViewById(R.id.tvfechapaciente);
        EditText tvlnacimientopaciente=findViewById(R.id.tvlnacimientopaciente);
        EditText tvgradopaciente=findViewById(R.id.tvgradopaciente);

        EditText tvcontacto=findViewById(R.id.tvcontacto);
        EditText tvcelular=findViewById(R.id.tvcelular);
        EditText tvdni=findViewById(R.id.tvdni);

        String nombrePaciente=tvnombrepaciente.getText().toString();
        String apellidopaciente=tvapellidopaciente.getText().toString();
        String edadpaciente=tvedadpacinte.getText().toString();
        String fechapaciente=tvfechapaciente.getText().toString();
        String naicmientopaciente=tvlnacimientopaciente.getText().toString();
        String gradopaciente=tvgradopaciente.getText().toString();

        String contacto=tvcontacto.getText().toString();
        String celuar=tvcelular.getText().toString();
        String dni=tvdni.getText().toString();

        paciente.setNombre(nombrePaciente);
        paciente.setApellidos(apellidopaciente);
        paciente.setEdad(edadpaciente);
        paciente.setFecha_nacimiento(fechapaciente);
        paciente.setLugar_nacimiento(naicmientopaciente);
        paciente.setGrado_institucion(gradopaciente);

        paciente.setContacto(contacto);
        paciente.setCelular(celuar);
        paciente.setDni(dni);
        paciente.setTipo(tipo);
        btnNext.setText(getString(R.string.next));
        presenter.save(paciente);
        finish();
        //startActivity(intent);
    }

    @Override
    public void oncallbackPaciente(String paciente_id) {

    }

    @Override
    public Context getContext2() {
        return this;
    }


    public class ViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public ViewPagerAdapter() {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}