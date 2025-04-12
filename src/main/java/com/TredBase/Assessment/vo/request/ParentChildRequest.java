package com.TredBase.Assessment.vo.request;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//@Getter
//@Setter
//@NoArgsConstructor
public class ParentChildRequest {


    private List<ParentRequest> parents = new ArrayList<>();
    private List<ChildRequest> children = new ArrayList<>();

    public ParentChildRequest() {
    }

    public List<ParentRequest> getParents() {
        return parents;
    }

    public void setParents(List<ParentRequest> parents) {
        this.parents = parents;
    }

    public List<ChildRequest> getChildren() {
        return children;
    }

    public void setChildren(List<ChildRequest> children) {
        this.children = children;
    }

}
