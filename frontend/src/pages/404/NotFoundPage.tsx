
import React from 'react';
import { DotLottieReact } from '@lottiefiles/dotlottie-react';
import { useNavigate } from 'react-router-dom';
import styles from './NotFoundPage.module.css';

const NotFoundPage: React.FC = () => {
    const navigate = useNavigate();

    const goToHomePage = () => {
        navigate('/');
    };

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
                <button
                    onClick={goToHomePage}
                    className={styles.notFoundButton}
                >
                    Take Me Home
                </button>
            </div>
        </div>
    );
};

export default NotFoundPage;
