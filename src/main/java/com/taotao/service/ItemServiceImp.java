package com.taotao.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIResult;
import com.taotao.common.pojo.TreeNode;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.utils.ExceptionUtil;
import com.taotao.utils.IDUtils;

@Service
public class ItemServiceImp implements ItemService {

	@Autowired TbItemMapper tbItemMapper;
	
	@Autowired TbItemDescMapper itemDescMapper;
	@Override
	public TbItem getItemById(long itemId) {
		// TODO Auto-generated method stub
		//tbItemMapper.selectByPrimaryKey(itemId);
		TbItemExample example =new TbItemExample();
		Criteria criteria=example.createCriteria();
		criteria.andIdEqualTo(itemId);
	
			TbItem tbItemLst=tbItemMapper.selectByPrimaryKey(itemId);

		System.out.println("Ok....");
			return tbItemLst;

	}
	@Override
	public EasyUIResult getItemList(Integer page, Integer rows) {
		TbItemExample example = new TbItemExample();
		//设置分页
		PageHelper.startPage(page, rows);
		List<TbItem> list = tbItemMapper.selectByExample(example);
		//取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EasyUIResult result = new EasyUIResult(total, list);
		
		return result;
	}
	@Override
	public TaotaoResult addItem(TbItem item, TbItemDesc itemDesc) {
		// TODO Auto-generated method stub
		try {
			//生成商品id
			//可以使用redis的自增长key，在没有redis之前使用时间+随机数策略生成
			Long itemId = IDUtils.genItemId();
			//补全不完整的字段
			item.setId(itemId);
			item.setStatus((byte) 1);
			Date date = new Date();
			item.setCreated(date);
			item.setUpdated(date);
			//把数据插入到商品表
			tbItemMapper.insert(item);
			//添加商品描述
			itemDesc.setItemId(itemId);
			itemDesc.setCreated(date);
			itemDesc.setUpdated(date);
			//把数据插入到商品描述表
			itemDescMapper.insert(itemDesc);
			
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
		return TaotaoResult.ok();
	}


}
