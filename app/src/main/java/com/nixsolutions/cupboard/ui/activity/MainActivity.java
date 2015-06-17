package com.nixsolutions.cupboard.ui.activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nixsolutions.cupboard.R;
import com.nixsolutions.cupboard.database.ContentDescriptor;
import com.nixsolutions.cupboard.database.CupboardContentProvider;
import com.nixsolutions.cupboard.entities.Device;
import com.nixsolutions.cupboard.entities.Family;
import com.nixsolutions.cupboard.entities.Human;
import com.nixsolutions.cupboard.entities.HumanDeviceUnion;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import nl.qbusict.cupboard.CupboardFactory;
import nl.qbusict.cupboard.ProviderCompartment;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    public static final String FORMAT = "%d --- %s, %s : %s \n";

    @InjectView(R.id.hId)
    EditText humId;

    @InjectView(R.id.dNmae)
    EditText dNmae;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        findViewById(R.id.button).setOnClickListener(this);

        printHumans(this);
    }

    @OnClick(R.id.print)
    public void print() {
        printHumans(this);
    }

    public static void printHumans(Context context) {
        ProviderCompartment compartment = CupboardFactory.getInstance().withContext(context);

        List<Human> humans = compartment.query(
                ContentDescriptor.getUri(Human.class),
                Human.class).list();

        StringBuilder stringBuilder = new StringBuilder(humans.size() + " \n");
        for (Human hum : humans) {
            stringBuilder.append(String.format(FORMAT, hum._id, hum.name, hum.secondName, hum.address));
        }

        stringBuilder.append(" \nDevices\n");

        List<Device> devices = compartment.query(ContentDescriptor.getUri(Device.class), Device.class).list();
        for (Device device : devices) {
            stringBuilder.append(device.humanId + " : " + device.deviceName + "\n");
        }

        printText(context, stringBuilder.toString());
    }

    private static void printText(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {
        Human human = new Human();
        human.name = getTextz(R.id.name);
        human.secondName = getTextz(R.id.seconname);
        human.address = getTextz(R.id.adress);

        Family family = new Family();
        family.first = human;

        CupboardContentProvider.DBHelper helper = new CupboardContentProvider.DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();


        CupboardFactory.getInstance().withContext(this).put(ContentDescriptor.getUri(Family.class), family);

        CupboardFactory.getInstance().withContext(this).put(ContentDescriptor.getUri(Human.class), human);
        printHumans(this);
    }

    private String getTextz(int res) {
        return ((EditText) findViewById(res)).getText().toString();
    }


    @OnClick(R.id.Add_device)
    public void addDevice() {
        Device device = new Device();

        device.humanId = Long.valueOf(humId.getText().toString());
        device.deviceName = dNmae.getText().toString();

        CupboardFactory.cupboard().withContext(this).put(ContentDescriptor.getUri(Device.class), device);
    }

    @OnClick(R.id.complex)
    public void complexSelection() {
        List<HumanDeviceUnion> list = CupboardFactory.getInstance().withContext(this).query(
                ContentDescriptor.getUri(HumanDeviceUnion.class),
                HumanDeviceUnion.class)
                .list();

        printText(this, list.size() + "");
    }
}
