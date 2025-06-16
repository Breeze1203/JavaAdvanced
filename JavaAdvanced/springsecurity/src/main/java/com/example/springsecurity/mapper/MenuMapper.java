package com.example.springsecurity.mapper;

import com.example.springsecurity.model.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "MenuMapper")
public interface MenuMapper {
    List<Menu> getMenuByRole();
}
