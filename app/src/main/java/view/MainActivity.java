package view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.isabella.parcial1.R;

import communication.OnMessageListener;
import communication.TCP_Singleton;
import model.Delete;
import model.Group;

public class MainActivity extends AppCompatActivity implements OnMessageListener, View.OnClickListener {

    private TCP_Singleton tcp;
    private EditText name,number,x,y;
    private Button rojo,verde,azul,crear,borrar;
    private int color=0;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tcp = TCP_Singleton.getInstance();
        gson = new Gson();
        tcp.SetObserver(this);
        name= findViewById(R.id.etGroupName);
        number= findViewById(R.id.etParticleNumber);
        x = findViewById(R.id.etPosX);
        y = findViewById(R.id.etPosY);
        rojo = findViewById(R.id.buttonrojo);
        verde = findViewById(R.id.buttonverde);
        azul = findViewById(R.id.buttonazul);
        crear = findViewById(R.id.btnCreate);
        borrar = findViewById(R.id.btnDelete);
        rojo.setOnClickListener(this);
        verde.setOnClickListener(this);
        azul.setOnClickListener(this);
        crear.setOnClickListener(this);
        borrar.setOnClickListener(this);


    }

    @Override
    public void OnMessage(String msg) {

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.btnCreate:

                if(ValidateInfo()){

                    int numberParticle = Integer.parseInt(number.getText().toString());
                    int posx = Integer.parseInt(x.getText().toString());
                    int posy = Integer.parseInt(x.getText().toString());
                    Group tempGroup = new Group(name.getText().toString(),color,numberParticle,posx,posy);

                    String msg = gson.toJson(tempGroup);
                    tcp.SendMessage(msg);

                }

                else{

                    Toast.makeText(this, "por favor compruebe la información", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.buttonrojo:

                color=1;
                break;
            case R.id.buttonverde:

                color=2;
                break;

            case R.id.buttonazul:

                color=3;
                break;

            case R.id.btnDelete:

                Delete delete = new Delete();
                String msg =gson.toJson(delete);
                tcp.SendMessage(msg);

        }
    }

    public boolean ValidateInfo(){

        if(name.getText().toString().isEmpty()||number.getText().toString().isEmpty()
        ||x.getText().toString().isEmpty()||y.getText().toString().isEmpty()||color==0
        ){

            return false;
        }
        else{
            return true;
        }

    }
}