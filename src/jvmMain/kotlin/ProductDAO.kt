import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement

class ProductDAO {

    private val jdbcUrl = "jdbc:h2:~/test;AUTO_SERVER=TRUE"
    private val jdbcUser = "sa"
    private val jdbcPassword = ""

    init {
        createTable()
    }

    private fun createTable() {
        val createTableSQL = """
            CREATE TABLE IF NOT EXISTS products (
                id INT PRIMARY KEY,
                name VARCHAR(50),
                price FLOAT,
                description VARCHAR(100),
                brand VARCHAR(50),
                category VARCHAR(50)
            )
        """
        getConnection().use { connection ->
            connection.createStatement().use { statement ->
                statement.execute(createTableSQL)
            }
        }
    }

    fun clearTable() {
        val deleteSQL = "DELETE FROM products"
        getConnection().use { connection ->
            connection.createStatement().use { statement ->
                statement.executeUpdate(deleteSQL)
            }
        }
    }

    private fun getConnection(): Connection {
        return DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword)
    }

    fun createProduct(product: Product): Int {
        val insertSQL = """
            INSERT INTO products (id, name, price, description, brand, category) VALUES (?, ?, ?, ?, ?, ?)
        """
        getConnection().use { connection ->
            connection.prepareStatement(insertSQL).use { preparedStatement ->
                preparedStatement.setInt(1, product.id)
                preparedStatement.setString(2, product.name)
                preparedStatement.setFloat(3, product.price)
                preparedStatement.setString(4, product.description)
                preparedStatement.setString(5, product.brand)
                preparedStatement.setString(6, product.category)

                val affectedRows = preparedStatement.executeUpdate()
                if (affectedRows > 0) {
                    return product.id
                } else {
                    throw Exception("Failed to insert product")
                }
            }
        }
    }
}
