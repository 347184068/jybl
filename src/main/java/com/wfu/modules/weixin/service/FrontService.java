package com.wfu.modules.weixin.service;

import com.wfu.common.utils.StringUtils;
import com.wfu.modules.sys.dao.BookDao;
import com.wfu.modules.sys.dao.BookPublisherDao;
import com.wfu.modules.sys.dao.CategoryDao;
import com.wfu.modules.sys.dao.UserInfoDao;
import com.wfu.modules.sys.entity.Book;
import com.wfu.modules.sys.entity.BookPublisher;
import com.wfu.modules.sys.entity.Category;
import com.wfu.modules.sys.entity.UserInfo;
import com.wfu.modules.weixin.dao.WxUserDao;
import com.wfu.modules.weixin.entity.WxUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static oracle.net.aso.C01.r;


/**
 * @Author XuYunXuan
 * @Date 2017/6/13 15:24
 */
@Service
@Transactional(readOnly = true)
public class FrontService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private BookPublisherDao bookPublisherDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private WxUserDao wxUserDao;


    public boolean isUserExistByOpenId(String openId){
        UserInfo userInfo = userInfoDao.getUserByOpenId(openId);
        if(userInfo!=null){
            return true;
        }else {
            return false;
        }
    }

    public UserInfo findUserByOpenId(String openId){
        UserInfo userInfo = userInfoDao.getUserByOpenId(openId);
        WxUser wxUser = wxUserDao.findByOpenId(openId);
        String userImg = wxUser.getHeadimgurl();
        if(null != userImg){
            userInfo.setWxImg(userImg);
        }
        return userInfo;
    }


    /**
     * 随机获取相关图书
     * @return 图书列表
     */
    public List<Book> getConnectionBook(String bookIsbn, String categoryId, Integer count) {
        Book book = new Book();
        book.setBookCategoryid(categoryId);
        if(null != count) book.setRandCount(count + 1);
        List<Book> books = bookDao.findList(book);
        List<Book> res = new ArrayList<Book>();
        for (Book b : books) {
            if (!b.getBookIsbn().equals(bookIsbn)) {
                res.add(b);
            }
        }
        int circle = res.size() > count ? count : res.size();
        for(int i = 0 ; i < res.size() - circle ;i++){
            res.remove(0);
        }
        return res;
    }

    /**
     * 查询图书

     * @return 图书列表
     */
    public List<Book> searchBookByKeyWord(String type, String keyWord) {
        Book book = new Book();
        if ("bookName".equals(type)) {
            book.setBookName(keyWord);
        } else if ("bookIsbn".equals(type)) {
            book.setBookIsbn(keyWord);
        } else if ("bookAuthor".equals(type)) {
            book.setBookAuthor(keyWord);
        } else if ("publisher".equals(type)) {
            BookPublisher queryObj = new BookPublisher();
            queryObj.setPublisherName(keyWord);
            List<BookPublisher> publisherList = bookPublisherDao.findList(queryObj);
            BookPublisher publisher = publisherList.size() == 0 ? null : publisherList.get(0);
            book.setBookPublisherid(publisher == null ? null : publisher.getPublisherId());
        }
        return bookDao.findList(book);
    }


    public Book getBookById(String id) {
        return bookDao.get(id);
    }

    /**
     * 获取所有分类
     * @return 分类列表
     */
    public List<Category> getAllCategory() {
        Category category = new Category();
        return categoryDao.findList(category);
    }

    /**
     * 随机获取图书
     * @return 图书列表
     */
    public List<Book> getRandBook(Integer count) {
        Book book = new Book();
        if (null != count) {
            book.setRandCount(count);
        }
        return bookDao.findList(book);
    }

    public List<Book> getBookByCategory(String categoryId) {
        Category category = getCategoryById(categoryId);
        return getBookByCategory(category);
    }

    /**
     * 获取某一个分类下的图书
     * @return 一个分类
     */
    public Category showBookByCategory(String categoryId, Integer showCount) {
        Category category = getCategoryById(categoryId);
        List<Book> bookList = null;
        List<Book> res = new ArrayList<Book>();
        if (null != category) {
            bookList = getBookByCategory(category);
        }
        if (null != bookList) {
            showCount = (null == showCount) ? bookList.size() : showCount;
            int circleCount = bookList.size() > showCount ? showCount : bookList.size();
            for (int i = 0; i < circleCount; i++) {
                res.add(bookList.get(i));
            }
        }
        category.setBookList(res);
        return category;
    }


    private List<Book> getBookByCategory(Category category) {
        List<Book> list = null;
        if (null != category) {
            Book book = new Book();
            book.setBookCategoryid(category.getId());
            list = bookDao.findList(book);
        } else {
            list = bookDao.findList(new Book());
        }
        return list;
    }

    private Category getCategoryById(String categoryId) {
        Category category = null;
        if (StringUtils.isNotBlank(categoryId)) {
            Category queryObj = new Category();
            queryObj.setId(categoryId);
            category = categoryDao.get(queryObj);
        }
        return category;
    }

}
