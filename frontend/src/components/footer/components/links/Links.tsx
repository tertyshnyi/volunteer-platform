import React from "react";

import styles from "./Links.module.css";

interface LinksProps {
    heading: string;
    links: { text: string; href: string }[];
}

const Links: React.FC<LinksProps> = ({ heading, links }) => {
    return (
        <div className={styles.section}>
            <h2 className={styles.heading}>{heading}</h2>
            <ul className={styles.links}>
                {links.map((link, index) => (
                    <li key={index}>
                        <a href={link.href}>{link.text}</a>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Links;
