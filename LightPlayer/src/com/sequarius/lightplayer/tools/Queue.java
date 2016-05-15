package com.sequarius.lightplayer.tools;

import java.util.LinkedList;
/**
 * 队列的工具类
 * @author Sequarius
 *
 * @param <E>
 */
public class Queue<E> {
	private LinkedList<E> link;

	/**
	 * 提供了构造队列对象的构造器。
	 */
	public Queue() {
		link = new LinkedList<E>();
	}

	/**
	 * 添加元素的方法。
	 */
	public void myAdd(E obj) {
		link.addFirst(obj);
	}

	/**
	 * 获取的方法。
	 */
	public E myGet() {
		return link.removeLast();
	}

	/**
	 * 判断队列是否有元素。
	 */
	public boolean isNull() {
		return link.isEmpty();
	}
}
