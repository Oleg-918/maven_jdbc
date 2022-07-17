package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//Класс отвечает за коннект с базой данных.
public class Util {
    private Util() {}
    private static Connection connection = null;
    private static Util instance = null;

    // Геттер на экземпляр соединения.
    public static Util getInstance() {
        if (null == instance) {
            instance = new Util();
        }
        return instance;
    }

    //Геттер на экземпляр соединения.
    public Connection getConnection() {
        try {
            if (null == connection || connection.isClosed()) {
                Properties props = getProps();
                connection = DriverManager
                        .getConnection(props.getProperty("db.url"), props.getProperty("db.username"), props.getProperty("db.password"));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //Геттер на файл с настройками соединения.
    private static Properties getProps() throws IOException {
       Properties props = new Properties();
       try (InputStream in = Files.newInputStream(Paths.get((Util.class.getResource("/database.properties").toURI())))) {
           props.load(in);
           return props;
       } catch (IOException | URISyntaxException e) {
           throw new IOException("Database config file not found");
       }
   }
}
