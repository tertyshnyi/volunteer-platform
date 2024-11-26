import React from 'react';

import styles from './Navbar.module.css';

import Menu from './components/menu/Menu';
import Logo from './components/logo/Logo';
import Login from './components/login/Login';

const Navbar: React.FC = () => {

    return (
        <div className={styles.navbar}>
            <div className={styles.left}>
                <Logo />
                <Menu />
            </div>

            <div className={styles.right}>
                <Login />
            </div>
        </div>
    );
};

export default Navbar;
