package com.soyhenryfeature.products.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.soyhenryfeature.products.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import com.soyhenry.core.model.domain.Product
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.soyhenry.library.ui.components.button.AnimatedIconButton
import kotlinx.coroutines.delay

@Composable
fun ProductItem(
    product: Product,
    modifier: Modifier = Modifier,
    onAddToCart: (Product) -> Unit,
) {
    val imagePainter = if (product.imgURL.isNotBlank()) {
        rememberAsyncImagePainter(model = product.imgURL)
    } else {
        painterResource(id = R.drawable.missing_img_product)
    }

    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.85f else 1f,
        label = "scaleAnimation"
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = imagePainter,
                contentDescription = stringResource(R.string.product_image_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
        }

        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(top = 8.dp)
            )

            Text(
                text = product.description,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                AnimatedIconButton(
                    onClick = { onAddToCart(product) },
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_to_cart_description)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductItemPreview() {
    val sampleProduct = Product(
        id = "1",
        name = "Sample Product",
        description = "A delicious sample product for preview purposes.",
        imgURL = "",
        price = 29.99,
        category = "Pizza",
    )

    MaterialTheme {
        ProductItem(
            product = sampleProduct,
            modifier = Modifier.padding(16.dp).width(180.dp),
            onAddToCart = { }
        )
    }
}