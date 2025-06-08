package com.soyhenryfeature.products.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.soyhenryfeature.products.model.Product

@Composable
fun ProductsView(
    navController: NavController
) {
    val products = listOf(
        Product(1, "Laptop", 999.99, "High performance laptop"),
        Product(2, "Smartphone", 699.99, "Latest model smartphone"),
        Product(3, "Headphones", 149.99, "Noise cancelling headphones"),
        Product(4, "Keyboard", 79.99, "Mechanical keyboard"),
        Product(5, "Mouse", 49.99, "Wireless ergonomic mouse")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(products) { product ->
            ProductItem(product = product)
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProductsViewPreview(){
    val navController = rememberNavController()

    ProductsView(
        navController = navController
    )
}