fun main() {
    val product = Product(2, "PC", 1567.93f, "The latest PC model", "HP", "Electronics")
    val productId = ProductDAO().createProduct(product)
    println("Product ID: $productId")
}
