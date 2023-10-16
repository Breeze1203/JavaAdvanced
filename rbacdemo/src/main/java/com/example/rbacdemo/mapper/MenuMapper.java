package com.example.rbacdemo.mapper;

import com.example.rbacdemo.model.menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MenuMapper")
public interface MenuMapper {
   List<menu> getMenuByRole(@Param("rid") int rid);
}
