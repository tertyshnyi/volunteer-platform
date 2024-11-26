import React from 'react';
import styles from './Login.module.css';

const Login: React.FC = () => {
    return (
        <div>
            <button className={styles.loginButton}>
                <a href="/login">Sign in</a>
            </button>
            {/*<div className={styles.userMenu}>*/}
            {/*    <img src="/small-logo.png" alt="User" className={styles.userAvatar}/>*/}
            {/*    <span className={styles.userName}>John Doe</span>*/}
            {/*</div>*/}
        </div>
    );
};

export default Login;
