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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.aplicacionarturito.Model.Paciente;
import com.example.aplicacionarturito.Presenter.PresenterFamiliar;
import com.example.aplicacionarturito.Presenter.PresenterHorario;
import com.example.aplicacionarturito.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class ViewPagerActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        reference= FirebaseDatabase.getInstance().getReference();
        presenter= new PresenterFamiliar(this,reference,user_id);

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
        int current = getItem(1);
        if (current < layouts.length){
            viewPager.setCurrentItem(current);
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
                            int edad =anioctual-anioelegico;
                            EditText tvedadpacinte=findViewById(R.id.tvedadpacinte);
                            tvedadpacinte.setText(edad+"");
                           // store(fecha,tgl_daftar_date.toString(),id);
                        }
                    },anio, mes, dia);
                    recogerFecha.show();
                }
            });
        } else {
            launchHomeScreen();
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



    private void launchHomeScreen() {
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

        btnNext.setText(getString(R.string.next));

        SharedPreferences sharedPreferences = getSharedPreferences("PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putInt("INTRO", 1);
        editor.apply();

        presenter.save(paciente);
        Intent intent = new Intent(this, PsicologosActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("paciente",paciente);
//        intent.putExtras(bundle);
        finish();
        //startActivity(intent);
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