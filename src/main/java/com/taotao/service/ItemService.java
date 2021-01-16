package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIResult;
import com.taotao.common.pojo.TreeNode;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {

	public TbItem getItemById(long itemId);
	public EasyUIResult getItemList(Integer page, Integer rows);
	public TaotaoResult addItem(TbItem item, TbItemDesc itemDesc);
}
