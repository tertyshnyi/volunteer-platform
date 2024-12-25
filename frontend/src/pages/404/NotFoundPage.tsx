import React from 'react';
import { DotLottieReact } from '@lottiefiles/dotlottie-react';
import {routesConfig} from '../../routes/routesConfig';
import styles from './NotFoundPage.module.css';

const NotFoundPage: React.FC = () => {
    return (
        <div className={styles.notFoundContainer}>
            <div className={styles.notFoundContent}>
                <DotLottieReact
                    src="https://lottie.host/038c1dc6-065e-40e7-bdec-9e6834267fe6/exSS6qNt3y.json"
                    loop
                    autoplay
                    className={styles.lottieAnimation}
                />
                <h4 className={styles.notFoundSubtitle}>Oops! Page Not Found</h4>
                <p className={styles.notFoundText}>
                    The page you are looking for doesn’t exist or has been moved.
                </p>
                <a href={routesConfig.home}
                    className={styles.notFoundButton}
                >
                    Take Me Home
                </a>
            </div>
        </div>
    );
};

export default NotFoundPage;