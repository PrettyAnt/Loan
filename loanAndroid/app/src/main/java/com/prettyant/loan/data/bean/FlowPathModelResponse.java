package com.prettyant.loan.data.bean;


import java.util.List;

public class FlowPathModelResponse extends Response {
    private List<FlowPathModel> flowPathModels;

    public List<FlowPathModel> getFlowPathModels() {
        return flowPathModels;
    }

    public void setFlowPathModels(List<FlowPathModel> flowPathModels) {
        this.flowPathModels = flowPathModels;
    }
}
