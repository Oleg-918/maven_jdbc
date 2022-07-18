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
    private Util() {
    }

    private static Connection connection = null;
    private static Util instance = null;

    private static Properties props= null;

    // Геттер на экземпляр соединения.
    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    //Геттер на экземпляр соединения.
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Properties props = getProps();
                connection = DriverManager
                        .getConnection(props.getProperty("db.url"), props.getProperty("db.username"), props.getProperty("db.password"));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static Properties getProps() throws IOException {
        try {
            if (props == null) {
                props = new Properties();
                InputStream in = Files.newInputStream(Paths.get("src/main/java/resourse/database.properties"));
                props.load(in);
            }
        } catch (IOException e) {
            throw new IOException("Database config file not found");
        }
        return props;
    }
}


