package com.dataprocess.bods.business;

import com.dataprocess.bods.dao.InstanceDetailsDAO;
import com.dataprocess.bods.vo.InstanceDetailsVO;

public class InstanceDetailsBusiness {

	public boolean saveInstanceDetails(InstanceDetailsVO instanceDetailsVO) {
		InstanceDetailsDAO instanceDAO = null;
		boolean hasCompleted = false;
		try {
			instanceDAO = new InstanceDetailsDAO();
			hasCompleted = instanceDAO.saveInstanceDetails(instanceDetailsVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hasCompleted;
	}

}
