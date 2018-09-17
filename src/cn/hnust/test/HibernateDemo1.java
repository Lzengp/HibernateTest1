package cn.hnust.test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.hnust.domain.Customer;
import cn.hnust.domain.LinkMan;
import cn.hnust.util.HibernateUtil;

/**
 * CRUD映射测试
 * 增加(Create)、读取查询(Retrieve)、更新(Update)和删除(Delete)
 * @author 龙伟
 * 2018年9月14日
 */
public class HibernateDemo1 {

	/*
	 * 保存操作
	 * 正常保存：创建一个新的联系人，需要关联一个客户
	 */
	@Test
	public void test1() {
	Session session = HibernateUtil.getCurrentSession();
	Transaction tx = session.beginTransaction();
	//1.查询一个客户
	Customer csut = session.get(Customer.class, 1L);//get方法查询
	//2.创建一个新的联系人
	LinkMan lkm  = new LinkMan();
	lkm.setLkmName("映射测试-保存");
	//3.创建客户与联系人的映射，让联系人知道自己属于哪个客户
	lkm.setCustomer(csut);
	//4.保存联系人
	session.save(lkm);
	tx.commit();
	}
	
	/*
	 * 保存操作
	 * 创建一个客户和一个联系人
	 * 建立联系人和客户的双向关联关系
	 * 使用符合原则的保存：
	 * 		原则是，先保存主表实体，再保存从表实体
	 * 执行之后会多一条insert语句
	 * 解决办法：
	 * 		让客户执行操作的时候，放弃维护关联关系的权利
	 * 		配置方式：
	 * 			在Customer的映射配置文件中的set标签上使用inverse属性
	 * 			inverse含义：是否放弃维护关联关系的权利
	 * 				true：放弃
	 * 				false：不放弃（默认值）
	 */
	@Test
	public void test2() {
		//1.创建一个客户
		Customer csut =  new Customer();//瞬时态
		csut.setCustName("映射测试-保存2");
		//2.创建一个新的联系人
		LinkMan lkm  = new LinkMan();//瞬时态
		lkm.setLkmName("映射测试-保存2");
		//3.建立联系人和客户的关联关系（双向）
		lkm.setCustomer(csut);//从表保存主表信息
		csut.getLinkmans().add(lkm);//主表保存从表信息
		//4.保存，要符合原则
		Session session = HibernateUtil.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(csut);//持久态  有一级缓存和快照
		session.save(lkm);//持久态  有一级缓存和快照
		tx.commit();
	}
	/*
	 * 保存操作
	 * 		级联保存
	 * 		使用级联保存，配置的方式，仍然时找到Customer的映射文件的Set标签，在上面加上cascade属性
	 * 		此时只需要session.save(csut);就可以保存两条信息
	 * 		在LinkMan的映射文件many-to-one上一样也可以，session.save(lkm)就可以保存所有信息
	 * 			cascade：配置级联操作
	 * 				级联保存更新的取值：save-update
	 */
	@Test
	public void test3() {
		//1.创建一个客户
		Customer csut =  new Customer();//瞬时态
		csut.setCustName("映射测试-保存3");
		//2.创建一个新的联系人
		LinkMan lkm  = new LinkMan();//瞬时态
		lkm.setLkmName("映射测试-保存3");
		//3.建立联系人和客户的关联关系（双向）
		lkm.setCustomer(csut);//从表保存主表信息
		csut.getLinkmans().add(lkm);//主表保存从表信息
		//4.保存，要符合原则
		Session session = HibernateUtil.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.save(csut);//持久态  有一级缓存和快照
		//session.save(lkm);//持久态  有一级缓存和快照
		tx.commit();
	}
	/*
	 * 更新操作
	 * 		需求：
	 * 			创建一个新的联系人，查询一个已有客户
	 * 			联系人新联系人和已有客户的双向关联关系
	 * 		更新客户
	 */
	@Test
	public void test4() {
	Session session = HibernateUtil.getCurrentSession();
	Transaction tx = session.beginTransaction();
	//1.查询一个客户
	Customer cust = session.get(Customer.class, 1L);//get方法查询
	//2.创建一个新的联系人
	LinkMan lkm  = new LinkMan();
	lkm.setLkmName("映射测试-更新");
	//3.创建客户与联系人的映射(双向)
	lkm.setCustomer(cust);
	cust.getLinkmans().add(lkm);
	//4.更新客户
	session.update(cust);
	tx.commit();
	}
	/*
	 * 删除操作
	 * 		删除从表数据就是单表
	 * 		删除主表数据：
	 * 			有引用：在删除时，hibernate会把从表的外键字段置为null，然后删除主表信息
	 * 			如果外键字段有非空约束，则hibernate不能更新外键字段为null，会报错
	 * 			无引用：就是单表，直接删
	 * 		级联删除：在实际开发中，要谨慎。
	 */
	@Test
	public void test5() {
	Session session = HibernateUtil.getCurrentSession();
	Transaction tx = session.beginTransaction();
	//1.查询一个客户
	Customer cust = session.get(Customer.class, 6L);//get方法查询
	//2.删除客户
	session.delete(cust);
	tx.commit();
	}
}
