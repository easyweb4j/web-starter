package org.easyweb4j.web.mybatis.test;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.easyweb4j.web.core.context.EasyWeb4JApplicationContext;
import org.easyweb4j.web.core.context.impl.GlobalChainedEasyWeb4JApplicationContext;
import org.easyweb4j.web.core.context.impl.LocalChainedEasyWeb4JApplicationContext;
import org.easyweb4j.web.mybatis.core.scripting.EasyWeb4jApplicationContextAwareXMLLanguageDriver;
import org.easyweb4j.web.mybatis.entity.Person;
import org.easyweb4j.web.mybatis.mapper.PersonMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MybatisTest {

  private SqlSessionFactory sessionFactory;
  private EasyWeb4JApplicationContext<String, Object> context;

  @BeforeClass
  public void setup() {
    EasyWeb4JApplicationContext<String, Object> appContext =
      new LocalChainedEasyWeb4JApplicationContext<>();
    context = new GlobalChainedEasyWeb4JApplicationContext<>();
    context.set(EasyWeb4jApplicationContextAwareXMLLanguageDriver.class.getName(), appContext);

    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/dev");
    dataSource.setUsername("dev");
    dataSource.setPassword("dev");

    TransactionFactory transactionFactory = new JdbcTransactionFactory();
    Environment environment =
      new Environment("development", transactionFactory, dataSource);
    Configuration configuration = new Configuration(environment);
    configuration
      .setDefaultScriptingLanguage(EasyWeb4jApplicationContextAwareXMLLanguageDriver.class);
    configuration.addMapper(PersonMapper.class);
    sessionFactory = new SqlSessionFactoryBuilder().build(configuration);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void customizeLanguageDriver() {
    EasyWeb4JApplicationContext<String, Object> appContext = (EasyWeb4JApplicationContext<String, Object>) context
      .get(EasyWeb4jApplicationContextAwareXMLLanguageDriver.class.getName())
      .orElseThrow(RuntimeException::new);
    appContext.set("myAddress", "name");

    Person person = new Person();
    person.setName("unit test");
    person.setAge(11);

    try (SqlSession session = sessionFactory.openSession()) {
      PersonMapper personMapper = session.getMapper(PersonMapper.class);
      int insertCount = personMapper.insert(person);
      Assert.assertEquals(insertCount, 1);

      Person personInDB = personMapper.selectOne(person.getId());
      Assert.assertNotNull(personInDB);
      Assert.assertEquals(personInDB.getAddress(), appContext.get("myAddress").orElse(null));
    }

  }

}
