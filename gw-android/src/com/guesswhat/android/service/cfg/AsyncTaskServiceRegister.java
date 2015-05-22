package com.guesswhat.android.service.cfg;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import android.os.AsyncTask;

public class AsyncTaskServiceRegister {
	
	private static InvocationHandler invocationHandler = new ServiceInvocationHandler();

	@SuppressWarnings("unchecked")
	public static <T> T proxify(T service) {
		Class<? extends Object> clazz = service.getClass();
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), invocationHandler);
	}
	
	private static class ServiceInvocationHandler implements InvocationHandler {

		@Override
		public Object invoke(final Object service, final Method method, final Object[] params)
				throws Throwable {
			
			AsyncTask<Object, Object, Object> asyncTask = new AsyncTask<Object, Object, Object>() {

				@Override
				protected Object doInBackground(Object... arg0) {
					Object result = null;
					try {
						result = method.invoke(service, params);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return result;
				}
				
			};
			
			return asyncTask.execute(service);
		}
		
	}
}
