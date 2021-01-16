package com.taotao.service;

import java.util.List;

import com.taotao.pojo.EasyUITreeNode;
import com.taotao.pojo.TaotaoResult;

public interface ContentCategoryService {
	public List<EasyUITreeNode> getContentCategoryList(long parentid);
	
	public TaotaoResult addNode(long parentid, String name)  throws Exception;
}
