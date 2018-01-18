package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.dto.InvoiceInfoDTO;
import com.jeeplus.modules.business.entity.Invoice;
import com.jeeplus.modules.business.entity.InvoiceExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface InvoiceMapper {
    int countByExample(InvoiceExample example);

    int deleteByExample(InvoiceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Invoice record);

    int insertSelective(Invoice record);

    List<Invoice> selectByExample(InvoiceExample example);

    Invoice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Invoice record, @Param("example") InvoiceExample example);

    int updateByExample(@Param("record") Invoice record, @Param("example") InvoiceExample example);

    int updateByPrimaryKeySelective(Invoice record);

    int updateByPrimaryKey(Invoice record);

	List<InvoiceInfoDTO> selectInvoices4Print(List<Integer> lds);

	List<Invoice> select4List(Invoice invoice);

	List<InvoiceInfoDTO> selectUndealInvoices();

}