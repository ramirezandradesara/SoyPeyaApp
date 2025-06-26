package com.soyhenry.feature.cart.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.soyhenry.feature.cart.R.drawable.missing_img_product
import com.soyhenry.feature.cart.viewmodel.CartViewModel

data class CartItem(
    val name: String, val description: String, val price: Double, val imgURL: String
)

@Composable
fun CartView(
    navController: NavController, viewModel: CartViewModel = hiltViewModel()
) {
    val image =
        "https://media.istockphoto.com/id/1442417585/es/foto/persona-recibiendo-un-pedazo-de-pizza-de-pepperoni-con-queso.jpg?s=612x612&w=0&k=20&c=Uk4fj96OIDxE4v2S5sRRXRY_gZ899_TE6jGD-T-TysI="

    val cartItems = listOf(
        CartItem("Melting Cheese Pizza", "Pizza Italiano", 11.88, image),
        CartItem("Veggie Delight", "Pizza Vegana", 10.50, image),
        CartItem("Pepperoni Supreme", "Pizza Americana", 12.25, image)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Cart 🛒",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        cartItems.forEach { item ->
            CartItemCard(item)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun CartItemCard(item: CartItem) {
    var quantity by remember { mutableStateOf(1) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF6F6F6), RoundedCornerShape(16.dp))
            .padding(12.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen (1/3 del ancho)
        val imagePainter = if (item.imgURL.isNotBlank()) {
            rememberAsyncImagePainter(model = item.imgURL)
        } else {
            painterResource(id = missing_img_product)
        }

        Image(
            painter = imagePainter,
            contentDescription = "Product image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Info (nombre, descripción, precio) ~1.5/3
        Column(
            modifier = Modifier.weight(1.5f)
        ) {
            Text(text = item.name, fontWeight = FontWeight.SemiBold)
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = "$${item.price}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }

        // Contador (+ / -) ~0.5/3
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(0.5f),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = { if (quantity > 1) quantity-- },
                modifier = Modifier
                    .size(20.dp)
                    .background(Color(0xFF4CAF50), shape = CircleShape)
            ) {
                Text("-", color = Color.White, fontWeight = FontWeight.Bold)
            }
            Text(
                text = quantity.toString(), modifier = Modifier.padding(horizontal = 4.dp)
            )
            IconButton(
                onClick = { quantity++ },
                modifier = Modifier
                    .size(20.dp)
                    .background(Color(0xFF4CAF50), shape = CircleShape)
            ) {
                Text("+", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CartViewPreview() {
    val navController = rememberNavController()
    val viewModel: CartViewModel = hiltViewModel()

    CartView(navController, viewModel)
}
