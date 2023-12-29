package ru.clevertec.bank.servlet.listener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import ru.clevertec.bank.jdbc.ConnectionPool;
import ru.clevertec.bank.jdbc.PropertiesManager;

@WebListener
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Context initialization started...");
        String initBD = PropertiesManager.getProperty("initBD");
        if (Boolean.parseBoolean(initBD)) {
            initBD();
            log.info("Database initialized.");
        } else {
            log.info("Database initialization skipped.");
        }
        log.info("Context initialization completed.");
    }


    private void initBD() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionPool.getDataSource().getConnection();
            statement = connection.createStatement();

            InputStream inputStream = InitListener.class.getClassLoader()
                .getResourceAsStream("init.sql");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder script = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line);
            }
            reader.close();
            statement.executeUpdate(script.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null && statement != null) {
                    statement.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

}
