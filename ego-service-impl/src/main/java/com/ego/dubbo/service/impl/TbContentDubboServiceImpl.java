package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbContentDubboServiceImpl implements TbContentDubboService{
	
	@Resource
	private TbContentMapper tbContentMapper;
	@Override
	public EasyUIDataGrid showContent(long categoryId, int page, int rows) {
		PageHelper.startPage(page, rows);
		//创建查询条件
		TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
		//分页
		PageInfo pi = new PageInfo(list);
		EasyUIDataGrid dataGrid = new EasyUIDataGrid();
		dataGrid.setRows(pi.getList());
		dataGrid.setTotal(pi.getTotal());
		return dataGrid;
	}
	
	//内容新增
	@Override
	public int insContent(TbContent content) {
		return tbContentMapper.insertSelective(content);
	}
	
	//内容修改
	@Override
	public int updContent(TbContent content) {
		return tbContentMapper.updateByPrimaryKeySelective(content);
	}
	
	//删除内容
	@Override
	public int delContent(long ids) {
		return tbContentMapper.deleteByPrimaryKey(ids);
	}
	
	//按照是否排序取出(count)多少条
	@Override
	public List<TbContent> selByCount(int count, boolean isSort) {
		TbContentExample example = new TbContentExample();
		//先判断是否需要排序
		if(isSort){
			//按照修改时间的降序排序
			example.setOrderByClause("updated desc");
		}
		//判断是否需要取出count条
		if(count!=0){
			//开始分页查询
			PageHelper.startPage(1, count);
			List<TbContent> list = tbContentMapper.selectByExample(example);
			PageInfo<TbContent> pi = new PageInfo<>(list);
			return pi.getList();
		}else{
			//不需要查询多少条,返回查询的所有结果
			return tbContentMapper.selectByExample(example);
			
		}
		
	}

}
