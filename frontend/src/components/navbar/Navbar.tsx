import React from 'react';

import styles from './Navbar.module.css';

const Navbar: React.FC = () => {
    return (
        <div className={styles.navbar}>
            <div className={styles.left}>
                <div className={styles.logo}>
                    <img src="/small-logo.png" alt="Logo" className={styles.logoIcon}/>
                    <span className={styles.logoText}>HeartHands</span>
                </div>
            </div>

            <ul className={styles.menu}>
                <li className={styles.menuItem}>
                    <a href="/">Home</a>
                </li>
                <li className={styles.menuItem}>
                    <a href="/about">About</a>
                </li>
                <li className={styles.menuItem}>
                    <a href="/news">News</a>
                </li>
                <li className={styles.menuItem}>
                    <a href="/volunteers">Volunteers</a>
                </li>
                <li className={styles.menuItem}>
                    <a href="/deliveries">Deliveries</a>
                </li>
            </ul>

            <div className={styles.right}>
                <button className={styles.loginButton}>
                    <a href="/login">Sign in</a>
                </button>
                {/*<div className={styles.userMenu}>*/}
                {/*    <img src="/small-logo.png" alt="User" className={styles.userAvatar}/>*/}
                {/*    <span className={styles.userName}>John Doe</span>*/}
                {/*</div>*/}
            </div>
        </div>
    );
};

export default Navbar;
