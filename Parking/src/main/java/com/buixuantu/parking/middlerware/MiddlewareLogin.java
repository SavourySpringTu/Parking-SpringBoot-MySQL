package com.buixuantu.parking.middlerware;

import com.buixuantu.parking.entity.EmployeeEntity;

public abstract class MiddlewareLogin {
	private MiddlewareLogin next;
	
	public MiddlewareLogin setNextChain(MiddlewareLogin next) {
		this.next=next;
		return next;
	}
	public void register(String username,String password) {
		
	}
	public abstract EmployeeEntity check(String username,String password);
	
	protected EmployeeEntity checkNext(String username, String password) {
		return next.check(username,password);
	}
}
