package com.prettyant.loan.ui.main.fragment;

import android.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.prettyant.loan.R;
import com.prettyant.loan.cons.ContantFields;
import com.prettyant.loan.model.bean.BusinessTypeModel;
import com.prettyant.loan.model.mvpview.ApplyMvpView;
import com.prettyant.loan.model.mvpview.RateMvpView;
import com.prettyant.loan.model.bean.BusinessInfo;
import com.prettyant.loan.model.bean.Response;
import com.prettyant.loan.presenter.ApplyPresenter;
import com.prettyant.loan.presenter.RatePresenter;
import com.prettyant.loan.ui.base.BaseFragment;
import com.prettyant.loan.view.CommDialog;
import com.prettyant.loan.view.pop.BusinessTypePopWindow;
import com.prettyant.loan.view.pop.adapter.BusinessTypeAdapter;

import java.util.ArrayList;


public class FragmentTabBusiness extends BaseFragment implements BusinessTypeAdapter.ItemClickListener, RateMvpView, ApplyMvpView, RadioGroup.OnCheckedChangeListener {

    private TextView tv_choose;

    private ArrayList<BusinessTypeModel> businessTypeModels = new ArrayList<>();
    private TextView                     tv_username;
    private RadioGroup                   rg_during;
    private RadioButton                  rb_1;
    private RadioButton                  rb_3;
    private RadioButton                  rb_5;
    private RadioButton                  rb_10;
    private EditText                     et_amout;
    private TextView                     tv_rate;
    private TextView                     tv_total;
    private CheckBox                     cb_rule;
    private Button                       btn_submit;
    private TextView                     tv_cal;
    private RatePresenter                ratePresenter;
    private LinearLayout                 ll_total;
    private ApplyPresenter               applyPresenter;
    private String                       years;

    @Override
    public int getContentView() {
        return R.layout.fragment_business;
    }

    /**
     * 注意：rl_title.getBackground().setAlpha(0)，设置标题栏背景透明度，会影响其它界面的背景，
     * 如果加上这个效果，那么其他界面的背景色在xml中设置将失效，需代码中动态设置，
     * 这个问题很奇葩，未找到原因，
     * 现在要么不要这个滑动改变透明度的效果，要么其他界面中的背景色就动态设置
     */
    @Override
    public void initView() {
        tv_username = (TextView) $(R.id.tv_username);
        tv_choose = (TextView) $(R.id.tv_choose);//贷款用途
        //年限
        rg_during = (RadioGroup) $(R.id.rg_during);
        //1年
        rb_1 = (RadioButton) $(R.id.rb_1);
        //3年
        rb_3 = (RadioButton) $(R.id.rb_3);
        //5年
        rb_5 = (RadioButton) $(R.id.rb_5);
        //10年
        rb_10 = (RadioButton) $(R.id.rb_10);
        //贷款总额
        et_amout = (EditText) $(R.id.et_amout);
        //贷款利率
        tv_rate = (TextView) $(R.id.tv_rate);
        //试算
        tv_cal = (TextView) $(R.id.tv_cal);
        //本息总额
        tv_total = (TextView) $(R.id.tv_total);
        //条款
        cb_rule = (CheckBox) $(R.id.cb_rule);
        //提交
        btn_submit = (Button) $(R.id.btn_submit);
        //本息总额
        ll_total = (LinearLayout) $(R.id.ll_total);
    }

    @Override
    public void initClick() {
        tv_choose.setOnClickListener(this);
        tv_cal.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        rg_during.setOnCheckedChangeListener(this);
    }

    @Override
    public void initData() {
        tv_username.setText(ContantFields.username);
        BusinessTypeModel businessTypeModel = new BusinessTypeModel();
        businessTypeModel.setBusinessIcon(R.mipmap.icon_fangdai);
        businessTypeModel.setBusinessName("房贷");

        BusinessTypeModel businessTypeModel1 = new BusinessTypeModel();
        businessTypeModel1.setBusinessIcon(R.mipmap.icon_chedai);
        businessTypeModel1.setBusinessName("车贷");

        BusinessTypeModel businessTypeModel2 = new BusinessTypeModel();
        businessTypeModel2.setBusinessIcon(R.mipmap.icon_zhuangxiu);
        businessTypeModel2.setBusinessName("装修");

        BusinessTypeModel businessTypeModel3 = new BusinessTypeModel();
        businessTypeModel3.setBusinessIcon(R.mipmap.icon_other);
        businessTypeModel3.setBusinessName("其他");

        businessTypeModels.add(businessTypeModel);
        businessTypeModels.add(businessTypeModel1);
        businessTypeModels.add(businessTypeModel2);
        businessTypeModels.add(businessTypeModel3);

        ratePresenter = new RatePresenter(getActivity());
        ratePresenter.attachView(this);
        applyPresenter = new ApplyPresenter(getActivity());
        applyPresenter.attachView(this);
    }

    @Override
    public void onClick(View v) {
        BusinessInfo businessInfo = new BusinessInfo();
        String username = tv_username.getText().toString();
        String businessName = tv_choose.getText().toString();
        String amount = et_amout.getText().toString();
        businessInfo.setUsername(username);
        businessInfo.setBusinessName(businessName);
        businessInfo.setDuringTime(years);
        businessInfo.setAmount(amount);

        switch (v.getId()) {
            case R.id.tv_choose:
                BusinessTypePopWindow.getInstance().showBusinessPop(tv_choose, getActivity(), businessTypeModels, this);
                break;
            case R.id.tv_cal:
                //试算接口
                if (isMatch(businessName, amount)) return;
                ratePresenter.getRate(businessInfo);
                break;
            case R.id.btn_submit:
                if (isMatch(businessName, amount)) return;
                if (!cb_rule.isChecked()) {
                    CommDialog.getInstance().commDialog(getActivity(), "温馨提示", "请勾选服务条款", null, "确定", new CommDialog.DialogClickListener() {
                        @Override
                        public void onCancleClickListener(View view, AlertDialog dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void onSureClickListener(View view, AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
                    return;
                }
                //提交接口
                applyPresenter.apply(businessInfo);
                break;
        }
    }

    /**
     * 非空提交校验
     * @param businessName
     * @param amount
     * @return
     */
    private boolean isMatch(String businessName, String amount) {
        if (TextUtils.isEmpty(businessName)) {
            CommDialog.getInstance().commDialog(getActivity(), "温馨提示", "请选择贷款用途", null, "确定", new CommDialog.DialogClickListener() {
                @Override
                public void onCancleClickListener(View view, AlertDialog dialog) {
                    dialog.dismiss();
                }

                @Override
                public void onSureClickListener(View view, AlertDialog dialog) {
                    dialog.dismiss();
                }
            });
            return true;
        }
        if (TextUtils.isEmpty(years)) {
            CommDialog.getInstance().commDialog(getActivity(), "温馨提示", "请选择贷款年限", null, "确定", new CommDialog.DialogClickListener() {
                @Override
                public void onCancleClickListener(View view, AlertDialog dialog) {
                    dialog.dismiss();
                }

                @Override
                public void onSureClickListener(View view, AlertDialog dialog) {
                    dialog.dismiss();
                }
            });
            return true;
        }
        if (TextUtils.isEmpty(amount)) {
            CommDialog.getInstance().commDialog(getActivity(), "温馨提示", "请输入贷款金额", null, "确定", new CommDialog.DialogClickListener() {
                @Override
                public void onCancleClickListener(View view, AlertDialog dialog) {
                    dialog.dismiss();
                }

                @Override
                public void onSureClickListener(View view, AlertDialog dialog) {
                    dialog.dismiss();
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ratePresenter.detachView();
        applyPresenter.detachView();
    }

    @Override
    public void onItemClickListener(View view, int position) {
        BusinessTypeModel businessTypeModel = businessTypeModels.get(position);
        String            businessName      = businessTypeModel.getBusinessName();
        tv_choose.setText(businessName);
        BusinessTypePopWindow.getInstance().hideBottomPop();
    }

    @Override
    public void getRateSuccess(BusinessInfo businessInfo) {
        float  rate       = businessInfo.getRate();
        String result     = String.format("%.2f",rate * 100);
        String amoutS     = et_amout.getText().toString();
        float  amoutF     = Float.parseFloat(amoutS);
        float  totalMoney = (rate + 1) * amoutF;
        tv_rate.setText(result);
        tv_total.setText(String.format("%.2f",totalMoney));
        ll_total.setVisibility(View.VISIBLE);
    }

    @Override
    public void getRateFail(String message) {
        ll_total.setVisibility(View.GONE);
        CommDialog.getInstance().commDialog(getActivity(), "温馨提示", message, null, "确定", new CommDialog.DialogClickListener() {
            @Override
            public void onCancleClickListener(View view, AlertDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onSureClickListener(View view, AlertDialog dialog) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void applySuccess(Response response) {

        CommDialog.getInstance().commDialog(getActivity(), "温馨提示", response.message, null, "确定", new CommDialog.DialogClickListener() {
            @Override
            public void onCancleClickListener(View view, AlertDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onSureClickListener(View view, AlertDialog dialog) {
                tv_choose.setText("");
                rb_1.setChecked(false);
                rb_3.setChecked(false);
                rb_5.setChecked(false);
                rb_10.setChecked(false);
                et_amout.setText("");
                tv_rate.setText("");
                ll_total.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void applyFail(String message) {
        CommDialog.getInstance().commDialog(getActivity(), "温馨提示", message, null, "确定", new CommDialog.DialogClickListener() {
            @Override
            public void onCancleClickListener(View view, AlertDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onSureClickListener(View view, AlertDialog dialog) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int         checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton          = getActivity().findViewById(checkedRadioButtonId);
        years = radioButton.getText().toString();
//        LogUtil.i("你选择了-->>"+radioButton.getText().toString()+"  i -->>"+i);
    }
}
