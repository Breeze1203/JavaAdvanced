package com.example.adminflow.mapper;

import com.example.adminflow.model.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MenuMapper")
public interface MenuMapper {
   List<Menu> getMenuByRole(@Param("rid") int rid);
}
