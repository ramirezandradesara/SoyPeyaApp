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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.soyhenry.feature.cart.R.drawable.missing_img_product
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.soyhenry.core.model.domain.CartItem
import com.soyhenry.core.model.domain.Product
import com.soyhenry.feature.cart.R
import com.soyhenry.library.ui.components.button.AnimatedIconButton
import androidx.compose.material.icons.filled.Delete

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
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = imagePainter,
            contentDescription = stringResource(R.string.product_image_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(0.8f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1.5f)
        ) {
            Text(
                text = item.product.name,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = "$${item.product.price}",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.wrapContentWidth()
        ) {
            AnimatedIconButton(
                onClick = { onDecrease() },
                imageVector = if (item.quantity == 1) Icons.Default.Delete else Icons.Default.Remove,
                contentDescription = if (item.quantity == 1) stringResource(R.string.remove_item) else stringResource(R.string.decrease_quantity)
            )

            Text(
                text = item.quantity.toString(),
                modifier = Modifier.padding(horizontal = 4.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            AnimatedIconButton(
                onClick = { onIncrease() },
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.increase_quantity)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
    val sampleProduct = Product(
        id = "1",
        name = "Pizza Margherita",
        description = "Delicious pizza with mozzarella and tomato.",
        imgURL = "",
        price = 12.99,
        category = "Pizza"
    )

    val sampleCartItem = CartItem(
        id = 1,
        productId = sampleProduct.id,
        quantity = 2,
        product = sampleProduct
    )

    MaterialTheme {
        CartItemCard(
            item = sampleCartItem,
            onIncrease = {},
            onDecrease = {}
        )
    }
}
