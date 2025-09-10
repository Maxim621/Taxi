package com.solvd.taxi.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtil {
    private static final Logger logger = LogManager.getLogger(MyBatisUtil.class);
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            logger.info("MyBatis SqlSessionFactory initialized successfully");
        } catch (IOException e) {
            logger.error("Error initializing MyBatis: {}", e.getMessage(), e);
            throw new RuntimeException("Error initializing MyBatis: " + e.getMessage(), e);
        }
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}