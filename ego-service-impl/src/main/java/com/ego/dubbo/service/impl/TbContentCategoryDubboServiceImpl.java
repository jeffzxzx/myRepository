package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;

public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService {
	@Resource
	private TbContentCategoryMapper tbContentCategoryMapper;
	
	// 根据父id查询
	@Override
	public List<TbContentCategory> selByPid2(long pid) {
		// 创建查询条件
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(pid);
		return tbContentCategoryMapper.selectByExample(example);
	}
	// 根据父id查询
	@Override
	public List<TbContentCategory> selByPid(long pid) {
		// 创建查询条件
		TbContentCategoryExample example = new TbContentCategoryExample();
		example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
		return tbContentCategoryMapper.selectByExample(example);
	}

	// 插入类目分类内容
	@Override
	public int insContentCategory(TbContentCategory category) {
		return tbContentCategoryMapper.insertSelective(category);
	}

	// 修改类目分类内容，同时可逻辑上删除分类内容，status为2指删除
	@Override
	public int updByCategory(TbContentCategory category) {
		return tbContentCategoryMapper.updateByPrimaryKeySelective(category);
	}
	
	//根据id查询返回单个对象
	@Override
	public TbContentCategory selById(long id) {
		return tbContentCategoryMapper.selectByPrimaryKey(id);
	}
}
