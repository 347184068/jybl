package com.wfu.modules.weixin.service;

import com.wfu.common.utils.StringUtils;
import com.wfu.modules.sys.dao.BookDao;
import com.wfu.modules.sys.dao.BookPublisherDao;
import com.wfu.modules.sys.dao.CategoryDao;
import com.wfu.modules.sys.entity.Book;
import com.wfu.modules.sys.entity.BookPublisher;
import com.wfu.modules.sys.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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


    public List<Book> getConnectionBook(String bookIsbn, String categoryId, Integer count) {
        Book book = new Book();
        book.setBookCategoryid(categoryId);
        book.setRandCount(count + 1);
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


    public List<Category> getAllCategory() {
        Category category = new Category();
        return categoryDao.findList(category);
    }


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
