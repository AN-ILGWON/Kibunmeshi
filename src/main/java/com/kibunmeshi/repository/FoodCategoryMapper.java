package com.kibunmeshi.repository;

import com.kibunmeshi.domain.FoodCategory;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface FoodCategoryMapper {
    List<FoodCategory> findAll();
    FoodCategory findById(Integer id);
}
