package com.lesso.base;

import java.io.Serializable;

public class DataTablesBo implements Serializable
{

	private Integer draw;				//当前页
	private Integer recordsTotal;		//总记录数
	private Integer recordsFiltered;	
	private Object  data;				//分页查询数据集合
	public Integer getDraw()
	{
		return draw;
	}
	public void setDraw(Integer draw)
	{
		this.draw = draw;
	}
	public Integer getRecordsTotal()
	{
		return recordsTotal;
	}
	public void setRecordsTotal(Integer recordsTotal)
	{
		this.recordsTotal = recordsTotal;
	}
	public Integer getRecordsFiltered()
	{
		return recordsFiltered;
	}
	public void setRecordsFiltered(Integer recordsFiltered)
	{
		this.recordsFiltered = recordsFiltered;
	}
	public Object getData()
	{
		return data;
	}
	public void setData(Object data)
	{
		this.data = data;
	}
}
