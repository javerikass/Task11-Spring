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
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.clevertec.bank.config.AppConfig;
import ru.clevertec.bank.config.ApplicationContextUtils;
import ru.clevertec.bank.jdbc.ConnectionPool;

@WebListener
@Slf4j
@NoArgsConstructor
public class InitListener implements ServletContextListener {

    private String shouldInitDB;

    public InitListener(String shouldInitDB) {
        this.shouldInitDB = shouldInitDB;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Context initialization started...");
        AnnotationConfigApplicationContext rootContext = new AnnotationConfigApplicationContext();
        rootContext.register(AppConfig.class);
        rootContext.refresh();
        ApplicationContextUtils.setApplicationContext(rootContext);
        if (isRequiredInitializationDB()) {
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
            connection = ApplicationContextUtils.getApplicationContext()
                .getBean(ConnectionPool.class).getDataSource().getConnection();
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

    private boolean isRequiredInitializationDB() {
        return Boolean.getBoolean(ApplicationContextUtils.getApplicationContext()
            .getBean(InitListener.class).shouldInitDB);
    }

}
