package com.ego.dubbo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.mapper.TbOrderItemMapper;
import com.ego.mapper.TbOrderMapper;
import com.ego.mapper.TbOrderShippingMapper;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

public class TbOrderDubboServiceImpl implements TbOrderDubboService {
	@Resource
	private TbOrderMapper tbOrderMapper;
	@Resource
	private TbOrderItemMapper tbOrderItemMapper;
	@Resource
	private TbOrderShippingMapper tbOrderShippingMapper;

	// 商品订单
	@Override
	public EgoResult insOrder(TbOrder order, List<TbOrderItem> item, TbOrderShipping shipping) throws Exception {
		int index = 0;
		index += tbOrderMapper.insertSelective(order);
		for (TbOrderItem tbOrderItem : item) {
			index += tbOrderItemMapper.insertSelective(tbOrderItem);
		}
		index += tbOrderShippingMapper.insertSelective(shipping);
		EgoResult er = new EgoResult();
		if (index == 2 + item.size()) {
			er.setStatus(200);
		} else {
			// 考虑到事务回滚
			throw new Exception("订单提交失败!");
		}
		return er;
	}

}
