/**
 * 人民币 阿拉伯数字转中文大写数字
 *
 * @author ruidest
 * @date 2018-5-11 16:26:59
 */
public class numberToChinese {
    public static void main(String[] args) {
        System.out.println(convert("903020301"));
    }

    /**
     * 转换主逻辑代码
     *
     * @param num
     * @return
     */
    public static String convert(String num) {
        String integer_part; //整数部分
        String decimal_part = "0"; //小数部分

        /* 判断是否为小数 */
        if (num.contains(".")) {
            integer_part = num.substring(0, num.indexOf("."));
            decimal_part = num.substring(num.indexOf(".") + 1, num.length());
        } else {
            integer_part = num;
        }
        System.out.println("整数部分：" + integer_part + "；小数部分：" + decimal_part);

        boolean integerNotZero = "0".equals(integer_part) ? false : true;//整数部分是否为0

        String retVal = "";//返回值

        if (integerNotZero) {
            retVal = getInteger(integer_part);
        }

        if (decimal_part.length() > 0) {
            retVal = getDecimal(retVal, decimal_part, integerNotZero);
        }
        return retVal;
    }

    /**
     * 处理整数部分
     *
     * @param integer_part
     * @return
     */
    private static String getInteger(String integer_part) {
        String retVal = "";
        /* 将整数部分转换为char型数组 */
        char[] integer_part_arr = integer_part.toCharArray();
        int index = -1;
        int integer_part_length = integer_part_arr.length;//整数部分长度

        /* 从左往右遍历 */
        for (int i = integer_part_length; i > 0; i--) {
            String str = numberToStr(integer_part_arr[integer_part_length - i]);
            if (i % 4 == 1) {//亿、万、个位
                if (i / 4 == 0) {//个位
                    if ("零".equals(str)) {
                        str = "";
                        if (retVal.lastIndexOf("零") == retVal.length() - 1) {
                            retVal = retVal.substring(0, retVal.length() - 1);
                        }
                    }
                    retVal += str;
                } else if (i / 4 == 1) {//万位
                    if ("零".equals(str)) {
                        str = "";
                        if (retVal.lastIndexOf("零") == retVal.length() - 1) {
                            retVal = retVal.substring(0, retVal.length() - 1);
                        }
                        if (retVal.lastIndexOf("億") != retVal.length() - 1) {
                            retVal += str + "萬";
                        }
                    } else {
                        retVal += str + "萬";
                    }

                } else if (i / 4 == 2) {//亿位
                    retVal += str + "億";
                }
            } else if (i % 4 == 2) {//十亿、十万、十位
                boolean zero = "零".equals(str) && i / 2 == 1 && integer_part_length > 2 ||
                        "零".equals(str) && i / 2 == 3 && integer_part_length > 6;
                if (zero) {
                    if (index - 1 == i) {
                        str = "";
                    }
                    retVal += str;
                    index = i;
                } else {
                    retVal += str + "拾";
                }
            } else if (i % 4 == 3) {//百亿、百万、百位
                boolean zero = "零".equals(str) && i / 3 == 1 && integer_part_length > 3 ||
                        "零".equals(str) && i / 3 == 2 && integer_part_length > 7;
                if (zero) {
                    if (index - 1 == i) {
                        str = "";
                    }
                    retVal += str;
                    index = i;
                } else {
                    retVal += str + "佰";
                }
            } else if (i % 4 == 0) {//千亿、千万、千位
                boolean zero = "零".equals(str) && i / 4 == 1 && integer_part_length > 4 ||
                        "零".equals(str) && i / 4 == 2 && integer_part_length > 8;
                if (zero) {
                    retVal += str;
                    index = i;
                } else {
                    retVal += str + "仟";
                }
            }
        }
        return retVal;
    }


    /**
     * 处理小数部分
     *
     * @param retVal       返回值
     * @param decimal_part 小数部分
     * @return
     */
    private static String getDecimal(String retVal, String decimal_part, Boolean integerNotZero) {
        if (integerNotZero) {
            retVal += "圆";
        }
        char[] decimal_part_arr = decimal_part.toCharArray();
        for (int i = 0; i < decimal_part_arr.length; i++) {
            String str = numberToStr(decimal_part_arr[i]);
            if (i == 0) {
                if (!"零".equals(str)) {
                    retVal += str + "角";
                }
            } else if (i == 1) {
                if (!"零".equals(str)) {
                    if ("零".equals(numberToStr(decimal_part_arr[0])) && integerNotZero) {
                        retVal += "零";
                    }
                    retVal += str + "分";
                }
            }
        }
        System.out.println(retVal);
        return retVal;
    }


    /**
     * 阿拉伯数字转中文大写数字
     *
     * @param number
     * @return
     */
    public static String numberToStr(char number) {
        int num = Integer.valueOf(String.valueOf(number));
        switch (num) {
            case 0:
                return "零";
            case 1:
                return "壹";
            case 2:
                return "贰";
            case 3:
                return "叁";
            case 4:
                return "肆";
            case 5:
                return "伍";
            case 6:
                return "陆";
            case 7:
                return "柒";
            case 8:
                return "捌";
            case 9:
                return "玖";
            default:
                break;
        }
    }

}
