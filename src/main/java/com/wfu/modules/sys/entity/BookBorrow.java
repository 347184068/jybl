/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wfu.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.wfu.common.persistence.DataEntity;

/**
 * 借书信息管理Entity
 * @author 徐韵轩
 * @version 2017-06-03
 */
public class BookBorrow extends DataEntity<BookBorrow> {
	
	private static final long serialVersionUID = 1L;
	private String borrowId;		// borrow_id
	private String userId;		// user_id
	private String bookId;		// book_id
	private String isReturn;		// 是否归还
	private String borrowTime;		// 借阅时间
	private String returnTime;		// 归还时间
	private String isRenew;		// 是否续借
	private String isOvertime;		// 是否超期
	private String isConfirm;		// 管理员确认借阅书籍无误

    //用于查询
    //书名
    private String bookName;
    //ISBN
    private String bookIsbn;

    //用户名
    private String bookUserName;

    //用户微信ID
    private String bookUserWechatId;

	public BookBorrow() {
		super();
	}

	public BookBorrow(String id){
		super(id);
	}

	@Length(min=1, max=255, message="borrow_id长度必须介于 1 和 255 之间")
	public String getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}
	
	@Length(min=0, max=255, message="user_id长度必须介于 0 和 255 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=255, message="book_id长度必须介于 0 和 255 之间")
	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	
	@Length(min=0, max=255, message="是否归还长度必须介于 0 和 255 之间")
	public String getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(String isReturn) {
		this.isReturn = isReturn;
	}
	
	@Length(min=0, max=255, message="借阅时间长度必须介于 0 和 255 之间")
	public String getBorrowTime() {
		return borrowTime;
	}

	public void setBorrowTime(String borrowTime) {
		this.borrowTime = borrowTime;
	}
	
	@Length(min=0, max=255, message="归还时间长度必须介于 0 和 255 之间")
	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
	
	@Length(min=0, max=255, message="是否续借长度必须介于 0 和 255 之间")
	public String getIsRenew() {
		return isRenew;
	}

	public void setIsRenew(String isRenew) {
		this.isRenew = isRenew;
	}
	
	@Length(min=0, max=255, message="是否超期长度必须介于 0 和 255 之间")
	public String getIsOvertime() {
		return isOvertime;
	}

	public void setIsOvertime(String isOvertime) {
		this.isOvertime = isOvertime;
	}
	
	@Length(min=0, max=255, message="管理员确认借阅书籍无误长度必须介于 0 和 255 之间")
	public String getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(String isConfirm) {
		this.isConfirm = isConfirm;
	}

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        this.bookIsbn = bookIsbn;
    }

    public String getBookUserName() {
        return bookUserName;
    }

    public void setBookUserName(String bookUserName) {
        this.bookUserName = bookUserName;
    }

    public String getBookUserWechatId() {
        return bookUserWechatId;
    }

    public void setBookUserWechatId(String bookUserWechatId) {
        this.bookUserWechatId = bookUserWechatId;
    }
}