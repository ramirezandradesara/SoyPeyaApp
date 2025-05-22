package com.soyhenry.appmodule

class ProductRepositoryFake: ProductRepository {
    override fun listaDeProductos(){

    }
}

interface ProductRepository {
    fun listaDeProductos()
}