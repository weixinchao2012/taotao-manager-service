package com.taotao.service;

import com.taotao.common.pojo.EasyUIResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	public EasyUIResult getContentList(long catId, Integer page, Integer rows);
	public TaotaoResult addContent(TbContent content);
}
