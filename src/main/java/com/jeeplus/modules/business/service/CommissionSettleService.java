package com.jeeplus.modules.business.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.modules.business.dao.CommissionSettlementMapper;
import com.jeeplus.modules.business.dto.VenueSettlementDTO;
import com.jeeplus.modules.business.entity.CommissionSettlement;

@Service
public class CommissionSettleService {

	@Autowired
	CommissionSettlementMapper settlementMapper ;
	
	public boolean saveCommissionSettle(CommissionSettlement commissionSettle) {
		
		commissionSettle.setStatus(CommonConstants.CommissionSettleStatus.SETTLED.getCode());
		commissionSettle.setTollerType(CommonConstants.CommissionTollerType.VENUE.getCode());
		commissionSettle.preInsert();
		int num = settlementMapper.insert(commissionSettle);
		if (num > 0) {
			return true;
		}
		return false;
	}

	public List<VenueSettlementDTO> listSettlements4Venue(Integer id) {
		List<VenueSettlementDTO> settlementDTOs = null ;
		settlementDTOs = settlementMapper.selectSettlements4Venue(id);
		if (settlementDTOs == null) {
			settlementDTOs = new ArrayList<VenueSettlementDTO>();
		}
		return settlementDTOs;
	}

}
