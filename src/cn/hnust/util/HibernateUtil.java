package cn.hnust.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * 抽取hibernate的工具类
 * @author 龙伟
 * 2018年9月14日
 */
public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	
	//hibernate把可以遇见的异常都转换成运行时异常
	static {
		try {
				Configuration cfg = new Configuration();
				cfg.configure();
				sessionFactory = cfg.buildSessionFactory();
		}catch(ExceptionInInitializerError e){
			throw new ExceptionInInitializerError("初始化SessionFactory失败，请检查配置文件！");
		}
	}
		
		/*
		 * 获取一个新的Session对象
		 */
		public static  Session openSession() {
			return sessionFactory.openSession();
		}
		
		/*
		 * 从当前线程上获取Session对象
		 */
		public static Session getCurrentSession() {
			
			return sessionFactory.getCurrentSession();
		}
		
		//生成表结构
		public static void main(String[] args) {
			getCurrentSession();
		}
	}
