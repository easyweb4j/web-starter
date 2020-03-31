package org.easyweb4j.web.boot.test;

import org.easyweb4j.web.core.context.EasyWeb4JApplicationContext;
import org.easyweb4j.web.core.dao.DeletedStatus;
import org.easyweb4j.web.mybatis.entity.House;
import org.easyweb4j.web.mybatis.mapper.HouseMapper;
import org.easyweb4j.web.mybatis.spring.config.EnableEasyWeb4jMybatis;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Mybatis 集成测试 1. 测试配置项 2. 测试上下文 3. 测试handler
 *
 * @author Ray(linxray @ gmail.com)
 * @date 2020/03/31
 * @since 1.0
 */
@SpringBootTest
@SpringBootApplication
@EnableEasyWeb4jMybatis
@Rollback
@MapperScan("org.easyweb4j.web.mybatis.mapper")
public class MybatisStaterTest extends AbstractTransactionalTestNGSpringContextTests {

  @Autowired
  private EasyWeb4JApplicationContext<String, Object> easyWeb4JApplicationContext;

  @Autowired
  private HouseMapper houseMapper;

  @BeforeMethod
  public void setup() {
    Assert.assertNotNull(easyWeb4JApplicationContext);
    easyWeb4JApplicationContext.set("DEFAULT_FLOOR", 1000);
  }

  @Test
  public void house() {
    // normal insert house
    // select and check data
    // insert with context
    // select and check data
    House houseOne = new House();
    houseOne.setName("house one");
    houseOne.setFloorNumber(1);
    houseOne.setState(DeletedStatus.NORMAL);
    houseOne.setStateTs(DeletedStatus.DELETED);

    Assert.assertEquals(houseMapper.insert(houseOne), 1);

    House house = houseMapper.selectOne(houseOne.getId());
    Assert.assertEquals(house, houseOne);

    House houseTwo = new House();
    houseTwo.setName("house Two");
    houseTwo.setFloorNumber(1);
    houseTwo.setState(DeletedStatus.NORMAL);
    houseTwo.setStateTs(DeletedStatus.DELETED);

    Assert.assertEquals(houseMapper.insert(houseTwo), 1);

    house = houseMapper.selectOne(houseTwo.getId());
    Assert.assertNotEquals(house, houseTwo);
    Assert.assertEquals(house.getName(), houseTwo.getName());
    Assert.assertEquals(easyWeb4JApplicationContext.get("DEFAULT_FLOOR"), houseTwo.getFloorNumber());


  }

}
