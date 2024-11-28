import React from 'react';

import styles from "./LoginPage.module.css";

import {routesConfig} from "../../../routes/routesConfig";

const LoginPage: React.FC = () => {
    return (
        <div className={styles.container}>
            {/*<img
                src="https://3.files.edl.io/0b2a/24/01/29/162828-0e29dfbb-09a6-4ef3-8535-b86eae10381e.jpeg"
                alt="background"
             />*/}

            <div className={styles.login}>
                <div className={styles.left}>
                    <h1 className={styles.title}>Welcome back.</h1>
                    <p className={styles.subtitle}>We're glad to see you again! Please enter your login details
                        to access your account and continue where you left off.
                    </p>
                </div>
                <div className={styles.right}>
                    <h1 className={styles.title}>Sign in.</h1>
                    <div className={styles.form}>
                        <label className={styles.label}>
                            <input type="email" className={styles.input} placeholder="Email*" />
                        </label>

                        <br/>
                        <label className={styles.label}>
                            <input type="tel" className={styles.input} placeholder="Phone number*" />
                        </label>

                        <br/>
                        <label className={styles.label}>
                            <input type="password" className={styles.input} placeholder="Password*" />
                        </label>

                        <br/>
                        <label className={styles.checkboxLabel}>
                            <input type="checkbox" className={styles.checkbox}/>
                            <span className={styles.checkboxText}>Remember me</span>
                        </label>

                        <br/>
                        <a href={routesConfig.resetPassword} className={styles.resetPass}>
                            Forgot password?
                        </a>

                        <br/>
                        <button type="submit" className={styles.button}>Sign in now</button>

                        {/*<p>or</p>*/}

                        {/*<a className={styles.logo} href="#">*/}
                        {/*    <img src="https://cdn1.iconfinder.com/data/icons/google-s-logo/150/Google_Icons-09-512.png" alt="Google" />*/}
                        {/*</a>*/}

                        <p className={styles.desc}>
                            By clicking on "Sign in now" you agree to <br/>
                            <a href="#">Terms of Service</a> | <a href="#">Privacy Policy</a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default LoginPage;