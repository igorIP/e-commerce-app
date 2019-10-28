package com.projects.shop.config;

import org.hibernate.dialect.MySQL8Dialect;

public class MySQL8UnicodeDialect extends MySQL8Dialect {

    /**
     * Custom MySQL dialect to support full Unicode in MySQL databases
     * (MySQL’s utf8 only allows you to store 5.88% of all possible Unicode code points).
     * (utf8mb4 maps to proper UTF-8 and thus fully supports Unicode, including astral symbols).
     */

    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
    }
}

