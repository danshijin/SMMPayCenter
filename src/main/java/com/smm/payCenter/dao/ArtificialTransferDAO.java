package com.smm.payCenter.dao;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.ArtificialTransferVO;

public interface ArtificialTransferDAO {

	public List<ArtificialTransferVO> ArtificialTransferList(Map<String,Object> map);
	public ArtificialTransferVO ArtificialTransferDetail(Integer mailorderid);
	
}
