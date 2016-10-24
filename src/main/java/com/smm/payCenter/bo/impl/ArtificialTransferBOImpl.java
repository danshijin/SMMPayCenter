package com.smm.payCenter.bo.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.smm.payCenter.bo.ArtificialTransferBO;
import com.smm.payCenter.dao.ArtificialTransferDAO;
import com.smm.payCenter.domain.ArtificialTransferVO;

@Service
public class ArtificialTransferBOImpl implements ArtificialTransferBO {

	@Resource
	private ArtificialTransferDAO transferdao;
	
	@Override
	public List<ArtificialTransferVO> ArtificialTransferList(Map<String, Object> map) {
		List<ArtificialTransferVO> list = transferdao.ArtificialTransferList(map);
		return list;
	}

	@Override
	public ArtificialTransferVO ArtificialTransferDetail(Integer mailorderid) {
		// TODO Auto-generated method stub
		return null;
	}

}
