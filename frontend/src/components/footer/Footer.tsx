import React from "react";

import styles from "./Footer.module.css";

const Footer = () => {
    return (
        <footer className={styles.footer}>
                <div className={styles.section}>
                    <img src="/big-logo.png" alt="Logo" className={styles.logo}/>
                    <p className={styles.description}>
                        Our mission is to connect people who are willing to help with those in need.
                        We provide opportunities to volunteer, help communities and make the world a better place
                        <a href="mailto:hearthand@gmail.com"> hearthand@gmail.com</a>.
                    </p>
                </div>
                <div className={styles.section}>
                    <h2 className={styles.heading}>Navigation</h2>
                    <ul className={styles.links}>
                        <li><a href="#">Home</a></li>
                        <li><a href="/about">About</a></li>
                        <li><a href="/news">News</a></li>
                        <li><a href="/volunteer">Volunteer</a></li>
                        <li><a href="/deliveries">Deliveries</a></li>
                    </ul>
                </div>
            <div className={styles.section}>
                    <h2 className={styles.heading}>Support (?)</h2>
                    <ul className={styles.links}>
                        <li><a href="#">Buymeacoffee</a></li>
                        <li><a href="#">Monobank</a></li>
                    </ul>
                </div>
                <div className={styles.section}>
                    <h2 className={styles.heading}>Social media</h2>
                    <ul className={styles.links}>
                        <li><a href="#">Telegram</a></li>
                        <li><a href="#">YouTube</a></li>
                        <li><a href="#">Instagram</a></li>
                    </ul>
                </div>
                <div className={styles.section}>
                    <h2 className={styles.heading}>Account</h2>
                    <ul className={styles.links}>
                        <li><a href="/login">Sign in</a></li>
                        <li><a href="/registration">Sign up</a></li>
                    </ul>
                </div>
        </footer>
    );
};

export default Footer;
