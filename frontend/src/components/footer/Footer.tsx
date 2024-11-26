import React from "react";

import Logo from "./components/logo/Logo";
import Links from "./components/links/Links";

import styles from "./Footer.module.css";

const Footer: React.FC = () => {
    const navigationLinks = [
        { text: "Home", href: "#" },
        { text: "About", href: "/about" },
        { text: "News", href: "/news" },
        { text: "Volunteer", href: "/volunteer" },
        { text: "Deliveries", href: "/deliveries" }
    ];

    const supportLinks = [
        { text: "Buymeacoffee", href: "#" },
        { text: "Monobank", href: "#" }
    ];

    const socialLinks = [
        { text: "Telegram", href: "#" },
        { text: "YouTube", href: "#" },
        { text: "Instagram", href: "#" }
    ];

    const accountLinks = [
        { text: "Sign in", href: "/login" },
        { text: "Sign up", href: "/registration" }
    ];

    return (
        <footer className={styles.footer}>
            <Logo />

            <Links heading="Navigation" links={navigationLinks} />
            <Links heading="Support" links={supportLinks} />
            <Links heading="Social media" links={socialLinks} />
            <Links heading="Account" links={accountLinks} />
        </footer>
    );
};

export default Footer;
