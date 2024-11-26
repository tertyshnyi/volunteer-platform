import React from "react";

import styles from "./Logo.module.css";

const Logo: React.FC = () => {
    return (
        <div className={styles.section}>
            <img src="/big-logo.png" alt="Logo" className={styles.logo}/>
            <p className={styles.description}>
                Our mission is to connect people who are willing to help with those in need.
                We provide opportunities to volunteer, help communities and make the world a better place
                <a href="mailto:hearthand@gmail.com"> hearthand@gmail.com</a>.
            </p>
        </div>
    );
};

export default Logo;
