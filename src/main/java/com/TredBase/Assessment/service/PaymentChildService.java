package com.TredBase.Assessment.service;

import com.TredBase.Assessment.vo.request.ParentChildRequest;
import com.TredBase.Assessment.vo.response.ParentChildResponse;

public interface PaymentChildService {

    ParentChildResponse saveParentChild(ParentChildRequest request);
}
