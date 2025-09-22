import { Routes } from '@angular/router';
import { HomePage } from './pages/home-page/home-page';
import { ProductsPage } from './pages/products-page/products-page';
import { ProductPage } from './pages/product-page/product-page';
import { CartPage } from './pages/cart-page/cart-page';
import { RegisterPage } from './pages/register-page/register-page';
import { LoginPage } from './pages/login-page/login-page';
import { AccountPage } from './pages/account-page/account-page';
import { SellPage } from './pages/sell-page/sell-page';
import { accountGuard } from './guards/account-guard';
import { SuccessPage } from './pages/success-page/success-page';

export const routes: Routes = [
    {path: "", component: HomePage},
    {path: "products", component: ProductsPage},
    {path: "products/:id", component: ProductPage},
    {path: "cart", component: CartPage},
    {path: "register", component: RegisterPage},
    {path: "login", component: LoginPage},
    {path: "account", component: AccountPage, canActivate: [accountGuard]},
    {path: "sell", component: SellPage},
    {path: "success", component: SuccessPage},
    {path: "**", redirectTo: "/"},
];
