package sg.edu.rp.c346.id21023028.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    //declare the fields needed
    TextInputEditText ipAmount;
    TextInputEditText ipPax;
    ToggleButton tbSvs;
    ToggleButton tbGst;
    TextInputEditText ipDiscount;
    RadioGroup rgPaymentMethod;
    RadioButton rbPayNow;
    RadioButton rbCash;
    ImageView imPaynow;
    ImageView imCash;
    Button btnSplit;
    Button btnReset;
    LinearLayout llTextDisplayData;
    TextView txtTotalBill;
    TextView txtEachPays;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign the object to variables
        ipAmount = findViewById(R.id.ipAmount);
        ipPax = findViewById(R.id.ipPax);
        ipDiscount = findViewById(R.id.ipDiscount);
        tbSvs = findViewById(R.id.tbSvs);
        tbGst = findViewById(R.id.tbGst);
        rgPaymentMethod = findViewById(R.id.rgPaymentMethod);
        rbPayNow = findViewById(R.id.rbPaynow);
        rbCash = findViewById(R.id.rbCash);
        imPaynow = findViewById(R.id.imgPaynow);
        imCash = findViewById(R.id.imgCash);
        btnSplit = findViewById(R.id.btnSplit);
        btnReset = findViewById(R.id.btnReset);
        llTextDisplayData = findViewById(R.id.txtDisplayData);
        txtTotalBill = findViewById(R.id.txtTotalBill);
        txtEachPays = findViewById(R.id.txtEachPays);

        //intial views setup
        rgPaymentMethod.check(R.id.rbPaynow); //set payment method to paynow for default
        llTextDisplayData.setVisibility(View.INVISIBLE); //invisible at the start, data displayed later
        tbGst.setChecked(true);
        tbSvs.setChecked(true);
        //set the fields to be empty
        ipAmount.setText("");
        ipPax.setText("");
        ipDiscount.setText("");
        txtTotalBill.setText("");
        txtEachPays.setText("");

//Create listeners

    //Listener for pictures (so clicking on them goes for radio group)

        //listener for PayNow
        imPaynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbPayNow.setChecked(true);
            }
        });

        //listener for cash
        imCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbCash.setChecked(true);
            }
        });

//Create Reset option
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intial views setup
                rgPaymentMethod.check(R.id.rbPaynow); //set payment method to paynow for default
                llTextDisplayData.setVisibility(View.INVISIBLE); //invisible at the start, data displayed later
                tbGst.setChecked(true);
                tbSvs.setChecked(true);
                //set the fields to be empty
                ipAmount.setText("");
                ipPax.setText("");
                ipDiscount.setText("");
                txtTotalBill.setText("");
                txtEachPays.setText("");
            }
        });

//Create split option
        btnSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the fields
                String discount = ipDiscount.getText().toString();
                String amount = ipAmount.getText().toString();
                String pax = ipPax.getText().toString();
                int checkedPaymentMethod = rgPaymentMethod.getCheckedRadioButtonId();
                Boolean svs = tbSvs.isChecked();
                Boolean gst = tbGst.isChecked();

                //check if the necessary fields are not empty, else return error
                if (pax.isEmpty() || amount.isEmpty()){ //if either empty

                    if (pax.isEmpty()){
                        ipPax.setError("Field cannot be empty");
                    }

                    if (amount.isEmpty()){
                        ipAmount.setError("Field cannot be empty");
                    }
                }
                else{ //normal operations and calculations

                    //convert the values to correct datatype
                    double amountNo = Double.parseDouble(amount);
                    int paxNo = Integer.parseInt(pax);


                    //check that amountNo and paxNo is more than 0
                    if(amountNo<=0||paxNo<=0){
                        if (amountNo<=0){
                            ipAmount.setError("Invalid Data");
                        }
                        if (paxNo<=0){
                            ipPax.setError("Invalid Data");
                        }


                    }
                    else{ //all ok data

                        //check if there is discount first
                        double discountNo = 0;
                        if (!discount.isEmpty()){
                            discountNo = Double.parseDouble(discount);
                        }

                        //check if there is SVS
                        int svsNo = 0;
                        if (svs){ //if it is checked
                            svsNo = 10;
                        }

                        //check if there is GST
                        int gstNo = 0;
                        if (gst){ //if it is checked
                            gstNo = 7;
                        }

                        //calculation for total bill
                        double onePercent = amountNo/100;
                        double totalBill = (amountNo + (onePercent*svsNo)+(onePercent*gstNo))
                                /100*(100-discountNo);

                        //display total bill
                        txtTotalBill.setText(String.format("$%.2f",totalBill));

                        //display each pays

                        //check which payment method used
                        String msgAdd = "";
                        if (checkedPaymentMethod == R.id.rbPaynow){ //if paynow
                            msgAdd = "via PayNow to 912345678"; //number will be hardcoded for this PS
                        }
                        else{
                            msgAdd = "in cash";
                        }

                        //display msg
                        txtEachPays.setText(String.format("$%.2f %s",totalBill/paxNo,msgAdd));

                        //make msg visible
                        llTextDisplayData.setVisibility(View.VISIBLE);
                    }

                }

            }
        });

    }
}