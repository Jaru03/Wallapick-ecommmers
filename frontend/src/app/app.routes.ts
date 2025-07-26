import { Routes } from '@angular/router';
import { HomePage } from './components/home-page/home-page';
import { ProductsPage } from './components/products-page/products-page';
import { ProductPage } from './components/product-page/product-page';
import { CartPage } from './components/cart-page/cart-page';

export const routes: Routes = [
    {path: "", component: HomePage},
    {path: "products", component: ProductsPage},
    {path: "products/1", component: ProductPage},
    {path: "cart", component: CartPage},
];
