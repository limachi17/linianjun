package com.moxian.common.login.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Type {

    /**
     * 
    * @Title: validatePhone 
    * @param: String countryCode  国家码 
    * @param: String phoneNo  手机号码
    * @Description: Validate the phone number for country
    * @return boolean
     */
    public static boolean isPhoneNumber(String countryCode, String phoneNo) {

        String regExp = null;
        boolean flag = false;

        // in China
        if (countryCode.contains("86")) {
            regExp = "^(86){1}((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0-9])|(177))\\d{8}$";
        } else {
            //other country
            regExp = "^("+countryCode+"){1}[0-9]*$";
        }

        Pattern regex = Pattern.compile(regExp);
        Matcher matcher = regex.matcher(phoneNo);
        flag = matcher.matches();
        return flag;
        
    }
    
    /**
     * 
    * @Title: validatePhone 
    * @param: 
    * @Description: 验证中国手机号码，不带86 
    * @return boolean
     */
    public static boolean isPhoneNumber(String phoneNo) {

        String regExp = null;
        boolean flag = false;

        // in China
            regExp = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0-9])|(177))\\d{8}$";

        Pattern regex = Pattern.compile(regExp);
        Matcher matcher = regex.matcher(phoneNo);
        flag = matcher.matches();
        return flag;
        
    }
    
    /**
     * 检测邮箱地址
    * @Title: validatePhone 
    * @param: String email
    * @Description: TODO(检测邮箱格式是否正确)
    * @return boolean
     */
    public static boolean isEmail(String email){

        String regExp = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
        boolean flag = false;

        Pattern regex = Pattern.compile(regExp);
        Matcher matcher = regex.matcher(email);
        flag = matcher.matches();
        
        return flag;
    }
    
    /**
     * 检测邮箱地址
    * @Title: validatePhone 
    * @param: String email
    * @Description: TODO(检测邮箱格式是否正确)
    * @return boolean
     */
    public static boolean isId(String email){

        String regExp = "^\\d+$";
        boolean flag = false;

        Pattern regex = Pattern.compile(regExp);
        Matcher matcher = regex.matcher(email);
        flag = matcher.matches();
        
        return flag;
    }

}