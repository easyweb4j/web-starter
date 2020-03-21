package org.easyweb4j.web.mybatis.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.easyweb4j.web.mybatis.entity.Person;

public interface PersonMapper {

  @Insert("insert into person (name, age, address) values(#{name}, #{age}, #{myAddress})")
  @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=int.class)
  int insert(Person person);

  @Select("select id, name, age, address from person where id = #{id}")
  Person selectOne(Integer id);
}
