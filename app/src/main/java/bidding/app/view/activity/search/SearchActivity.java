package bidding.app.view.activity.search;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import bidding.app.R;
import bidding.app.extra.Utils;
import bidding.app.model.ProductList;
import bidding.app.view.activity.BaseActivity;
import bidding.app.view.activity.search.presenter.SearchActivityPresenter;
import bidding.app.view.activity.search.presenter.SearchActivityPresenterHandler;
import bidding.app.view.activity.search.view.SearchActivityView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements SearchActivityView, TextView.OnEditorActionListener, TextWatcher {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    SearchActivityPresenterHandler handler;
    String key = "name", value = "", conditionType = "like", direction = "DESC", pageSize = "10", currentPage = "1";
    @BindView(R.id.ll_search_activity)
    LinearLayout llSearchActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        findView();
        listner();

    }

    private void findView() {
        handler = new SearchActivityPresenter(this);
    }

    private void listner() {
        edSearch.setOnEditorActionListener(this);
        edSearch.addTextChangedListener(this);
    }

    @OnClick({R.id.back, R.id.iv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:

                Utils.hideKeyboardFrom(this, llSearchActivity);
                Handler handler = new Handler();
                Runnable r = new Runnable() {
                    public void run() {
                        finish();
                    }
                };
                handler.postDelayed(r, 100);

                break;

            case R.id.iv_clear:

                if (edSearch.getText().toString().trim().length() > 0) {
                    String result = edSearch.getText().toString().substring(0, edSearch.getText().toString().length() - 1);
                    edSearch.setText(result);
                    edSearch.setSelection(result.length());
                }

                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_SEARCH) {
            //Search
            Utils.hideKeyboardFrom(this, llSearchActivity);
            value = edSearch.getText().toString();
            handler.productlist(key, "%" + value + "%", conditionType, direction, pageSize, "1");

            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.length() > 0)
            ivClear.setVisibility(View.VISIBLE);
        else
            ivClear.setVisibility(View.GONE);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void showProgess() {
        showProgressDialog();
    }

    @Override
    public void hideProgess() {
        hideProgressDialog();
    }

    @Override
    public void showFeedBackMessage(String message) {
        showToast(message);
    }

    @Override
    public void productList(ProductList productList) {

    }
}
