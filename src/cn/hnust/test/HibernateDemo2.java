package cn.hnust.test;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.hnust.domain.Customer;
import cn.hnust.domain.LinkMan;
import cn.hnust.util.HibernateUtil;

/**
 * 查询
 * 一对多的查询操作
 * 		OID查询，HQL查询，QBC查询，SQL查询
 * 		hibernate中最后一种查询：对象导航查询
 * 			当两个实体之间有映射关系时，（关联关系可以是4种的任意一种）
 * 			我们通过调用getXXX方法既可以实现查询功能（功能由hibernate提供的）
 * 		例如:
 * 			customer.getLinkMans()就可以得到当前客户下的所有联系人
 * 			linkman.getCustomer()就可以得到当前联系人的所属客户
 * @author 龙伟
 * 2018年9月17日
 */

public class HibernateDemo2 {

	/*
	 * 查询客户id为1下所有的联系人
	 */
	@Test
	public void test1() {
	Session session = HibernateUtil.openSession();
	Transaction tx = session.beginTransaction();
	//1.查询客户
	Customer customer = session.get(Customer.class, 1L);
	//2.通过客户得到所有的联系人
	System.out.println("客户："+customer);
	Set<LinkMan> linkman = customer.getLinkmans();
	System.out.println("联系人："+linkman);
	tx.commit();
	}
	
	/*
	 * 查询联系人id为1所属的客户
	 */
	@Test
	public void test2() {
	Session session = HibernateUtil.openSession();
	Transaction tx = session.beginTransaction();
	//1.查询联系人
	LinkMan linkman = session.get(LinkMan.class, 1L);
	//2.通过客户得到所有的联系人
	System.out.println("联系人："+linkman);
	System.out.println("客户："+linkman.getCustomer());
	
	tx.commit();
	}
	
}
