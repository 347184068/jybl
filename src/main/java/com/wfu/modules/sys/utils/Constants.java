package com.wfu.modules.sys.utils;

/**
 * @Author XuYunXuan
 * @Date 2017/6/3 10:46
 */
public class Constants {

    //最大推荐书籍数量
    public static final int MAX_RECOMMEND_COUNT = 5;

    //最大借阅数量
    public static final int MAX_BORROW_COUNT  = 2;

    //最大不良记录，超出即不可续借、不可借书
    public static final int MAX_BADRECORD_COUNT = 10;

    //图书续借
    public static final String BOOK_RENEW = "1";

    //图书超期
    public static final String BOOK_OVERTIME = "1";

    //管理员还书确认
    public static final String BOOK_CONFIRM = "1";

    //图书归还
    public static final String BOOK_RETURN = "1";
}
