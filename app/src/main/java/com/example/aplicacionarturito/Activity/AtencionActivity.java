package com.example.aplicacionarturito.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.aplicacionarturito.Adapter.SampleRecyclerViewAdapter;
import com.example.aplicacionarturito.Model.ItemObject;
import com.example.aplicacionarturito.Presenter.PresenterAtencion;
import com.example.aplicacionarturito.Presenter.PresenterLectura;
import com.example.aplicacionarturito.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AtencionActivity extends AppCompatActivity {


    private StaggeredGridLayoutManager layoutManager;

    RecyclerView recyclerfiguras;
    String id,paciente_id;

    private DatabaseReference reference;
    PresenterAtencion presenter;
    ImageView figura;

    String CategoriaId="-N3iTztFqDubpdA0kT3h";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atencion);

        recyclerfiguras=(RecyclerView)findViewById(R.id.recyclerfiguras);
        layoutManager = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager linearLayoutManager2 = new  LinearLayoutManager(this);

        recyclerfiguras.setLayoutManager(layoutManager);

        List<ItemObject> sList = getListItemData();
        SampleRecyclerViewAdapter rcAdapter = new SampleRecyclerViewAdapter(
                AtencionActivity.this, sList);
        recyclerfiguras.setAdapter(rcAdapter);


        inpust();
        id=getIntent().getStringExtra("id");
        paciente_id=getIntent().getStringExtra("paciente_id");
        reference= FirebaseDatabase.getInstance().getReference();
        presenter= new PresenterAtencion(this,reference,paciente_id);


    }

    private void inpust() {
        figura=(ImageView) findViewById(R.id.figura);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (id!=""){
            viewAtencion(id);
        }
    }
    private void viewAtencion(String id) {
        presenter.ViewAtencion(value -> {
            if (value.getFigura().equals("triangulo")){
                figura.setImageResource(R.drawable.trianguloapp);
            }
            else if (value.getFigura().equals("rectangulo")){
                figura.setImageResource(R.drawable.rectaguloapp);
            }else if (value.getFigura().equals("circulo")){
                figura.setImageResource(R.drawable.circuloapp);
            }else if (value.getFigura().equals("rombo")) {
                figura.setImageResource(R.drawable.romboapp);
            }
            else if (value.getFigura().equals("cuadrado")) {
                figura.setImageResource(R.drawable.cuadradoapp);
            }

        }, CategoriaId,id);
    }



    private List<ItemObject> getListItemData()
    {
        List<ItemObject> listViewItems = new ArrayList<ItemObject>();
        listViewItems.add(new ItemObject("1984", "George Orwell","cuadrado"));
        listViewItems.add(new ItemObject("Pride and Prejudice", "Jane Austen","rectangulo"));
        listViewItems.add(new ItemObject("One Hundred Years of Solitude", "Gabriel Garcia Marquez","rombo"));
        listViewItems.add(new ItemObject("The Book Thief", "Markus Zusak","circulo"));
        listViewItems.add(new ItemObject("The Hunger Games", "Suzanne Collins","triangulo"));
        listViewItems.add(new ItemObject("The Hitchhiker's Guide to the Galaxy", "Douglas Adams","cuadradp"));
        listViewItems.add(new ItemObject("The Theory Of Everything", "Dr Stephen Hawking","rombo"));
        listViewItems.add(new ItemObject("One Hundred Years of Solitude", "Gabriel Garcia Marquez","rombo"));
        listViewItems.add(new ItemObject("The Book Thief", "Markus Zusak","circulo"));
        listViewItems.add(new ItemObject("The Hunger Games", "Suzanne Collins","triangulo"));
        listViewItems.add(new ItemObject("The Hitchhiker's Guide to the Galaxy", "Douglas Adams","cuadradp"));
        listViewItems.add(new ItemObject("The Theory Of Everything", "Dr Stephen Hawking","rombo"));
        listViewItems.add(new ItemObject("1984", "George Orwell","cuadrado"));
        listViewItems.add(new ItemObject("Pride and Prejudice", "Jane Austen","rectangulo"));
        listViewItems.add(new ItemObject("The Book Thief", "Markus Zusak","circulo"));
        listViewItems.add(new ItemObject("The Hunger Games", "Suzanne Collins","triangulo"));
        listViewItems.add(new ItemObject("The Hitchhiker's Guide to the Galaxy", "Douglas Adams","cuadradp"));
        listViewItems.add(new ItemObject("The Theory Of Everything", "Dr Stephen Hawking","rombo"));
        listViewItems.add(new ItemObject("The Book Thief", "Markus Zusak","circulo"));
        listViewItems.add(new ItemObject("The Hunger Games", "Suzanne Collins","triangulo"));
        listViewItems.add(new ItemObject("The Hunger Games", "Suzanne Collins","triangulo"));
        listViewItems.add(new ItemObject("The Hitchhiker's Guide to the Galaxy", "Douglas Adams","cuadradp"));
        listViewItems.add(new ItemObject("The Theory Of Everything", "Dr Stephen Hawking","rombo"));
        listViewItems.add(new ItemObject("1984", "George Orwell","cuadrado"));
        listViewItems.add(new ItemObject("The Hunger Games", "Suzanne Collins","triangulo"));
        listViewItems.add(new ItemObject("The Hitchhiker's Guide to the Galaxy", "Douglas Adams","cuadradp"));
        listViewItems.add(new ItemObject("The Theory Of Everything", "Dr Stephen Hawking","rombo"));
        listViewItems.add(new ItemObject("1984", "George Orwell","cuadrado"));
        listViewItems.add(new ItemObject("The Hitchhiker's Guide to the Galaxy", "Douglas Adams","cuadradp"));
        listViewItems.add(new ItemObject("The Theory Of Everything", "Dr Stephen Hawking","rombo"));
        listViewItems.add(new ItemObject("1984", "George Orwell","cuadrado"));
        return listViewItems;
    }
}

