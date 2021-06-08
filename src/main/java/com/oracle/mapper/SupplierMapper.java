package com.oracle.mapper;

import com.oracle.entity.Supplier;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierMapper {
    List<Supplier> getSupById(@Param("memId") int memId);

    List<Supplier> getAllSup();

    int addSup(Supplier supplier);

    int updateSupStatus(@Param("supStatus") int supStatus,@Param("supId") int supId);

    int delSup(@Param("supId") int supId);

    int updSup(Supplier supplier);

    List<Supplier> getAllSupNoPass();

    Supplier getSupByName(@Param("name") String supname);

    List<Supplier> getPurSup(@Param("purId") int purId);

    List<Supplier> getAllSupPass();

    Supplier getSupBySupId(@Param("supId")int supId);
}
