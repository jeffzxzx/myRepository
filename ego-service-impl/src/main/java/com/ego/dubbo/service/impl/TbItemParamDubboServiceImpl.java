package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TbItemParamDubboServiceImpl implements TbItemParamDubboService {
	@Resource
	private TbItemParamMapper tbItemParamMapper;

	@Override
	public EasyUIDataGrid selAllItemParam(int page, int rows) {
		PageHelper.startPage(page, rows);
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(new TbItemParamExample());
		PageInfo pi = new PageInfo(list);
		EasyUIDataGrid dataGrid = new EasyUIDataGrid();
		dataGrid.setRows(pi.getList());
		dataGrid.setTotal(pi.getTotal());
		return dataGrid;
	}

	@Override
	public int delItemParam(String ids) throws Exception {
		// 是否有多条记录
		String idStr[] = ids.split(",");
		int index = 0;
		for (String id : idStr) {
			index = tbItemParamMapper.deleteByPrimaryKey(Long.parseLong(id));
		}
		if (index == idStr.length) {
			return 1;
		} else {
			throw new Exception("删除失败；可能数据不存在");
		}
	}

	@Override
	public TbItemParam selByCatId(long catId) {
		TbItemParamExample example = new TbItemParamExample();
		// 查询条件
		example.createCriteria().andItemCatIdEqualTo(catId);
		// 查询
		List<TbItemParam> param = tbItemParamMapper.selectByExampleWithBLOBs(example);
		TbItemParam tbItemParam = new TbItemParam();
		//在判断list集合是否有数据时：不仅要判断是否为空，
		//还要判断长度是否大于0，因为当容器初始化后！=null，此时容器中并没有值。
		if (param != null && param.size()>0) {
			tbItemParam = param.get(0);
		}
		return tbItemParam;
	}

	@Override
	public int insItemParam(TbItemParam param) {
		int index = tbItemParamMapper.insertSelective(param);
		return index;
	}

}
