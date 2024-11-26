import React, {useState} from 'react';
import MenuIcon from '@mui/icons-material/Menu';
import styles from './Menu.module.css';

const Menu: React.FC = () => {

    const [isMenuOpen, setIsMenuOpen] = useState(false);

    const toggleMenu = () => {
        setIsMenuOpen((prev) => !prev);
    };


    return (
        <div className={styles.menuContainer}>
            <MenuIcon className={styles.menuIcon} onClick={toggleMenu} />

            <ul className={`${styles.menu} ${isMenuOpen ? styles.openMenu : ''}`}>
                <li className={styles.menuItem}><a href="/">Home</a></li>
                <li className={styles.menuItem}><a href="/about">About</a></li>
                <li className={styles.menuItem}><a href="/news">News</a></li>
                <li className={styles.menuItem}><a href="/volunteers">Volunteers</a></li>
                <li className={styles.menuItem}><a href="/deliveries">Deliveries</a></li>
            </ul>
        </div>
    );
};

export default Menu;
