package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemExample;
import com.ego.pojo.TbItemParamItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbItemDubboServiceImpl implements TbItemDubboService {
	@Resource
	TbItemMapper tbItemMapper;
	@Resource
	TbItemDescMapper tbItemDescMapper;
	@Resource
	TbItemParamItemMapper tbItemParamItemMapper;

	// 显示所有商品
	@Override
	public EasyUIDataGrid show(int page, int rows) {
		// 开始分页
		PageHelper.startPage(page, rows);
		// 查询全部
		List<TbItem> list = tbItemMapper.selectByExample(new TbItemExample());
		// 查询条件
		PageInfo<TbItem> pi = new PageInfo(list);
		// 创建返回对象
		EasyUIDataGrid dataGrid = new EasyUIDataGrid();
		dataGrid.setRows(pi.getList());
		dataGrid.setTotal(pi.getTotal());
		return dataGrid;
	}
	
	// 修改商品状态
	@Override
	public int updByStatus(TbItem item) {
		// 根据对象修改，有数据则修改，null不该
		return tbItemMapper.updateByPrimaryKeySelective(item);
	}

	// 商品新增
	@Override
	public int insItem(TbItem item) {
		return tbItemMapper.insert(item);
	}

	// 商品新增，考虑事务回滚
	@Override
	public int insTtem(TbItem item, TbItemDesc desc , TbItemParamItem itemParam) throws Exception {
		int index = 0;
		try {
			index = tbItemMapper.insertSelective(item);
			index += tbItemDescMapper.insertSelective(desc);
			index += tbItemParamItemMapper.insertSelective(itemParam);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (index == 3) {
			return 1;
		} else {
			throw new Exception("新增失敗");
		}
	}
	
	
	//根据商品状态查询
	@Override
	public List<TbItem> selByStatus(byte status) {
		//创建查询条件
		TbItemExample example = new TbItemExample();
		example.createCriteria().andStatusEqualTo(status);
		List<TbItem> list = tbItemMapper.selectByExample(example);
		return list;
	}
	
	//根据商品的id查询到商品对象
	@Override
	public TbItem selById(long id) {
		return tbItemMapper.selectByPrimaryKey(id);
	}

	//商品修改
	@Override
	public int update(TbItem item) {
		return tbItemMapper.countByExample(new TbItemExample());
	}
	
	//商品修改
	@Override
	public int updItem(TbItem item, TbItemDesc desc, TbItemParamItem param) throws Exception {
		int index = 0;
		try {
			index = tbItemMapper.updateByPrimaryKeySelective(item);
			index +=tbItemDescMapper.updateByPrimaryKeySelective(desc);
			index +=tbItemParamItemMapper.updateByPrimaryKeySelective(param);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (index == 3) {
			return 1;
		} else {
			throw new Exception("新增失敗");
		}
	}

}
