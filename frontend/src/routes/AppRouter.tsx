import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import ProtectedRoutes from "./ProtectedRoutes";
import AuthRoutes from "./AuthRoutes";
import { routesConfig } from "./routesConfig";

import HomePage from "../pages/home/HomePage";
import NotFoundPage from "../pages/404/NotFoundPage";

import LoginPage from "../features/auth/pages/LoginPage";
import RegisterPage from "../features/auth/pages/RegisterPage";
import ResetPasswordPage from "../features/auth/pages/ResetPasswordPage";

import NewsListPage from "../features/news/pages/NewsListPage";
import NewsDetailsPage from "../features/news/pages/NewsDetailsPage";

import DeliveryListPage from "../features/deliveries/pages/DeliveryListPage";
import DeliveryDetailsPage from "../features/deliveries/pages/DeliveryDetailsPage";

import VolunteerListPage from "../features/volunteers/pages/VolunteerListPage";
import VolunteerDetailsPage from "../features/volunteers/pages/VolunteerDetailsPage";

const AppRouter: React.FC = () => {
    const isAuthenticated = !!localStorage.getItem("authToken");

    return (
        <BrowserRouter>
            <Routes>
                {/* Public routes */}
                <Route path={routesConfig.home} element={<HomePage />} />

                {/* Restricting access for authorized users */}
                <Route element={<AuthRoutes isAuthenticated={isAuthenticated} />}>
                    <Route path={routesConfig.login} element={<LoginPage />} />
                    <Route path={routesConfig.register} element={<RegisterPage />} />
                    <Route path={routesConfig.resetPassword} element={<ResetPasswordPage />} />
                </Route>

                {/* Protected routes */}
                <Route element={<ProtectedRoutes isAuthenticated={isAuthenticated} />}>
                    <Route path={routesConfig.news} element={<NewsListPage />} />
                    <Route path={routesConfig.newsDetails} element={<NewsDetailsPage />} />
                    <Route path={routesConfig.deliveries} element={<DeliveryListPage />} />
                    <Route path={routesConfig.deliveryDetails} element={<DeliveryDetailsPage />} />
                    <Route path={routesConfig.volunteers} element={<VolunteerListPage />} />
                    <Route path={routesConfig.volunteerDetails} element={<VolunteerDetailsPage />} />
                </Route>

                {/* Page 404 */}
                <Route path="*" element={<NotFoundPage />} />
            </Routes>
        </BrowserRouter>
    );
};

export default AppRouter;