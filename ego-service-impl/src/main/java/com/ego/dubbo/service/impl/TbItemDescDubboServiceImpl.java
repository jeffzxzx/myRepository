package com.ego.dubbo.service.impl;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemDescExample;

public class TbItemDescDubboServiceImpl implements TbItemDescDubboService{
	@Resource
	private TbItemDescMapper tbItemDescMapper;
	
	//新增商品描述
	@Override
	public int insItemDesc(TbItemDesc desc) {
		return tbItemDescMapper.insert(desc);
	}
	
	//商品的描述信息查询
	public TbItemDesc selByItemid(long id) {
		return tbItemDescMapper.selectByPrimaryKey(id);
		
	}
	
	
	//修改商品描述
	@Override
	public int updItemDesc(TbItemDesc desc) {
		return tbItemDescMapper.updateByExampleWithBLOBs(desc, new TbItemDescExample());
	}

}
