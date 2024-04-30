import me.luxoru.databaserepository.configurations.AuthenticationConfigurations;
import me.luxoru.databaserepository.impl.mysql.MySQLContainer;
import me.luxoru.databaserepository.impl.mysql.MySQLDatabase;
import me.luxoru.databaserepository.impl.mysql.data.Table;
import me.luxoru.databaserepository.impl.mysql.data.column.Column;
import me.luxoru.databaserepository.impl.mysql.data.column.impl.IntegerColumn;
import me.luxoru.databaserepository.impl.mysql.data.column.impl.LongColumn;
import me.luxoru.databaserepository.impl.mysql.data.column.impl.VarcharColumn;
import org.junit.Test;

import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class MySQLTest {

    @Test
    public void onConnect(){
        MySQLDatabase database = new MySQLDatabase("mydatabase")
                .connect(new AuthenticationConfigurations(
                        "localhost",
                        3306,
                        "root",
                        "password"
                ));

        MySQLContainer container = database.createContainer();

        Table table = new Table("banTable", new Column[]{
                new LongColumn("userID", false),
                new VarcharColumn("username", false, 255),
                new IntegerColumn("timeOfBan", false, false),
                new IntegerColumn("endOfBan", false, false)
        }, new String[]{"userID"});
        String query = table.getOrCreateTableQuery(false);
        System.out.println(query);
        container.executeQuery(query);

        long uuid = ThreadLocalRandom.current().nextLong(0,999999999);

        container.executeInsert("INSERT INTO banTable (userID, username, timeOfBan, endOfBan) VALUES" +
                "(?,?,?,?)", new Column[]{
                new LongColumn("userID", uuid),
                new VarcharColumn("username", "Luxoru"),
                new IntegerColumn("timeOfBan", 123456),
                new IntegerColumn("endOfBan", 456789)

        });


        container.executeQuery("SELECT * FROM " + table.getName() + " WHERE userID = ?",
                new Column[]{new LongColumn("userID", uuid)},
                resultSet -> {
                    try {
                        while (resultSet.next()) {
                            int userID = resultSet.getInt("userID");
                            String username = resultSet.getString("username");
                            int timeOfBan = resultSet.getInt("timeOfBan");
                            int endOfBan = resultSet.getInt("endOfBan");


                            System.out.println("--------------");
                            System.out.println("userID = " + userID);
                            System.out.println("username = " + username);
                            System.out.println("timeOfBan = " + timeOfBan);
                            System.out.println("endOfBan = " + endOfBan);
                            System.out.println("--------------");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                });


    }

}
