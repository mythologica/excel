package org.example.common.excel.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.io.FileReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ExcelUtils {

    private static final String readPath = "C:\\data\\dev\\workspace\\java\\excel\\src\\main\\resources";
    private static final String writePath = "C:\\data\\dev\\workspace\\java\\excel\\src\\main\\resources";


    public static String getReadPath(String subPath) {
        return readPath + subPath.replaceAll("../", "_");
    }

    public static String getWritePath(String subPath) {
        return writePath + subPath.replaceAll("../", "_");
    }

}
