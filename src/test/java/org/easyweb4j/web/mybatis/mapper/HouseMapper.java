package org.easyweb4j.web.mybatis.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.easyweb4j.web.mybatis.entity.House;

@Mapper
public interface HouseMapper {

  @Insert("insert into house (name, floor_number, state, state_ts) values(#{name}, #{floorNumber}, #{state,jdbcType=BIT}, #{stateTs,jdbcType=TIMESTAMP})")
  @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
  int insert(House house);

  @Select("select id, name, floor_number,state, state_ts from house where id = #{id}")
  House selectOne(Integer id);

  @Insert("insert into house (name, floor_number, state, state_ts) values(#{name}, #{DEFAULT_FLOOR}, #{state,jdbcType=BIT}, #{stateTs,jdbcType=TIMESTAMP})")
  @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = int.class)
  int insertWithGlobalContext(House house);
}
