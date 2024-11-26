import React from "react";
import { Navigate, Outlet } from "react-router-dom";

interface AuthRoutesProps {
    isAuthenticated: boolean;
}

const AuthRoutes: React.FC<AuthRoutesProps> = ({ isAuthenticated }) => {
    return isAuthenticated ? <Navigate to="/" /> : <Outlet />;
};

export default AuthRoutes;