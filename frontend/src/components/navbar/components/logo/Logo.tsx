import React from 'react';
import styles from './Logo.module.css';

const Logo: React.FC = () => {
    return (
        <div className={styles.logo}>
            <img src="/small-logo.png" alt="Logo" className={styles.logoIcon} />
            <span className={styles.logoText}>HeartHands</span>
        </div>
    );
};

export default Logo;
