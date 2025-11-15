package com.aggregation.shared;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final DateTimeFormatter YYYY_MM_DD_T_HH_MM_SS_Z = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    public static final String DEFAULT_PAGE = "1";
    public static final String DEFAULT_SIZE = "50";
}
