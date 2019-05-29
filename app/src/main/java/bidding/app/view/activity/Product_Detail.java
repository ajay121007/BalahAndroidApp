package bidding.app.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import bidding.app.R;

public class  Product_Detail extends BaseActivity {

    TextView plus_text,minus_text,qantity_text,addcart_text,productprice_text,productname_text;
    int qantityvalue =1;
    double totalprice,singleprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_deatil);
        initviews();
    }

    private void initviews(){
        plus_text =(TextView)findViewById(R.id.textplus);
        minus_text =(TextView)findViewById(R.id.textminus);
        qantity_text =(TextView)findViewById(R.id.text_qantity);
        addcart_text =(TextView)findViewById(R.id.textadd_cart);
        minus_text =(TextView)findViewById(R.id.textminus);
        productprice_text=(TextView)findViewById(R.id.textproduct_price);
        productname_text =(TextView)findViewById(R.id.text_productname);
        productname_text.setText("الاسم الكامل");
        qantity_text.setText(qantityvalue+"");
        productprice_text.setText("10.00");
        addcart_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Product_Detail.this, getResources().getString(R.string.addtocart), Toast.LENGTH_SHORT).show();
            }
        });



        totalprice = Double.parseDouble(productprice_text.getText().toString());
        singleprice = Double.parseDouble(productprice_text.getText().toString());



        plus_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                qantityvalue =qantityvalue+1;
                qantity_text.setText(qantityvalue+"");
                totalprice = totalprice+singleprice;
                productprice_text.setText(totalprice+"");


            }
        });

        minus_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qantityvalue>1) {

                    qantityvalue = qantityvalue -1;
                    qantity_text.setText(qantityvalue + "");
                    totalprice = totalprice -singleprice;
                    productprice_text.setText(totalprice + "");

                }

            }
        });



    }
}
