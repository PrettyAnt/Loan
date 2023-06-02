package com.prettyant.loan.ui.main.fragment;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.prettyant.loan.R;
import com.prettyant.loan.databinding.FragmentBusinessBinding;
import com.prettyant.loan.imp.ItemClickListener;
import com.prettyant.loan.data.bean.BusinessTypeModel;
import com.prettyant.loan.ui.base.BaseJetFragment;
import com.prettyant.loan.view.pop.BusinessTypePopWindow;

import java.util.ArrayList;

/**
 * @author chenyu
 * My personal blog  https://prettyant.github.io
 * Created on 9:18 AM  30/05/23
 * PackageName : com.prettyant.loan.ui.main.fragment
 * describle :
 */
public class FragmentTabBusiness extends BaseJetFragment<FragmentBusinessBinding, TabBusinessViewModel> {
    private ArrayList<BusinessTypeModel> businessTypeModels = new ArrayList<>();

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_business;
    }

    @Override
    protected void initViewModel() {
        viewModel = new ViewModelProvider(this).get(TabBusinessViewModel.class);
        viewModel.setActivity(getActivity());
    }

    @Override
    protected void bindViewModel() {
        dataBinding.setVm(viewModel);
        dataBinding.setClick(new ProxyClick());
    }

    @Override
    protected void init() {
        dataBinding.llTotal.setVisibility(View.GONE);
        businessTypeModels.clear();
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

        viewModel.getBusinessInfoMutableLiveData().observe(this, businessInfo -> {
            if (businessInfo.code != 1) {
                Toast.makeText(getContext(), businessInfo.message, Toast.LENGTH_SHORT).show();
                return;
            }
            @SuppressLint("DefaultLocale")
            String rate = String.format("%.2f", businessInfo.getRate() * 100);//贷款利率
            String amount = viewModel.getAmout().getValue();
            if (TextUtils.isEmpty(amount)) {
                amount = "0";
            }
            float total = Float.parseFloat(amount) * (1 + businessInfo.getRate());//贷款总额
            @SuppressLint("DefaultLocale")
            String totalStr = String.format("%.2f", total);
            dataBinding.tvRate.setText(rate);
            dataBinding.tvTotal.setText(totalStr);
            dataBinding.llTotal.setVisibility(View.VISIBLE);
        });
        viewModel.getResponseMutableLiveData().observe(this, response -> {
            if (response.code != 1) {
                Toast.makeText(getActivity(), TextUtils.isEmpty(response.message) ? "" : response.message, Toast.LENGTH_SHORT).show();
                return;
            }
            dataBinding.llTotal.setVisibility(View.GONE);
            viewModel.getRate().setValue("");
            viewModel.getAmout().setValue("");
            viewModel.getChoose().setValue("");
            dataBinding.rgDuring.clearCheck();
            viewModel.showDialog(response.message);
        });
    }

    public class ProxyClick implements ItemClickListener {
        public void click() {
            BusinessTypePopWindow.getInstance().showBusinessPop(dataBinding.tvChoose, getActivity(), businessTypeModels, this);
        }

        @Override
        public void onItemClickListener(View view, int position) {
            BusinessTypeModel businessTypeModel = businessTypeModels.get(position);
            String businessName = businessTypeModel.getBusinessName();
            dataBinding.tvChoose.setText(businessName);
            BusinessTypePopWindow.getInstance().hideBottomPop();
        }
    }
}
