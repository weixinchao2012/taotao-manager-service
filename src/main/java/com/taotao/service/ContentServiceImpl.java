package com.taotao.service;

import java.util.Date;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.utils.HttpClientUtil;

@Service
public class ContentServiceImpl implements ContentService {

	 private Logger log = Logger.getLogger(ContentServiceImpl.class);//输出Log日志
	@Autowired
	private TbContentMapper contentMapper;
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	
	@Override
	public EasyUIResult getContentList(long catId, Integer page, Integer rows) {
		//根据category_id查询内容列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(catId);
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		//取分页信息
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		EasyUIResult result = new EasyUIResult(pageInfo.getTotal(), list);
		return result;
	}

	@Override
	public TaotaoResult addContent(TbContent content) {
		//把图片信息保存至数据库
				content.setCreated(new Date());
				content.setUpdated(new Date());
				//把内容信息添加到数据库
				contentMapper.insert(content);
				try {
					HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId());
				} catch (Exception e) {
					log.error("sync error..."+e);
				}
				
				
				return TaotaoResult.ok();
	}

}