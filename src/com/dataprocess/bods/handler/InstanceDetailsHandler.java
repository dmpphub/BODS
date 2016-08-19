package com.dataprocess.bods.handler;

import com.dataprocess.bods.business.InstanceDetailsBusiness;
import com.dataprocess.bods.vo.InstanceDetailsVO;

public class InstanceDetailsHandler {

	public boolean saveInstanceDetails(InstanceDetailsVO instanceDetailsVO) {
		InstanceDetailsBusiness instanceBusiness = null;
		boolean hasCompleted = false;
		try {
			instanceBusiness = new InstanceDetailsBusiness();
			hasCompleted = instanceBusiness
					.saveInstanceDetails(instanceDetailsVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return hasCompleted;
	}

}
