package bidding.app.view.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bidding.app.R;
import bidding.app.extra.CSPreferences;
import bidding.app.model.CardDetailsBean;
import bidding.app.extra.Config;
import bidding.app.extra.Constants;
import bidding.app.extra.Event;
import bidding.app.extra.Utils;

/**
 * Created by Thakur on 10/24/2017.
 */

public class CardAddAdapter extends RecyclerView.Adapter<CardAddAdapter.ViewHolder> {

    private List<CardDetailsBean.Datum> response;
    private static LayoutInflater inflater = null;
    String TAG = CardAddAdapter.class.getSimpleName();
    Activity activity;
    View view;
    String card_type = "";
    int selectedCard;

    public CardAddAdapter(Activity activity, List<CardDetailsBean.Datum> datumList) {
        this.response = datumList;
        this.activity = activity;
    }


    @Override
    public CardAddAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_adapter_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardAddAdapter.ViewHolder holder, final int position) {
        holder.rd_btn.setChecked(false);
        card_type = response.get(position).getCardType();
        if (!CSPreferences.readString(activity, Constants.SELECTEDCARD).isEmpty() && CSPreferences.readString(activity, Constants.SELECTEDCARD) != null) {
            selectedCard = Integer.parseInt(CSPreferences.readString(activity, Constants.SELECTEDCARD));
            if (selectedCard <= response.size() - 1 && selectedCard == position) {
                holder.rd_btn.setChecked(true);
            }
        }
        holder.cardNumber.setText(response.get(position).getCardType() + "****" + response.get(position).getCardNumber().substring(response.get(position).getCardNumber().length() - 4));
        holder.expiry.setText(response.get(position).getExMonth() + "/" + response.get(position).getExYear());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCardApi(position, response.get(position).getCardNumber());
            }
        });
        holder.mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectUnselect(holder, position, response.get(position).getCardNumber());
            }
        });

        Log.e("CARD", card_type);
        if (card_type.equalsIgnoreCase("Visa")) {
            holder.img.setImageResource(R.drawable.ic_icon_visa_card);
        } else if (card_type.equalsIgnoreCase("American Express")) {
            holder.img.setImageResource(R.drawable.ic_icon_amex);
        } else if (card_type.equalsIgnoreCase("MasterCard")) {
            holder.img.setImageResource(R.drawable.ic_icon_visa_card);
        } else if (card_type.equalsIgnoreCase("Discover")) {
            holder.img.setImageResource(R.drawable.ic_icon_discover);
        } else if (card_type.equalsIgnoreCase("JCB")) {
            holder.img.setImageResource(R.drawable.ic_icon_jcb);
        } else if (card_type.equalsIgnoreCase("Diners Club")) {
            holder.img.setImageResource(R.drawable.ic_icon_dinersclub);
        } else if (card_type.equalsIgnoreCase("Unknown")) {
            holder.img.setImageResource(R.drawable.ic_icon_unknown);
        }

    }

    private void selectUnselect(ViewHolder holder, int position, String cardNumber) {

        if (holder.rd_btn.isChecked()) {
            holder.rd_btn.setChecked(false);
            EventBus.getDefault().post(new Event(Constants.DEFAULTCARD, ""));
            CSPreferences.putString(activity, Constants.SELECTEDCARD, "");
            CSPreferences.putString(activity, Constants.SELECTEDCARDNUMBER, "");
        } else {
            defaultCard(holder, position, cardNumber);
        }

    }

    private void defaultCard(final ViewHolder holder, final int position, final String cardNumber) {
        Utils.showProgressDialog(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Utils.hideProgressDialog();
                        Log.d("Response", response + "");
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject mainResponse = jsonObject.getJSONObject("response");
                            if (mainResponse.getString("status").equalsIgnoreCase("1")) {
                                holder.rd_btn.setChecked(true);
                                Toast.makeText(activity, mainResponse.getString("message"), Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new Event(Constants.DEFAULTCARD, ""));
                                CSPreferences.putString(activity, Constants.SELECTEDCARD, String.valueOf(position));
                                CSPreferences.putString(activity, Constants.SELECTEDCARDNUMBER, cardNumber);
                            } else {
                                Toast.makeText(activity, mainResponse.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.hideProgressDialog();
                Toast.makeText(activity, error.getMessage() + "", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "selectCard");
                params.put("card_number", cardNumber);
                Log.d("Params", params + "");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    private void deleteCardApi(final int position, final String cardNumber) {
        Utils.showProgressDialog(activity);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.BASE_URI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Utils.hideProgressDialog();
                        Log.d("Response", response + "");
                        try {
                            if (!CSPreferences.readString(activity, Constants.SELECTEDCARD).isEmpty() && CSPreferences.readString(activity, Constants.SELECTEDCARD) != null) {
                                if (Integer.parseInt(CSPreferences.readString(activity, Constants.SELECTEDCARD)) == (position)) {
                                    CSPreferences.putString(activity, Constants.SELECTEDCARD, "");
                                }
                            }
                            if (!CSPreferences.readString(activity, Constants.SELECTEDCARDNUMBER).isEmpty() && CSPreferences.readString(activity, Constants.SELECTEDCARDNUMBER) != null) {
                                Log.e("Cards", "  " + CSPreferences.readString(activity, Constants.SELECTEDCARD) + " ...... " + cardNumber);
                                if (CSPreferences.readString(activity, Constants.SELECTEDCARDNUMBER).equalsIgnoreCase(cardNumber)) {
                                    CSPreferences.putString(activity, Constants.SELECTEDCARDNUMBER, "");
                                }
                            }

                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject mainResponse = jsonObject.getJSONObject("response");
                            Toast.makeText(activity, mainResponse.getString("message"), Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post(new Event(Constants.DELETECARDSUCCESS, ""));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Utils.hideProgressDialog();
                Toast.makeText(activity, error.getMessage() + "", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "deleteCard");
                params.put("card_number", cardNumber);
                params.put("user_id", CSPreferences.readString(activity, "user_id"));
                Log.d("Params", params + "");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        requestQueue.add(stringRequest);
    }

    @Override
    public int getItemCount() {
        return response.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cardNumber, expiry;
        LinearLayout delete;
        CardView mainView;
        RadioButton rd_btn;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            cardNumber = itemView.findViewById(R.id.cardnumber);
            expiry = itemView.findViewById(R.id.expiry);
            delete = itemView.findViewById(R.id.delete);
            mainView = itemView.findViewById(R.id.main_view);
            rd_btn = itemView.findViewById(R.id.rd_btn);
            img = itemView.findViewById(R.id.img);
        }
    }

    public void customNotify(List<CardDetailsBean.Datum> response, Activity activity) {
        this.response = response;
        this.activity = activity;
        notifyDataSetChanged();
    }
}