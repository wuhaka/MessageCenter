package com.devin.messagecenter.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class MessageCenter {
	
	private static final String DEFAULT_METHOD = "onHandleMessage";
	
	private static MessageCenter sInstance = new MessageCenter();
	
	private Map<Class<?>, LinkedHashSet<String>> mReceivers = new HashMap<Class<?>, LinkedHashSet<String>>();
	private Map<Class<?>, LinkedHashSet<Object>> mClassMap = new HashMap<Class<?>, LinkedHashSet<Object>>();
	private Map<String, LinkedHashSet<Object>> mMethodMap = new HashMap<String, LinkedHashSet<Object>>();
	
	public static void register(Object receiver) {
		register(receiver, DEFAULT_METHOD);
	}
	
	public static void register(Object receiver, String method) {
		if (receiver == null) {
			return;
		}
		Class<?> clazz = receiver.getClass();
		LinkedHashSet<String> methods = null;
		if (!sInstance.mReceivers.containsKey(clazz)) {
			methods = new LinkedHashSet<String>();
			sInstance.mReceivers.put(clazz, methods);
		} else {
			methods = sInstance.mReceivers.get(clazz);
		}
		methods.add(method);
		
		LinkedHashSet<Object> objects = null;
		if (!sInstance.mClassMap.containsKey(clazz)) {
			objects = new LinkedHashSet<Object>();
			sInstance.mClassMap.put(clazz, objects);
		} else {
			objects = sInstance.mClassMap.get(clazz);
		}
		objects.add(receiver);
		
		LinkedHashSet<Object> objects2 = null;
		if (!sInstance.mMethodMap.containsKey(method)) {
			objects2 = new LinkedHashSet<Object>();
			sInstance.mMethodMap.put(method, objects2);
		} else {
			objects2 = sInstance.mMethodMap.get(method);
		}
		objects2.add(receiver);
	}
	
	public static void unregister(Object receiver) {
		unregister(receiver, DEFAULT_METHOD);
	}
	
	public static void unregister(Object receiver, String method) {
		if (receiver == null) {
			return;
		}
		Class<?> clazz = receiver.getClass();
		if (sInstance.mReceivers.containsKey(clazz)) {
			LinkedHashSet<String> methods = sInstance.mReceivers.get(clazz);
			if (methods.contains(method)) {
				methods.remove(method);
			}
		} 
		
		if (sInstance.mClassMap.containsKey(clazz)) {
			LinkedHashSet<Object> objects = sInstance.mClassMap.get(clazz);
			if (objects.contains(receiver)) {
				objects.remove(receiver);
			}
		}
		
		if (sInstance.mMethodMap.containsKey(method)) {
			LinkedHashSet<Object> objects = sInstance.mMethodMap.get(method);
			if (objects.contains(receiver)) {
				objects.remove(receiver);
			}
		} 
	}
	
	public static Object notify(Class<?> clazz, Object... obj) {
		return notify(0, null, clazz, obj);
	}
	
	private static Object notify(int catchId, String catchMethod, Class<?> clazz, Object... obj) {
		if (sInstance.mReceivers.containsKey(clazz)) {
			LinkedHashSet<String> methodSet = sInstance.mReceivers.get(clazz);
			int tempId = 0;
			String tempMethod = null;
		
				Method[] methods = clazz.getMethods();
				if (catchMethod != null) {
					tempMethod = catchMethod;
					for (int i = catchId - 1; i >= 0; i--) {
						if (methods[i].getName().equals(catchMethod)) {
							LinkedHashSet<Object> objectSet = sInstance.mClassMap.get(clazz);
							tempId = i;
							try {
							for (Object receiver : objectSet) {
								Object result = methods[i].invoke(receiver, obj);
								if (objectSet.size() == 1) {
									return result;
								}
							} 
						}  catch (IllegalArgumentException e) {
							notify(tempId, tempMethod, clazz, obj);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
						}
					}
				} else {
					for (String methodName : methodSet) {
						tempMethod = methodName;
						try {
							for (int i = methods.length - 1; i >= 0; i--) {
								if (methods[i].getName().equals(methodName)) {
									LinkedHashSet<Object> objectSet = sInstance.mClassMap.get(clazz);
									tempId = i;
									for (Object receiver : objectSet) {
										Object result = methods[i].invoke(receiver, obj);
										if (objectSet.size() == 1) {
											return result;
										}
									}
								}
							}
						}  catch (IllegalArgumentException e) {
							notify(tempId, tempMethod, clazz, obj);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
						
					}
				}
		}
		return null;
	}
	
	public static Object notify(String methodName, Object... obj) {
		if (sInstance.mMethodMap.containsKey(methodName)) {
			LinkedHashSet<Object> objectSet = sInstance.mMethodMap.get(methodName);
			try {
				for (Object receiver : objectSet) {
					Method[] methods = receiver.getClass().getMethods();
					Method method = null;
					boolean findMethod = false;
					if (!findMethod) {
						for (int i = methods.length - 1; i >= 0; i--) {
							if (methods[i].getName().equals(methodName)) {
								method = methods[i];
								findMethod = true;
								break;
							}
						}
					}
					if (method != null) {
						Object result = method.invoke(receiver, obj);
						if (objectSet.size() == 1) {
							return result;
						}
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
