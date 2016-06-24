/*Copyright (c) 2015-2016 wavemaker-com All Rights Reserved.
 This software is the confidential and proprietary information of wavemaker-com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with wavemaker-com*/


package com.wavemaker.sampleapps.wavekart.eshopping.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.wavemaker.runtime.data.model.CustomProcedure;
import com.wavemaker.runtime.data.dao.procedure.WMProcedureExecutor;

@Service
public class EshoppingProcedureExecutorServiceImpl implements EshoppingProcedureExecutorService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EshoppingProcedureExecutorServiceImpl.class);

	@Autowired
	@Qualifier("eshoppingWMProcedureExecutor")
	private WMProcedureExecutor procedureExecutor;


	@Transactional(value = "eshoppingTransactionManager")
	@Override
	public List<Object> executeWMCustomProcedure(CustomProcedure procedure) {
	    return procedureExecutor.executeCustomProcedure(procedure);
	}


}

