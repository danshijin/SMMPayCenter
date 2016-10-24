package com.smm.payCenter.bo;

import java.util.List;
import java.util.Map;

import com.smm.payCenter.domain.ArtificialTransferVO;

/**
 * 
 * @author hece
 *	人工转账
 */
public interface ArtificialTransferBO {
	
	public List<ArtificialTransferVO> ArtificialTransferList(Map<String,Object> map);
	public ArtificialTransferVO ArtificialTransferDetail(Integer mailorderid);
}