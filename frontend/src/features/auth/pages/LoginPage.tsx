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
                    <p className={styles.subtitle}>
                        We are happy to see you again! Please enter your login details
                        to access your account and pick up where you left off so that
                        there are fewer hungry people in the world!
                    </p>
                </div>
                <div className={styles.right}>
                    <h1 className={styles.title}>Sign in.</h1>
                    <div className={styles.context}>
                        <div className={styles.form}>
                            <label className={styles.label}>
                                <input type="email" className={styles.input} placeholder="Email*"/>
                            </label>

                            <label className={styles.label}>
                                <input type="tel" className={styles.input} placeholder="Phone number*"/>
                            </label>

                            <label className={styles.label}>
                                <input type="password" className={styles.input} placeholder="Password*"/>
                            </label>

                            <div className={styles.buttons}>
                                <label className={styles.checkboxLabel}>
                                    <input type="checkbox" className={styles.checkbox}/>
                                    <span className={styles.checkboxText}>Remember me</span>
                                </label>

                                <a href={routesConfig.resetPassword} className={styles.resetPass}>
                                    <span>Forgot password?</span>
                                </a>
                            </div>
                        </div>

                        <button type="submit" className={styles.button}>Sign in now</button>

                        <div className={styles.social_login}>
                            <a className={styles.logo} href="#">
                                <img
                                    src="https://images.rawpixel.com/image_png_800/czNmcy1wcml2YXRlL3Jhd3BpeGVsX2ltYWdlcy93ZWJzaXRlX2NvbnRlbnQvbHIvdjk4Mi1kNS0xMF8xLnBuZw.png"
                                    alt="Linkedin"/>
                            </a>
                            <a className={styles.logo} href="#">
                                <img
                                    src="https://image.similarpng.com/very-thumbnail/2020/06/Logo-google-icon-PNG.png"
                                    alt="Google"/>
                            </a>
                            <a className={styles.logo} href="#">
                                <img
                                    src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b8/2021_Facebook_icon.svg/2048px-2021_Facebook_icon.svg.png"
                                    alt="Facebook"/>
                            </a>
                        </div>

                        <p className={styles.register}>
                            Don't have an account?
                            <a href="#">Register</a>
                        </p>

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