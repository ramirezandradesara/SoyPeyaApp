package com.soyhenry.feature.cart.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.soyhenry.feature.cart.R.drawable.missing_img_product
import androidx.compose.ui.res.stringResource
import com.soyhenry.core.domain.CartItem
import com.soyhenry.feature.cart.R

@Composable
fun CartItemCard(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    val imagePainter = if (item.product.imgURL.isNotBlank()) {
        rememberAsyncImagePainter(model = item.product.imgURL)
    } else {
        painterResource(id = missing_img_product)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(12.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = imagePainter,
            contentDescription = stringResource(R.string.product_image_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1.5f)
        ) {
            Text(text = item.product.name, fontWeight = FontWeight.SemiBold)
            Text(
                text = item.product.category,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = "$${item.product.price}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.wrapContentWidth()
        ) {
            IconButton(
                onClick = { onDecrease() },
                modifier = Modifier.size(25.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            ) {
                Icon(
                    modifier = Modifier.size(15.dp),
                    imageVector = Icons.Default.Remove,
                    contentDescription = stringResource(R.string.decrease_quantity),
                    tint = Color.White,
                )
            }

            Text(
                text = item.quantity.toString(),
                modifier = Modifier.padding(horizontal = 4.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            IconButton(
                onClick = { onIncrease() },
                modifier = Modifier.size(25.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            ) {
                Icon(
                    modifier = Modifier.size(15.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.increase_quantity),
                    tint = Color.White,
                )
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    val cartItemWithProductEntity = CartItemWithProductEntity(
        product = ProductEntity(
            id = "id_1",
            productName = "Melting Cheese Pizza",
            category = "Pizza",
            price = 11.88,
            imageURL = "",
        ),
        cartItem = CartItemEntity(
            id = 1,
            productId = "id_1",
            quantity = 2
        ),
    )

    MaterialTheme {
        CartItemCard(
            item = cartItemWithProductEntity,
            onIncrease = {},
            onDecrease = {}
        )
    }
} */